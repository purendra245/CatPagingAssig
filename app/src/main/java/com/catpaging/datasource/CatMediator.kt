package com.catpaging.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.catpaging.model.CatsResponse
import com.catpaging.model.RemoteKeys
import com.catpaging.constants.AppConstants
import com.catpaging.constants.AppConstants.DEFAULT_PAGE_INDEX
import com.catpaging.db.CatDatabase
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


@ExperimentalPagingApi
class CatMediator (private val networkDataSource: NetworkDataSource,
                   private val  database: CatDatabase
) :RemoteMediator<Int, CatsResponse>() {

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CatsResponse>
    ): MediatorResult {

        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = networkDataSource.getCat( state.config.pageSize,page, AppConstants.MIME_TYPE)
            val isEndOfList = response.isEmpty()
            database.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    database.getRepoDao().clearRemoteKeys()
                    database.catDao().clearAllCats()
                }
                val prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.map {
                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.getRepoDao().insertAll(keys)
                database.catDao().insertAll(response)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * this returns the page key or the final end of list success result
     */
    suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, CatsResponse>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: DEFAULT_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                    ?: throw InvalidObjectException("Remote key should not be null for $loadType")
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                    ?: throw InvalidObjectException("Invalid state, key should not be null")
                //end of list condition reached
                remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, CatsResponse>): RemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { cats -> database.getRepoDao().remoteKeysCatId(cats.id) }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, CatsResponse>): RemoteKeys? {
        return state.pages
            .firstOrNull() { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { cats -> database.getRepoDao().remoteKeysCatId(cats.id) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, CatsResponse>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.getRepoDao().remoteKeysCatId(repoId)
            }
        }
    }

}