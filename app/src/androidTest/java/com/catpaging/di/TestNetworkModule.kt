package com.catpaging.di

import com.catpaging.datasource.NetworkDataSource
import com.catpaging.network.CatRetrofit
import com.catpaging.network.CatRetrofitService
import com.catpaging.datasource.NetworkDataSourceImpl
import com.catpaging.network.CatRetrofitServiceImpl
import com.catpaging.repository.FakeCatRetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object  TestNetworkModule {

    @Provides
    @Named("fake_api")
    fun provideRetrofitService(
    ): CatRetrofitService {
        return FakeCatRetrofitService()
    }


    @Provides
    @Named("fake_network")
    fun provideNetworkDataSource(
        @Named("fake_api") catRetrofitService: CatRetrofitService
    ): NetworkDataSource {
        return NetworkDataSourceImpl(catRetrofitService)
    }

}