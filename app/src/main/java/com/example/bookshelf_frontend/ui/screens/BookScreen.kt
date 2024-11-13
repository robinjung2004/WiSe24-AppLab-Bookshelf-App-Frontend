package com.example.bookshelf_frontend.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf_frontend.model.Book
import com.example.bookshelf_frontend.model.Details
import com.example.bookshelf_frontend.model.Reviews
import com.example.bookshelf_frontend.ui.theme.PastelBlue
import com.example.bookshelf_frontend.ui.theme.PastelGrayGreen
import com.example.bookshelf_frontend.ui.theme.PastelPink
import com.example.bookshelf_frontend.ui.theme.PastelPurple
import com.example.bookshelf_frontend.ui.theme.PastelWhite
import com.example.bookshelf_frontend.ui.theme.SoftPink
import kotlinx.coroutines.launch
import androidx.compose.material.icons.outlined.Star as StarBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val detail by viewModel.selectedBookDetails.collectAsState()
    val reviews by viewModel.selectedBookReviews.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            error != null -> {
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            books.isEmpty() -> {
                Text(
                    text = "No books found",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 80.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(books.size) { index ->
                        val book = books[index]
                        BookItem(
                            book = book,
                            onClick = {
                                selectedBook = book
                                viewModel.loadBookDetails(book.id)
                                showBottomSheet = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showBottomSheet && selectedBook != null) {
        BookDetailsBottomSheet(
            book = selectedBook,
            detail = detail,
            reviews = reviews,
            isLoading = viewModel.isLoadingDetails.collectAsState().value,
            onDismiss = {
                showBottomSheet = false
                selectedBook = null
                viewModel.clearSelectedBook()
            }
        )
    }
}

@Composable
fun BookItem(
    book: Book,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (book.id % 6) {
                0 -> PastelWhite
                1 -> PastelPink
                2 -> PastelBlue
                3 -> PastelGrayGreen
                4 -> PastelPurple
                5 -> SoftPink
                else -> PastelWhite
            }
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.Center) // Zentriert den Titel
            )
            Text(
                text = book.author,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.BottomCenter) // Positioniert den Autor unten in der Mitte
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsBottomSheet(
    book: Book?,
    detail: Details?,
    reviews: List<Reviews>,
    isLoading: Boolean,  // Neuer Parameter
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            detail == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "Could not load details",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Button(onClick = onDismiss) {
                            Text("Close")
                        }
                    }
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Hauptinformationen
                    Text(
                        text = book?.title ?: "",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = book?.author ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    // Details Section
                    detail?.let { bookDetail ->
                        Spacer(modifier = Modifier.height(16.dp))

                        // Technische Details in einer Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                DetailRow("Year", bookDetail.year.toString())
                                DetailRow("Type", bookDetail.type)
                                DetailRow("Publisher", bookDetail.publisher)
                                DetailRow("Language", bookDetail.language)
                                DetailRow("ISBN-13", bookDetail.isbn13)
                                DetailRow("Pages", bookDetail.pages.toString())
                            }
                        }

                        // Beschreibung
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = bookDetail.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Reviews Section
                    if (reviews.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Reviews",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        reviews.forEach { reviews ->
                            ReviewCard(reviews)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    // Zusätzlicher Spacer am Ende für besseres Scrolling
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ReviewCard(review: Reviews) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.reviewer,
                    style = MaterialTheme.typography.titleSmall
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < review.stars)
                                Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = if (index < review.stars)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

