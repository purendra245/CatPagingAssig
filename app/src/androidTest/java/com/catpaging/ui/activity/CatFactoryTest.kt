/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.catpaging.ui.activity

import com.catpaging.model.CatsResponse
import java.util.concurrent.atomic.AtomicInteger

open class CatFactoryTest {
    private val counter = AtomicInteger(0)
    fun createCatResponse(): CatsResponse {
        val id = counter.incrementAndGet()
        return CatsResponse(
            id = "id_$id",
            url = "https://cdn2.thecatapi.com/images/${id}tq.png"
        )
    }
}