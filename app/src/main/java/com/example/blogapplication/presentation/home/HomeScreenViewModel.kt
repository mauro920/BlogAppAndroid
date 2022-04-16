package com.example.blogapplication.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.blogapplication.core.Resource
import com.example.blogapplication.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {

    fun fetchLatestPosts() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        kotlin.runCatching {
            repo.getLatestPosts()
        }.onSuccess { flowList ->
            flowList.collect { emit(it) }
        }.onFailure {
            emit(Resource.Failure(Exception(it.message)))
        }
    }
}

class HomeScreenViewModelFactory(private val repo: HomeScreenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }

}