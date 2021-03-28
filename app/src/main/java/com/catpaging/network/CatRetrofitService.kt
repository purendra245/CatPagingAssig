package com.catpaging.network

import com.catpaging.model.CatsResponse


interface CatRetrofitService {

    suspend fun getCat(limit: Int,
                    page: Int,mimeType: String): List<CatsResponse>
}