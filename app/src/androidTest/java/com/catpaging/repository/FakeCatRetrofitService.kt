package com.catpaging.repository

import com.catpaging.model.CatsResponse
import com.catpaging.network.CatRetrofit
import com.catpaging.network.CatRetrofitService
import com.catpaging.ui.activity.CatFactoryTest


class FakeCatRetrofitService : CatRetrofitService {

    override suspend fun getCat(limit: Int, page: Int, mimeType: String): List<CatsResponse> {
        val factory = CatFactoryTest()
        return listOf(factory.createCatResponse(),factory.createCatResponse(),factory.createCatResponse())

    }

}