package com.bestapp.zipbab.data.model.remote.kakaomap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property addressName 전체 지번 주소, 경기 시흥시 정왕동 1614-11
 * @property bCode 법정 코드, 4139013200
 * @property hCode 행정 코드, 4139059100
 * @property mainAddressNo 지번 주번지, 1614
 * @property subAddressNo 지번 부번지, 없을 경우 빈 문자열("") 반환, 11
 * @property mountainYn 산 여부(Y 또는 N), N
 * @property regionDepthName1 지역 1 Depth(시도 단위), 경기
 * @property regionDepthName2 지역 2 Depth(구 단위), 시흥시
 * @property regionDepthName3h 지역 3 Depth(동 단위), 정왕1동
 * @property regionDepthName3 지역 3 Depth(행정동 명칭), 정왕동
 * @property longitude 경도 127,x(11자리)
 * @property latitude 위도 37.x(13자리)
 */
@Serializable
data class AddressResponse(
    @SerialName("address_name") val addressName: String?,
    @SerialName("x") val longitude: String?,
    @SerialName("y") val latitude: String?,
    @SerialName("b_code") val bCode: String?,
    @SerialName("h_code") val hCode: String?,
    @SerialName("main_address_no") val mainAddressNo: String?,
    @SerialName("sub_address_no") val subAddressNo: String?,
    @SerialName("mountain_yn") val mountainYn: String?,
    @SerialName("region_1depth_name") val regionDepthName1: String?,
    @SerialName("region_2depth_name") val regionDepthName2: String?,
    @SerialName("region_3depth_h_name") val regionDepthName3h: String?,
    @SerialName("region_3depth_name") val regionDepthName3: String?,
)
