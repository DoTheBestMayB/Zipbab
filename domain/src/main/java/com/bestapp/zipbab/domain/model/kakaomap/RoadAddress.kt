package com.bestapp.zipbab.domain.model.kakaomap

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
data class RoadAddress(
    val addressName: String?,
    val longitude: String?,
    val latitude: String?,
    val regionDepthName1: String?,
    val regionDepthName2: String?,
    val regionDepthName3: String?,
    val roadName: String?,
    val undergroundYn: String?,
    val mainBuildingNo: String?,
    val subBuildingNo: String?,
    val buildingName: String?,
    val zoneNo: String?,
)
