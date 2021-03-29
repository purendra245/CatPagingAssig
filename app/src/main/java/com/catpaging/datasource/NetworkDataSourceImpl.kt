package com.catpaging.datasource

import com.catpaging.datasource.NetworkDataSource
import com.catpaging.model.CatsResponse
import com.catpaging.network.CatRetrofitService

class NetworkDataSourceImpl
constructor(
    private val catRetrofitService: CatRetrofitService,
): NetworkDataSource {

    override suspend fun getCat(limit: Int, page: Int, mimeType: String): List<CatsResponse> {
        return catRetrofitService.getCat(limit, page, mimeType)
    }

}