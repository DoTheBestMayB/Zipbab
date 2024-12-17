package com.bestapp.zipbab.util

import okhttp3.Interceptor
import okhttp3.Response

class CacheControlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val response = chain.proceed(originalRequest)
        val modifiedCacheControl = changeCacheControl(response.header("Cache-Control"))

        return response.newBuilder()
            .header("Cache-Control", modifiedCacheControl)
            .build()
    }

    private fun changeCacheControl(cacheControl: String?): String {
        return cacheControl?.split(",")?.joinToString(",") { value ->
            if (value.contains("max-age")) {
                "max-age=86400"
            } else {
                value
            }
        } ?: "max-age=86400"
    }
}

