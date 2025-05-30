package com.zenkriztao.netflix.data.service

import com.zenkriztao.netflix.data.remote.CreditsResponse
import com.zenkriztao.netflix.data.remote.GenresResponse
import com.zenkriztao.netflix.data.remote.ImagesResponse
import com.zenkriztao.netflix.data.remote.MovieDetailResponse
import com.zenkriztao.netflix.data.remote.MoviesResponse

interface MovieService {
    suspend fun fetchMovies(pageNumber: Int, options: Map<String, String>): MoviesResponse

    suspend fun search(pageNumber: Int, searchQuery: String, includeAdult: Boolean = true): MoviesResponse

    suspend fun fetchGenres(): GenresResponse

    suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse

    suspend fun fetchMovieCredits(movieId: Int): CreditsResponse

    suspend fun fetchMovieImages(movieId: Int): ImagesResponse
}
