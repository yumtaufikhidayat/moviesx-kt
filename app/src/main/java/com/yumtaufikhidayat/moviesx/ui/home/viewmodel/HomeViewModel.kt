package com.yumtaufikhidayat.moviesx.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.yumtaufikhidayat.moviesx.data.repository.MoviesXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MoviesXRepository
): ViewModel() {
    fun getGenres() = repository.getGenres()

    fun getMovieNowPlaying() = repository.getMovieNowPlaying().cachedIn(viewModelScope)
}