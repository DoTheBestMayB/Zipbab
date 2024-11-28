package com.bestapp.zipbab.data.mapper

import com.bestapp.zipbab.data.model.remote.kakaomap.AddressResponse
import com.bestapp.zipbab.data.model.remote.kakaomap.DocumentResponse
import com.bestapp.zipbab.data.model.remote.kakaomap.MetaResponse
import com.bestapp.zipbab.data.model.remote.kakaomap.RoadAddressResponse
import com.bestapp.zipbab.data.model.remote.kakaomap.SearchLocationResponse
import com.bestapp.zipbab.domain.model.kakaomap.Address
import com.bestapp.zipbab.domain.model.kakaomap.Document
import com.bestapp.zipbab.domain.model.kakaomap.Meta
import com.bestapp.zipbab.domain.model.kakaomap.RoadAddress
import com.bestapp.zipbab.domain.model.kakaomap.SearchLocation

fun SearchLocationResponse.toDomain(): SearchLocation {
    return SearchLocation(
        document = documentResponses?.map { it.toDomain() },
        meta = metaResponse?.toDomain(),
    )
}

fun DocumentResponse.toDomain(): Document {
    return Document(
        address = addressResponse?.toDomain(),
        longitude = longitude,
        latitude = latitude,
        addressName = addressName,
        addressType = addressType,
        roadAddress = roadAddressResponse?.toDomain(),
    )
}

fun AddressResponse.toDomain(): Address {
    return Address(
        addressName = addressName,
        longitude = longitude,
        latitude = latitude,
        bCode = bCode,
        hCode = hCode,
        mainAddressNo = mainAddressNo,
        subAddressNo = subAddressNo,
        mountainYn = mountainYn,
        regionDepthName1 = regionDepthName1,
        regionDepthName2 = regionDepthName2,
        regionDepthName3h = regionDepthName3h,
        regionDepthName3 = regionDepthName3,
    )
}

fun RoadAddressResponse.toDomain(): RoadAddress {
    return RoadAddress(
        addressName = addressName,
        longitude = longitude,
        latitude = latitude,
        regionDepthName1 = regionDepthName1,
        regionDepthName2 = regionDepthName2,
        regionDepthName3 = regionDepthName3,
        roadName = roadName,
        undergroundYn = undergroundYn,
        mainBuildingNo = mainBuildingNo,
        subBuildingNo = subBuildingNo,
        buildingName = buildingName,
        zoneNo = zoneNo,
    )
}

fun MetaResponse.toDomain(): Meta {
    return Meta(
        isEnd = isEnd,
        pageableCount = pageableCount,
        totalCount = totalCount,
    )
}
