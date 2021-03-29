package com.catpaging.di

import android.content.Context
import androidx.room.Room
import com.catpaging.db.CatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestCacheModule {


    @Named("test_db")
    @Provides
    fun provideCatDb(@ApplicationContext context: Context): CatDatabase {
        return Room
            .databaseBuilder(
                context,
                CatDatabase::class.java,
                CatDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}