package com.example.bookshelf_frontend

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApi {
    @GET("list")
    suspend fun getBooks(): Response<List<Book>>

    @GET("details/{id}")
    suspend fun getBookDetails(@Path("id") id: Int): Response<Details>

    @GET("reviews/{id}")
    suspend fun getReviews(@Path("id") id: Int): Response<List<Reviews>>


    companion object {
        const val BASE_URL = "http://10.0.2.2/"
    }
}