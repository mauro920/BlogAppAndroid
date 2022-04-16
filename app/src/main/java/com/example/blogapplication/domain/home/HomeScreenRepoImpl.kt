package com.example.blogapplication.domain.home

import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.model.Post
import com.example.blogapplication.data.remote.home.HomeScreenDataSource
import kotlinx.coroutines.flow.Flow

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {

    override suspend fun getLatestPosts(): Flow<Resource<List<Post>>> = dataSource.getLatestPosts()

}