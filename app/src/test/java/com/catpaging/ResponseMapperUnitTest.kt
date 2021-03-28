package com.catpaging

import com.catpaging.model.CatsResponse
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ResponseMapperUnitTest {
    private val catFactory =CatFactory ()

    @Test
    fun `test network model object with all property contained in result`() {
        assertTrue(
            listOf(
                catFactory.createCatResponse(),
                catFactory.createCatResponse(),
                catFactory.createCatResponse(),
            ).any { it.url == "https://cdn2.thecatapi.com/images/3tq.png" }
        )

        assertFalse(
            listOf(
                catFactory.createCatResponse(),
                catFactory.createCatResponse(),
                catFactory.createCatResponse(),
            ).any { it.id == "id_1" }
        )
    }

    @Test
    fun `test result list should have the same item order as network model list`() {
        val result = listOf(
            CatsResponse(id = "image0", url = "https://cdn2.thecatapi.com/images/1tq.png"),
            CatsResponse(id = "dfgdfgs", url = "https://cdn2.thecatapi.com/images/2tq.png"),
            CatsResponse(id = "id", url = "https://cdn2.thecatapi.com/images/3tq.png")
        )

        assertTrue(result[0].id == "image0")
        assertTrue(result[1].id == "dfgdfgs")
        assertTrue(result[2].id == "id")
    }



}