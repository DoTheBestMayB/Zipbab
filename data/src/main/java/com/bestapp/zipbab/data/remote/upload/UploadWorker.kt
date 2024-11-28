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
import com.bestapp.zipbab.domain.util.Result
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
    private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.uploadWorkNotification("")

    private val json = Json {
        serializersModule = SerializersModule {
            polymorphic(UploadStateDto::class) {
                subclass(UploadStateDto.Pending::class, UploadStateDto.Pending.serializer())
                subclass(
                    UploadStateDto.ProcessImage::class,
                    UploadStateDto.ProcessImage.serializer()
                )
                subclass(
                    UploadStateDto.ProcessPost::class,
                    UploadStateDto.ProcessPost.serializer()
                )
                subclass(UploadStateDto.Fail::class, UploadStateDto.Fail.serializer())
                subclass(
                    UploadStateDto.SuccessPost::class,
                    UploadStateDto.SuccessPost.serializer()
                )
            }
        }
    }

    override suspend fun doWork(): Result {
        // 업로드할 이미지 데이터가 입력되었는지 확인한다.
        val userId =
            inputData.getString(UPLOAD_USER_ID_KEY) ?: return Result.failure()
        val tempPostDocumentID =
            inputData.getString(UPLOAD_TEMP_POST_DOCUMENT_ID_KEY) ?: return Result.failure()
        val images =
            inputData.getStringArray(UPLOAD_IMAGES_KEY)?.toList() ?: return Result.failure()

        return withContext(ioDispatcher) {
            return@withContext try {
                setForeground(getForegroundInfo())
                updateProgress(UploadStateDto.Pending(tempPostDocumentID))
                makeNotification("Pending")
                val imageUrls = mutableListOf<String>()

                for ((idx, image) in images.withIndex()) {
                    makeNotification("Uploading : ${idx + 1} / ${images.size}")
                    updateProgress(
                        UploadStateDto.ProcessImage(
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

                updateProgress(UploadStateDto.ProcessPost(tempPostDocumentID))
                val postDocumentId = when (val response = profilePostRepository.createPost(userId, imageUrls)) {
                    is com.bestapp.zipbab.domain.util.Result.Error -> return@withContext Result.failure()
                    is com.bestapp.zipbab.domain.util.Result.Success -> response.data
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

    private suspend fun updateProgress(state: UploadStateDto) {
        val data = json.encodeToString(state)
        setProgress(workDataOf(PROGRESS_KEY to data))
    }

    private fun makeNotification(message: String) {
        appContext.makeStatusNotification(message)
    }

    companion object {
        const val UPLOAD_USER_ID_KEY = "UploadUserId"
        const val UPLOAD_TEMP_POST_DOCUMENT_ID_KEY = "UploadTempPostDocumentId"
        const val UPLOAD_IMAGES_KEY = "UploadImages"
        const val PROGRESS_KEY = "Progress"
        const val RESULT_TEMP_POST_DOCUMENT_ID_KEY = "RESULT_TEMP_POST_DOCUMENT_ID"
        const val RESULT_POST_DOCUMENT_ID_KEY = "RESULT_POST_DOCUMENT_ID"
    }
}
