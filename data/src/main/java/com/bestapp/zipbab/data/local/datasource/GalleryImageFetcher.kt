package com.bestapp.zipbab.data.local.datasource

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import com.bestapp.zipbab.data.model.local.GalleryImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryImageFetcher(
    private val contentResolver: ContentResolver
) {

    suspend fun getImageFromGallery(range: IntRange): List<GalleryImageInfo> =
        withContext(Dispatchers.IO) {
            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
            )

            val collectionUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val images = mutableListOf<GalleryImageInfo>()
            var order = range.first
            var skipped = 0
            var count = 0

            contentResolver.query(
                collectionUri,
                projection,
                null,
                null,
                "${MediaStore.Images.Media.DATE_ADDED} DESC",
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val displayNameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

                while (cursor.moveToNext()) {
                    if (skipped < range.first) {
                        skipped++
                        continue
                    } else if (count >= range.last) {
                        break
                    }
                    val uri = ContentUris.withAppendedId(collectionUri, cursor.getLong(idColumn))
                    val name = cursor.getString(displayNameColumn)

                    val image = GalleryImageInfo(uri, name, order++)
                    images.add(image)
                    count++
                }
            }
            return@withContext images
        }
}