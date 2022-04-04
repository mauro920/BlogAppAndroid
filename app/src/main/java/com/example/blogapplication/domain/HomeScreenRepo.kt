package com.example.blogapplication.domain

import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Resource<List<Post>>
}