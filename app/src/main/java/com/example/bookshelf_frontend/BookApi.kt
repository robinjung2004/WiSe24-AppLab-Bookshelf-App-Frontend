package com.example.bookshelf_frontend

import retrofit2.Response
import retrofit2.http.GET

interface BookApi {
    @GET("list")
    suspend fun getBooks(): Response<List<Book>>

    companion object {
        const val BASE_URL = "http://10.0.2.2/"
    }
}