package com.bestapp.zipbab.util

import coil.intercept.Interceptor
import coil.request.ImageResult

class CacheControlInterceptor: Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val request = chain.request

        val cacheControl = request.headers["Cache-Control"]
        if (cacheControl != null && cacheControl.contains("max-age=0")) {
            val modifiedCacheControl = cacheControl.split(",").joinToString(",") { value ->
                if (value.contains("max-age")) {
                    "max-age=86400"
                } else {
                    value
                }
            }

            val modifiedRequest = request.newBuilder()
                .setHeader("Cache-Control", modifiedCacheControl)
                .build()

            return chain.proceed(modifiedRequest)
        }

        return chain.proceed(request)
    }
}
