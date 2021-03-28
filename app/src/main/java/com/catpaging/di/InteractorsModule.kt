package com.example.mypaging.di

import androidx.paging.ExperimentalPagingApi
import com.catpaging.datasource.NetworkDataSource
import com.catpaging.db.CatDatabase
import com.catpaging.repository.CatDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
 object InteractorsModule {


    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideRepository(
        cacheDataSource: CatDatabase,
        networkDataSource: NetworkDataSource
    ): CatDbRepository {
        return CatDbRepository(networkDataSource, cacheDataSource)
    }
}