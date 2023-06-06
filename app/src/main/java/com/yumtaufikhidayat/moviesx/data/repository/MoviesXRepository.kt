package com.yumtaufikhidayat.moviesx.data.repository

import com.yumtaufikhidayat.moviesx.data.source.RemoteDataSource
import javax.inject.Inject

class MoviesXRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getGenres() = remoteDataSource.getGenres()

    fun getMovieNowPlaying() = remoteDataSource.getMovieNowPlaying()
}