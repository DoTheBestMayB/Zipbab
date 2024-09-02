package com.bestapp.zipbab.data.upload

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.bestapp.zipbab.data.datasource.remote.UserRemoteDataSource
import com.bestapp.zipbab.data.model.UploadStateEntity
import com.bestapp.zipbab.data.repository.PostRepository
import com.bestapp.zipbab.data.repository.StorageRepository
import com.squareup.moshi.Moshi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@HiltWorker
class UploadWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted params: WorkerParameters,
    private val storageRepository: StorageRepository,
    private val postRepository: PostRepository,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
    moshi: Moshi,
) : CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo = appContext.uploadWorkNotification("")

    private val jsonAdapter = moshi.adapter(UploadStateEntity::class.java)

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
                    val url = storageRepository.uploadImage(
                        Uri.parse(image)
                    )
                    imageUrls.add(url)
                }
                makeNotification("ProcessPost")

                updateProgress(UploadStateEntity.ProcessPost(tempPostDocumentID))
                val postDocumentId = postRepository.createPost(imageUrls)

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
        val data = jsonAdapter.toJson(state)
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