package com.bestapp.zipbab.data.model.remote.kakaomap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property addressName    전체 도로명 주소, 경기 시흥시 봉우재로23번안길 7-1
 * @property buildingName 건물 이름, ""
 * @property mainBuildingNo 건물 본번, 7
 * @property subBuildingNo 건물 부번, 없을 경우 빈 문자열("") 반환, 1
 * @property regionDepthName1 지역명1, 경기
 * @property regionDepthName2 지역명2, 시흥시
 * @property regionDepthName3 지역명3, 정왕동
 * @property roadName 봉우재로23번안길
 * @property zoneNo 지하 여부(Y 또는 N), N
 * @property undergroundYn 우편번호(5자리), 15056
 * @property longitude 경도 127,xxxxxxxxxxx
 * @property latitude 위도 37.xxxxxxxxxxxxx
 */
@Serializable
data class RoadAddressResponse(
    @SerialName("address_name") val addressName: String?,
    @SerialName("x") val longitude: String?,
    @SerialName("y") val latitude: String?,
    @SerialName("region_1depth_name") val regionDepthName1: String?,
    @SerialName("region_2depth_name") val regionDepthName2: String?,
    @SerialName("region_3depth_name") val regionDepthName3: String?,
    @SerialName("road_name") val roadName: String?,
    @SerialName("underground_yn") val undergroundYn: String?,
    @SerialName("main_building_no") val mainBuildingNo: String?,
    @SerialName("sub_building_no") val subBuildingNo: String?,
    @SerialName("building_name") val buildingName: String?,
    @SerialName("zone_no") val zoneNo: String?,
)
