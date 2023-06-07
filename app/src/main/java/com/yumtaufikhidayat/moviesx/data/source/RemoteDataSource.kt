package com.yumtaufikhidayat.moviesx.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.yumtaufikhidayat.moviesx.data.NetworkResult
import com.yumtaufikhidayat.moviesx.data.paging.MoviePagingSource
import com.yumtaufikhidayat.moviesx.data.remote.ApiService
import com.yumtaufikhidayat.moviesx.model.cast.MovieCastResponse
import com.yumtaufikhidayat.moviesx.model.detail.MovieDetailResponse
import com.yumtaufikhidayat.moviesx.model.genres.GenresResponse
import com.yumtaufikhidayat.moviesx.model.main.MovieMainResponse
import com.yumtaufikhidayat.moviesx.model.movietrailer.MovieVideoResponse
import com.yumtaufikhidayat.moviesx.model.reviews.MovieReviewResponse
import com.yumtaufikhidayat.moviesx.utils.Constant
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun getGenres() : LiveData<NetworkResult<GenresResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getGenres()
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieNowPlaying() = Pager(
        PagingConfig(
            pageSize = Constant.LOAD_PER_PAGE,
            enablePlaceholders = false
        ), pagingSourceFactory = {
            MoviePagingSource(apiService)
        }).flow

    fun getDetailMovie(movieId: Int): LiveData<NetworkResult<MovieDetailResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getDetailMovie(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieVideo(movieId: Int): LiveData<NetworkResult<MovieVideoResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getMovieVideo(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieCast(movieId: Int): LiveData<NetworkResult<MovieCastResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getMovieCast(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun getMovieReviews(movieId: Int): LiveData<NetworkResult<MovieReviewResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getMovieReviews(movieId)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }

    fun discoverMovie(query: String): LiveData<NetworkResult<MovieMainResponse>> = liveData {
        emit(NetworkResult.Loading)
        try {
            val response = apiService.getDiscoverMovie(query)
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.toString()))
        }
    }
}