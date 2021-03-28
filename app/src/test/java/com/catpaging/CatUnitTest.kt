package com.catpaging

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.hilt.lifecycle.ViewModelFactoryModules
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.GlideContext
import com.bumptech.glide.RequestManager
import com.bumptech.glide.module.AppGlideModule
import com.catpaging.di.NetworkModule
import com.catpaging.model.CatsResponse
import com.catpaging.repository.CatDbRepository
import com.catpaging.ui.adapter.CatListAdapter
import com.catpaging.ui.viewmodel.CatsViewModel
import com.example.mypaging.di.InteractorsModule
import com.example.mypaging.utils.GlideApp
import com.example.mypaging.utils.GlideRequests
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@HiltAndroidTest
class CatUnitTest {



    @ExperimentalPagingApi
    @Inject lateinit var catDbRepository: CatDbRepository

    @ExperimentalPagingApi
    @Inject
    lateinit var context: Application

    @ExperimentalCoroutinesApi
    private lateinit var viewModel: CatsViewModel
    private val catFactory =CatFactory ()



    @ExperimentalPagingApi
    @InternalCoroutinesApi
    @Test
    fun getCatData() = runBlocking {

        val glide = GlideApp.with(context)
        val adapter = CatListAdapter(glide)

        val job = launch {
            viewModel.fetchCatData().distinctUntilChanged().collectLatest {
                    adapter.submitData(it)
            }
        }

        assertTrue(adapter.itemCount>0)

        job.cancel()


    }



    @ExperimentalCoroutinesApi
    @ExperimentalPagingApi
    @Before
    fun init() {
        viewModel = CatsViewModel(catDbRepository)
    }



}