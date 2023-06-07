package com.yumtaufikhidayat.moviesx.data.remote

import com.yumtaufikhidayat.moviesx.model.cast.MovieCastResponse
import com.yumtaufikhidayat.moviesx.model.detail.MovieDetailResponse
import com.yumtaufikhidayat.moviesx.model.genres.GenresResponse
import com.yumtaufikhidayat.moviesx.model.main.MovieMainResponse
import com.yumtaufikhidayat.moviesx.model.movietrailer.MovieVideoResponse
import com.yumtaufikhidayat.moviesx.model.reviews.MovieReviewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(UrlConstant.GENRE)
    suspend fun getGenres(): GenresResponse

    @GET(UrlConstant.NOW_PLAYING)
    suspend fun getMovieNowPlaying(
        @Query(UrlConstant.QUERY_PAGE) page: Int
    ): Response<MovieMainResponse>

    @GET(UrlConstant.MOVIE_DETAIL)
    suspend fun getDetailMovie(
        @Path(UrlConstant.QUERY_MOVIE_ID) movieId: Int
    ): MovieDetailResponse

    @GET(UrlConstant.MOVIE_VIDEO)
    suspend fun getMovieVideo(
        @Path(UrlConstant.QUERY_MOVIE_ID) movieId: Int
    ): MovieVideoResponse

    @GET(UrlConstant.MOVIE_CAST)
    suspend fun getMovieCast(
        @Path(UrlConstant.QUERY_MOVIE_ID) movieId: Int
    ): MovieCastResponse

    @GET(UrlConstant.MOVIE_REVIEWS)
    suspend fun getMovieReviews(
        @Path(UrlConstant.QUERY_MOVIE_ID) movieId: Int
    ): MovieReviewResponse
}