package com.bestapp.zipbab.data.model.remote.kakaomap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property documentResponses 응답 결과
 * @property metaResponse 응답 관련 정보
 */

@Serializable
data class SearchLocationResponse(
    @SerialName("documents") val documentResponses: List<DocumentResponse>?,
    @SerialName("meta") val metaResponse: MetaResponse?,
)
