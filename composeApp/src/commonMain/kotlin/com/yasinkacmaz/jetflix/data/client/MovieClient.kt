package com.zenkriztao.netflix.data.client

import com.zenkriztao.netflix.data.remote.CreditsResponse
import com.zenkriztao.netflix.data.remote.GenresResponse
import com.zenkriztao.netflix.data.remote.ImagesResponse
import com.zenkriztao.netflix.data.remote.MovieDetailResponse
import com.zenkriztao.netflix.data.remote.MoviesResponse
import com.zenkriztao.netflix.data.service.MovieService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieClient(private val httpClient: HttpClient) : MovieService {

    override suspend fun fetchMovies(pageNumber: Int, options: Map<String, String>): MoviesResponse =
        httpClient.get("discover/movie") {
            url {
                parameters.append("page", pageNumber.toString())
                options.forEach {
                    parameter(it.key, it.value)
                }
            }
        }.body()

    override suspend fun search(pageNumber: Int, searchQuery: String, includeAdult: Boolean): MoviesResponse =
        httpClient.get("search/movie") {
            url {
                parameters.append("page", pageNumber.toString())
                parameters.append("query", searchQuery)
                parameters.append("include_adult", includeAdult.toString())
            }
        }.body()

    override suspend fun fetchGenres(): GenresResponse = httpClient.get("genre/movie/list").body()

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse = httpClient.get("movie/$movieId").body()

    override suspend fun fetchMovieCredits(movieId: Int): CreditsResponse =
        httpClient.get("movie/$movieId/credits").body()

    override suspend fun fetchMovieImages(movieId: Int): ImagesResponse = httpClient.get("movie/$movieId/images").body()
}
