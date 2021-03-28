package com.catpaging.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.ExperimentalPagingApi
import com.catpaging.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCatActivity : AppCompatActivity() {


    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_cat)

    }

}