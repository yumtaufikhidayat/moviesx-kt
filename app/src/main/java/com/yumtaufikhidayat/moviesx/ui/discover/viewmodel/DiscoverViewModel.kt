package com.yumtaufikhidayat.moviesx.ui.discover.viewmodel

import androidx.lifecycle.ViewModel
import com.yumtaufikhidayat.moviesx.data.repository.MoviesXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val repository: MoviesXRepository
): ViewModel() {

    fun getGenres() = repository.getGenres()
    fun discoverMovie(query: String) = repository.discoverMovie(query)
}