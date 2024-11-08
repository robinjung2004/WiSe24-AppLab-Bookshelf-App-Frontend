package com.example.bookshelf_frontend.data

import com.example.bookshelf_frontend.model.Reviews

class ReviewsRepository {
    private val bookApi = RetrofitClient.bookApi

    suspend fun getReviews(id: Int): List<Reviews> {
        val response = bookApi.getReviews(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty response body")
        }
        throw Exception("Error fetching review details: ${response.code()}")
    }
}