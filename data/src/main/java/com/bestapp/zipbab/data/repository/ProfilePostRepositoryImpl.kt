package com.bestapp.zipbab.data.repository

import com.bestapp.zipbab.data.remote.datasource.ProfilePostRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.StorageRemoteDataSource
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.domain.repository.ProfilePostRepository
import com.bestapp.zipbab.domain.util.NetworkError
import com.bestapp.zipbab.domain.util.Result
import com.bestapp.zipbab.domain.util.onError
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ProfilePostRepositoryImpl @Inject constructor(
    private val profilePostRemoteDataSource: ProfilePostRemoteDataSource,
    private val storageRemoteDataSource: StorageRemoteDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : ProfilePostRepository {
    override suspend fun deletePost(
        userId: String,
        postDocumentId: String
    ): Result<Boolean, NetworkError> {
        val post = when (val response = profilePostRemoteDataSource.getPost(postDocumentId)) {
            is Result.Error -> return response
            is Result.Success -> response.data
        }
        // 포스트 삭제
        profilePostRemoteDataSource.deletePost(userId, postDocumentId)

        // 포스트에 사용된 이미지 삭제
        coroutineScope {
            for (path in post.images) {
                launch {
                    storageRemoteDataSource.deleteImage(path)
                        .onError { exception ->
                            // TODO : firebase crashlytics 기록 후 cloud Function으로 삭제
//                            Firebase.crashlytics.log("Failed to delete image: $path")
//                            Firebase.crashlytics.recordException(exception)
                        }
                }
            }
        }
        return Result.Success(true)
    }

    override suspend fun createPost(
        userId: String,
        imagePaths: List<String>
    ): Result<String, NetworkError> {
        val postId =
            when (val response = profilePostRemoteDataSource.createPost(userId, imagePaths)) {
                is Result.Error -> return response
                is Result.Success -> response.data
            }
        userRemoteDataSource.addPost(userId, postId).onError {
            return Result.Error(it)
        }
        return Result.Success(postId)
    }
}
