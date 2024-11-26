package com.bestapp.zipbab.data.remote.di

import com.bestapp.zipbab.data.networking.HttpClientFactory
import com.bestapp.zipbab.data.remote.service.SearchLocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClientFactory.create(CIO.create())
    }

    @Provides
    @Singleton
    fun providesSearchLocationService(
        httpClient: HttpClient,
    ): SearchLocationService {
        return SearchLocationService(httpClient)
    }
}

