package com.example.bookshelf_frontend.data

import com.example.bookshelf_frontend.model.Book
import com.example.bookshelf_frontend.model.Details
import com.example.bookshelf_frontend.model.Reviews
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