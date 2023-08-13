package com.example.composelazycolumn.repo

import com.example.composelazycolumn.network.ApiService
import com.example.composelazycolumn.data.Post
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(private val api: ApiService) {

    suspend fun execute(): Response<Post> {
        return api.getPosts()
    }

}
