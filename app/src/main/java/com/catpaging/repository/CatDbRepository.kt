package com.catpaging.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.catpaging.constants.AppConstants.DEFAULT_PAGE_SIZE
import com.catpaging.datasource.CatMediator
import com.catpaging.datasource.NetworkDataSource
import com.catpaging.db.CatDatabase
import com.catpaging.model.CatsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ExperimentalPagingApi
class CatDbRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val database: CatDatabase
) : CatRepository {


    /**
     * let's define page size, page size is the only required param, rest is optional
     */
    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }


    override fun getCats(): Flow<PagingData<CatsResponse>> {
        val pagingConfig: PagingConfig = getDefaultPageConfig()
        val pagingSourceFactory = { database.catDao().getCatData() }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = CatMediator(networkDataSource, database)
        ).flow
    }

}
