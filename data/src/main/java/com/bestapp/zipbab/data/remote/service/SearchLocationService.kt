package com.bestapp.zipbab.data.remote.service

import com.bestapp.zipbab.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class SearchLocationService(
    private val ktor: HttpClient,
) {

    /** 주소 검색 API - 주소를 지도 위에 정확하게 표시하기 위해 해당 주소의 좌표 정보를 제공하는 API
     * @param query 필수) 검색을 원하는 질의어(주소)
     * @param analyzeType
     *  similar: 입력한 건물명과 일부만 매칭될 경우에도 확장된 검색 결과 제공 (기본값)
     *  exact: 주소의 정확한 건물명이 입력된 주소패턴일 경우에 한해, 입력한 건물명과 정확히 일치하는 검색 결과 제공
     * @param page 결과 페이지 번호(최소: 1, 최대: 45, 기본값: 1)
     * @param size 한 페이지에 보여질 문서의 개수(최소: 1, 최대: 30, 기본값: 10)
     */
    suspend fun convertLocation(
        query: String,
        analyzeType: String,
        page: Int,
        size: Int,
    ): HttpResponse {
        return ktor.get(
            urlString = "${BuildConfig.KAKAO_MAP_BASE_URL}/v2/local/search/address"
        ) {
            headers {
                append("Authorization", "KakaoAK ${BuildConfig.KAKAO_REST_API_KEY}")
            }
            parameter("query", query)
            parameter("analyzeType", analyzeType)
            parameter("page", page)
            parameter("size", size)
        }
    }
}
