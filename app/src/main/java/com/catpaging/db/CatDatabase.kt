package com.catpaging.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.catpaging.model.CatsResponse
import com.catpaging.model.RemoteKeys
import com.catpaging.db.dao.CatDao
import com.example.mypaging.db.dao.RemoteKeysDao

@Database(version = 1,entities = [CatsResponse::class, RemoteKeys::class ], exportSchema = false)
abstract class CatDatabase: RoomDatabase() {

    abstract fun catDao(): CatDao
    abstract fun getRepoDao(): RemoteKeysDao

    companion object{
        val DATABASE_NAME: String = "cats.db"
    }


}