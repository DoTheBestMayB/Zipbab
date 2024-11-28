package com.bestapp.zipbab.domain.model.kakaomap

/**
 * @property isEnd 현재 페이지가 마지막 페이지인지 여부
 * @property pageableCount total_count 중 노출 가능 문서 수 (최대 45)
 * @property totalCount    검색어에 검색된 문서 수
 */
data class Meta(
    val isEnd: Boolean?,
    val pageableCount: Int?,
    val totalCount: Int?,
)
