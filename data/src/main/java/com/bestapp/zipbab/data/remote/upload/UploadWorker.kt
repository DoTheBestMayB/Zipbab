package com.bestapp.zipbab.data.remote.upload

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.bestapp.zipbab.data.remote.datasource.UserRemoteDataSource
import com.bestapp.zipbab.domain.repository.ProfilePostRepository
import com.bestapp.zipbab.domain.repository.StorageRepository
import com.bestapp.zipbab.domain.util.onError
import com.bestapp.zipbab.domain.util.onSuccess
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val storageRepository: StorageRepository,
    private val profilePostRepository: ProfilePostRepository,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.uploadWorkNotification("")

    private val json = Json {
        serializersModule = SerializersModule {
            polymorphic(UploadStateEntity::class) {
                subclass(UploadStateEntity.Pending::class, UploadStateEntity.Pending.serializer())
                subclass(
                    UploadStateEntity.ProcessImage::class,
                    UploadStateEntity.ProcessImage.serializer()
                )
                subclass(
                    UploadStateEntity.ProcessPost::class,
                    UploadStateEntity.ProcessPost.serializer()
                )
                subclass(UploadStateEntity.Fail::class, UploadStateEntity.Fail.serializer())
                subclass(
                    UploadStateEntity.SuccessPost::class,
                    UploadStateEntity.SuccessPost.serializer()
                )
            }
        }
    }

    override suspend fun doWork(): Result {
        // 업로드할 이미지 데이터가 입력되었는지 확인한다.
        val userDocumentID =
            inputData.getString(UPLOAD_USER_DOCUMENT_ID_KEY) ?: return Result.failure()
        val tempPostDocumentID =
            inputData.getString(UPLOAD_TEMP_POST_DOCUMENT_ID_KEY) ?: return Result.failure()
        val images =
            inputData.getStringArray(UPLOAD_IMAGES_KEY)?.toList() ?: return Result.failure()

        return withContext(ioDispatcher) {
            return@withContext try {
                setForeground(getForegroundInfo())
                updateProgress(UploadStateEntity.Pending(tempPostDocumentID))
                makeNotification("Pending")
                val imageUrls = mutableListOf<String>()

                for ((idx, image) in images.withIndex()) {
                    makeNotification("Uploading : ${idx + 1} / ${images.size}")
                    updateProgress(
                        UploadStateEntity.ProcessImage(
                            tempPostDocumentID,
                            idx + 1,
                            images.size,
                        )
                    )
                    storageRepository.uploadImage(
                        "profilePost",
                        image
                    ).onSuccess { url ->
                        imageUrls.add(url)
                    }.onError {
                        makeNotification("Fail")
                        return@withContext Result.failure()
                    }
                }
                makeNotification("ProcessPost")

                updateProgress(UploadStateEntity.ProcessPost(tempPostDocumentID))
                val postDocumentId = profilePostRepository.createPost(imageUrls)

                val isSuccess = userRemoteDataSource.addPost(userDocumentID, postDocumentId)
                if (isSuccess) {
                    makeNotification("Success")
                    updateProgress(
                        UploadStateEntity.SuccessPost(
                            tempPostDocumentID,
                            postDocumentId
                        )
                    )
                } else {
                    updateProgress(UploadStateEntity.Fail(tempPostDocumentID))
                }
                val output = Data.Builder()
                    .putString(RESULT_TEMP_POST_DOCUMENT_ID_KEY, tempPostDocumentID)
                    .putString(RESULT_POST_DOCUMENT_ID_KEY, postDocumentId)
                    .build()
                Result.success(output)
            } catch (exception: Throwable) {
                makeNotification("Fail")
                Result.failure()
            }
        }
    }

    private suspend fun updateProgress(state: UploadStateEntity) {
        val data = json.encodeToString(state)
        setProgress(workDataOf(PROGRESS_KEY to data))
    }

    private fun makeNotification(message: String) {
        appContext.makeStatusNotification(message)
    }

    companion object {
        const val UPLOAD_USER_DOCUMENT_ID_KEY = "UploadUserDocumentId"
        const val UPLOAD_TEMP_POST_DOCUMENT_ID_KEY = "UploadTempPostDocumentId"
        const val UPLOAD_IMAGES_KEY = "UploadImages"
        const val PROGRESS_KEY = "Progress"
        const val RESULT_TEMP_POST_DOCUMENT_ID_KEY = "RESULT_TEMP_POST_DOCUMENT_ID"
        const val RESULT_POST_DOCUMENT_ID_KEY = "RESULT_POST_DOCUMENT_ID"
    }
}
