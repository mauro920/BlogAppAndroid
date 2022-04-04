package com.example.blogapplication.domain

import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.model.Post
import com.example.blogapplication.data.remote.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {

    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()

}