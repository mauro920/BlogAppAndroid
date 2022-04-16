package com.example.blogapplication.data.remote.home

import com.example.blogapplication.core.Resource
import com.example.blogapplication.data.model.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

class HomeScreenDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getLatestPosts(): Flow<Resource<List<Post>>> = callbackFlow {
        val postList = mutableListOf<Post>()

        var postReference: Query? = null

        try {
            postReference = FirebaseFirestore.getInstance().collection("posts")
                .orderBy("created_at", Query.Direction.DESCENDING)

        } catch (e: Exception) {
            close(e)
        }

        val subscription = postReference?.addSnapshotListener { value, error ->
            if (value == null) return@addSnapshotListener

            try {
                for (post in value.documents) {
                    postList.clear()
                    post.toObject(Post::class.java)?.let { fbPost ->
                        fbPost.apply {
                            created_at = post.getTimestamp(
                                "created_at",
                                DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                            )?.toDate()
                        }
                        postList.add(fbPost)
                    }
                }
            } catch (e: Exception) {
                close(e)
            }
            trySend(Resource.Success(postList))
        }
        awaitClose { subscription?.remove() }
    }
}