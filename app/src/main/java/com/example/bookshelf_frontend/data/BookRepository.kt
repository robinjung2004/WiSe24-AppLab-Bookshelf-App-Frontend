package com.example.bookshelf_frontend.data

import com.example.bookshelf_frontend.model.Book

class BookRepository {
    private val bookApi = RetrofitClient.bookApi

    suspend fun getBooks(): List<Book> {
        val response = bookApi.getBooks()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        }
        throw Exception("Error fetching books: ${response.code()}")
    }
}