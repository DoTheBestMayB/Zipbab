package com.bestapp.zipbab.data.model.remote.kakaomap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property isEnd 현재 페이지가 마지막 페이지인지 여부
 * @property pageableCount total_count 중 노출 가능 문서 수 (최대 45)
 * @property totalCount    검색어에 검색된 문서 수
 */
@Serializable
data class MetaResponse(
    @SerialName("is_end") val isEnd: Boolean?,
    @SerialName("pageable_count") val pageableCount: Int?,
    @SerialName("total_count") val totalCount: Int?,
)
