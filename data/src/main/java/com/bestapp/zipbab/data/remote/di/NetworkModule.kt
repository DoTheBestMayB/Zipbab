package com.bestapp.zipbab.data.remote.di

import com.bestapp.zipbab.data.BuildConfig
import com.bestapp.zipbab.data.remote.service.SearchLocationService
import com.bestapp.zipbab.data.remote.upload.UploadStateEntityAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class KakaoMapRetrofit

    @Provides
    @Singleton
    fun providesCustomInterceptor(): CustomInterceptor {
        return CustomInterceptor()
    }

    @Provides
    @Singleton
    fun providesKakaoOkHttpClient(
        customInterceptor: CustomInterceptor
    ): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(customInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .add(UploadStateEntityAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @KakaoMapRetrofit
    @Provides
    @Singleton
    fun providesKakaoMapRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.KAKAO_MAP_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun providesSearchLocationService(
        @KakaoMapRetrofit retrofit: Retrofit
    ): SearchLocationService {
        return retrofit.create(SearchLocationService::class.java)
    }

    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val url = chain.request().url.toUri().toString()

            val (name: String, key: String) = when {
                url.contains(BuildConfig.KAKAO_MAP_BASE_URL) -> Pair(
                    KEY_NAME,
                    String.format("%s %s", KEY, BuildConfig.KAKAO_REST_API_KEY)

                )

//                url.contains(BuildConfig.FCM_BASE_URL) -> {
//
//                    Pair(KEY_NAME, String.format("%s %s", FCM_KEY, BuildConfig.KAKAO_ADMIN_KEY))
//                }

                else -> {
                    return chain.proceed(chain.request())
                }
            }

            return with(chain) {
                val newRequest = request().newBuilder()
                    .addHeader(name, key)
                    .build()
                proceed(newRequest)
            }
        }
        companion object {
            private const val KEY_NAME = "Authorization"
            private const val KEY = "KakaoAK"
            private const val FCM_KEY = "Bearer"
        }
    }
}

