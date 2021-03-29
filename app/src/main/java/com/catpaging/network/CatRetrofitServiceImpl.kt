package com.catpaging.network

import com.catpaging.model.CatsResponse
import com.catpaging.network.CatRetrofit
import com.catpaging.network.CatRetrofitService


class CatRetrofitServiceImpl constructor(
    private val catRetrofit: CatRetrofit
): CatRetrofitService {

    override suspend fun getCat(limit: Int, page: Int, mimeType: String): List<CatsResponse> {
        return catRetrofit.getCats(limit,page,mimeType)
    }

}