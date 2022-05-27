package com.gilangmarta.thisable.di

import com.gilangmarta.thisable.data.remote.VisionAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Provides
    fun provideGoogleVisionAPIService(retrofit: Retrofit): VisionAPIService =
        retrofit.create(VisionAPIService::class.java)

}