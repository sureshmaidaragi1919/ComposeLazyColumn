package com.example.composelazycolumn.network

import com.example.composelazycolumn.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    @GET("/posts")
    suspend fun getPosts(): Response<Post>

}
