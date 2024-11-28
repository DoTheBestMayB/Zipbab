package com.bestapp.zipbab.data.remote.datasource

import com.bestapp.zipbab.data.model.remote.user.UserPrivateResponse
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result

interface UserPrivateRemoteDataSource {

    suspend fun getUser(userId: String): Result<UserPrivateResponse, NetworkError>
}
