package com.yumtaufikhidayat.moviesx.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import com.yumtaufikhidayat.moviesx.data.repository.MoviesXRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesXRepository
): ViewModel() {
}