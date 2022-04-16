package com.example.blogapplication.data.remote.home

import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.model.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    suspend fun getLatestPosts(): Resource<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").orderBy("created_at", Query.Direction.DESCENDING).get().await()
        for (post in querySnapshot.documents) {
            post.toObject(Post::class.java)?.let { fbPost ->
                fbPost.apply { created_at = post.getTimestamp("created_at",DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)?.toDate() }
                postList.add(fbPost)
            }
        }
        return Resource.Success(postList)
    }
}