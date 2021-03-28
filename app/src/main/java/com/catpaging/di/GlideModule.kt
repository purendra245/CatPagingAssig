package com.catpaging.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.mypaging.utils.GlideApp
import com.example.mypaging.utils.GlideRequest
import com.example.mypaging.utils.GlideRequests
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class GlideModule {

    @Provides
    fun provideGlide(@ActivityContext context: Context): RequestManager = Glide.with(context)

//    @Provides
//    fun provideGlideApp(@ActivityContext context: Context): GlideRequests = GlideApp.with(context)

}