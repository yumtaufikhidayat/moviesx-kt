package com.yumtaufikhidayat.moviesx.data.remote

import com.yumtaufikhidayat.moviesx.model.genres.GenresResponse
import com.yumtaufikhidayat.moviesx.model.main.MovieMainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(UrlConstant.GENRE)
    suspend fun getGenres(): GenresResponse

    @GET(UrlConstant.NOW_PLAYING)
    suspend fun getMovieNowPlaying(
        @Query(UrlConstant.QUERY_PAGE) page: Int
    ): Response<MovieMainResponse>
}