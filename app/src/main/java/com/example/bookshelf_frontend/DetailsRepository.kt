package com.example.bookshelf_frontend

class DetailsRepository {
    private val bookApi = RetrofitClient.bookApi

    suspend fun getBookDetails(id: Int): Details {
        val response = bookApi.getBookDetails(id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty response body")
        }
        throw Exception("Error fetching book details: ${response.code()}")
    }
}
