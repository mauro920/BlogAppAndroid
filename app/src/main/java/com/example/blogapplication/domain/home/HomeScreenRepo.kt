package com.example.blogapplication.domain.home

import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.model.Post
import kotlinx.coroutines.flow.Flow

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Flow<Resource<List<Post>>>
}