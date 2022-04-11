package com.example.blogapplication.domain.camera

import android.graphics.Bitmap
import com.example.blogapplication.data.remote.camera.CameraDataSource

class CameraRepoImpl(private val dataSource:CameraDataSource): CameraRepo {
    override suspend fun uploadPicture(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPicture(imageBitmap,description)
    }
}