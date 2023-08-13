package com.example.composelazycolumn.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Post : ArrayList<PostItem>()
data class PostItem(
    @SerializedName("userId") @Expose val userId: Int?,
    @SerializedName("id") @Expose val id: Int?,
    @SerializedName("title") @Expose val title: String?,
    @SerializedName("body") @Expose val body: String?
)