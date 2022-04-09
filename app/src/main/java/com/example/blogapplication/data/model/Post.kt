package com.example.blogapplication.data.model

import java.util.*

data class Post(
    val profile_picture: String = "",
    val profile_name: String = "",
    val post_timestamp: Date? = null,
    val post_image: String = "",
    val post_description: String = ""
)
