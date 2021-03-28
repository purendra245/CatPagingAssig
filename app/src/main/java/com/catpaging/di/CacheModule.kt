package com.catpaging.di

import android.content.Context
import androidx.room.Room
import com.catpaging.db.CatDatabase
import com.catpaging.db.dao.CatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
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

    @Singleton
    @Provides
    fun provideCatDAO(catDatabase: CatDatabase): CatDao {
        return catDatabase.catDao()
    }


}