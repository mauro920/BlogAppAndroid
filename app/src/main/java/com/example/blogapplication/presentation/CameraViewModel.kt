package com.example.blogapplication.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.blogapplication.core.Resource
import com.example.blogapplication.domain.auth.AuthRepo
import com.example.blogapplication.domain.camera.CameraRepo
import com.example.blogapplication.presentation.auth.AuthViewModel
import kotlinx.coroutines.Dispatchers

class CameraViewModel(private val repo: CameraRepo): ViewModel() {
    fun uploadPicture(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.uploadPicture(imageBitmap,description)))
        }catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }
}
class CameraViewModelProvider(private val repo: CameraRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(repo) as T
    }
}