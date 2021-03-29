package com.catpaging.di

import androidx.paging.ExperimentalPagingApi
import com.catpaging.datasource.NetworkDataSource
import com.catpaging.db.CatDatabase
import com.catpaging.repository.CatDbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestInteractorsModule {

    @ExperimentalPagingApi
    @Named("test_repo")
    @Provides
    fun provideRepository(
        cacheDataSource: CatDatabase,
        @Named("fake_network") networkDataSource: NetworkDataSource
    ): CatDbRepository {
        return CatDbRepository(networkDataSource, cacheDataSource)
    }
}