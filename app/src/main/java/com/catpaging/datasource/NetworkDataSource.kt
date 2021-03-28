package com.catpaging.datasource

import com.catpaging.model.CatsResponse

interface NetworkDataSource {

    suspend fun getCat( limit: Int,
                     page: Int,mimeType:String): List<CatsResponse>
}