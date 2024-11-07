package com.example.bookshelf_frontend

data class Details(
    val id: Int,
    val author: String,
    val year: Int,
    val type: String,
    val publisher: String,
    val language: String,
    val isbn13: String,
    val pages: Int,
    val title: String,
    val description: String
)
