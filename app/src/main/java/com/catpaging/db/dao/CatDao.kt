package com.catpaging.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.catpaging.model.CatsResponse

@Dao
interface CatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(catEntity: List<CatsResponse>)

    @Query("SELECT * FROM catsresponse")
    fun getCatData(): PagingSource<Int,CatsResponse>

    @Query("DELETE FROM catsresponse")
    suspend fun clearAllCats()
}
