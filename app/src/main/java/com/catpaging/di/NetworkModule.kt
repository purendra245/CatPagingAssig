package com.catpaging.di

import com.catpaging.BuildConfig
import com.catpaging.constants.AppConstants
import com.catpaging.datasource.NetworkDataSource
import com.catpaging.network.CatRetrofit
import com.catpaging.network.CatRetrofitService
import com.catpaging.datasource.NetworkDataSourceImpl
import com.catpaging.network.CatRetrofitServiceImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  NetworkModule {


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson:  Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideCatService(retrofit: Retrofit.Builder,  client : OkHttpClient): CatRetrofit {
        return retrofit
            .client(client)
            .build()
            .create(CatRetrofit::class.java)
    }

    @Singleton
    @Provides
     fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
     fun getOkHttpClient(
        okHttpLogger: HttpLoggingInterceptor): OkHttpClient {
        return if (BuildConfig.DEBUG){
            OkHttpClient.Builder()
                .addInterceptor(okHttpLogger)
                .build()
        }else{
            OkHttpClient.Builder()
                .build()
        }

    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        catRetrofit: CatRetrofit
    ): CatRetrofitService {
        return CatRetrofitServiceImpl(catRetrofit)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        catRetrofitService: CatRetrofitService
    ): NetworkDataSource {
        return NetworkDataSourceImpl(catRetrofitService)
    }

}