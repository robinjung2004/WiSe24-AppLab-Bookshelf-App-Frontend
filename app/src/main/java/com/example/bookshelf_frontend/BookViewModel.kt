package com.example.bookshelf_frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val repository = BookRepository()
    private val detailsRepository = DetailsRepository()
    private val reviewsRepository = ReviewsRepository()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _selectedBookDetails = MutableStateFlow<Details?>(null)
    val selectedBookDetails: StateFlow<Details?> = _selectedBookDetails

    private val _selectedBookReviews = MutableStateFlow<List<Reviews>>(emptyList())
    val selectedBookReviews: StateFlow<List<Reviews>> = _selectedBookReviews

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _books.value = repository.getBooks()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadBookDetails(bookId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _selectedBookDetails.value = detailsRepository.getBookDetails(bookId)
                _selectedBookReviews.value = reviewsRepository.getReviews(bookId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearSelectedBook() {
        _selectedBookDetails.value = null
        _selectedBookReviews.value = emptyList()
    }
}