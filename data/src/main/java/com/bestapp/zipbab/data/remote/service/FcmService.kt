package com.bestapp.zipbab.data.remote.service

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FcmService {
    @FormUrlEncoded
    @POST("v2/push/register")
    suspend fun registerToken(
        @Field("uuid") uuid: String,
        @Field("device_id") deviceId: String,
        @Field("push_type") pushType: String,
        @Field("push_token") pushToken: String
    ) : com.bestapp.zipbab.data.remote.notification.RegisterToken

    @GET("v2/push/tokens")
    suspend fun downloadToken(
        @Query("uuid") uuid: String
    ) : com.bestapp.zipbab.data.remote.notification.DownloadToken

    @FormUrlEncoded
    @POST("v2/push/deregister")
    suspend fun deleteToken(
        @Field("uuid") uuid: String,
        @Field("device_id") deviceId: String,
        @Field("push_type") pushType: String
    )

    @POST("v1/projects/food-879fc/messages:send")
    suspend fun sendNotification(
        @Header("Authorization") apikey: String,
        @Body message: com.bestapp.zipbab.data.remote.notification.fcm.PushNotification
    )
}