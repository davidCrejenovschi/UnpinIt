package org.example.project.ui.home

import android.content.Context
import android.provider.MediaStore

lateinit var androidContext: Context

// Added 'actual constructor()' to match the commonMain declaration
actual class LocalGalleryManager actual constructor() {

    actual fun getImagesPaths(): List<String> {
        val imagesList = mutableListOf<String>()
        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        androidContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(dataColumn)
                imagesList.add(absolutePathOfImage)
            }
        }

        return imagesList.shuffled()
    }
}