package com.example.blogapplication.domain.camera

import android.graphics.Bitmap

interface CameraRepo {
    suspend fun uploadPicture(imageBitmap: Bitmap, description: String)
}