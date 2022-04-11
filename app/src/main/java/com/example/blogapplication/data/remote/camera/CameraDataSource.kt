package com.example.blogapplication.data.remote.camera

import android.graphics.Bitmap
import com.example.blogapplication.data.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class CameraDataSource {
    suspend fun uploadPicture(imageBitmap: Bitmap, description: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val randomName = UUID.randomUUID().toString()
        val imageRef =
            FirebaseStorage.getInstance().reference.child("${user?.uid}/posts/$randomName")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val downloadUrl =
            imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        user?.displayName?.let {
            FirebaseFirestore.getInstance().collection("posts").add(
                Post(
                    it,
                    user.photoUrl.toString(),
                    post_image = downloadUrl,
                    post_description = description,
                    uid = user.uid
                )
            )
        }
    }
}