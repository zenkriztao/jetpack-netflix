package com.zenkriztao.netflix.util.client

import com.zenkriztao.netflix.data.remote.CreditsResponse
import com.zenkriztao.netflix.data.remote.Genre
import com.zenkriztao.netflix.data.remote.GenresResponse
import com.zenkriztao.netflix.data.remote.ImagesResponse
import com.zenkriztao.netflix.data.remote.MovieDetailResponse
import com.zenkriztao.netflix.data.remote.MovieResponse
import com.zenkriztao.netflix.data.remote.MoviesResponse
import com.zenkriztao.netflix.data.service.MovieService
import com.zenkriztao.netflix.util.parseJson
import com.zenkriztao.netflix.util.resource.creditsJson
import com.zenkriztao.netflix.util.resource.imagesJson
import com.zenkriztao.netflix.util.resource.movieDetailJson

class FakeMovieClient : MovieService {
    val genre = Genre(1, "Name")
    var fetchGenresException: Exception? = null
    var movieDetailException: Exception? = null
    val movieDetailResponse: MovieDetailResponse = parseJson(movieDetailJson)
    val creditsResponse: CreditsResponse = parseJson(creditsJson)
    val imagesResponse: ImagesResponse = parseJson(imagesJson)
    val moviesResponse = MoviesResponse(1, listOf(MovieResponse(1, "movie", "", "", "", "", "", 1.1, 1)), 1, 1)
    val searchResponse = MoviesResponse(1, listOf(MovieResponse(1, "search", "", "", "", "", "", 1.1, 1)), 1, 1)

    override suspend fun fetchMovies(pageNumber: Int, options: Map<String, String>): MoviesResponse = moviesResponse

    override suspend fun search(pageNumber: Int, searchQuery: String, includeAdult: Boolean): MoviesResponse =
        searchResponse

    override suspend fun fetchGenres(): GenresResponse = if (fetchGenresException == null) {
        GenresResponse(listOf(genre))
    } else {
        throw fetchGenresException!!.also { fetchGenresException = null }
    }

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetailResponse = if (movieDetailException == null) {
        movieDetailResponse
    } else {
        throw movieDetailException!!.also { movieDetailException = null }
    }

    override suspend fun fetchMovieCredits(movieId: Int): CreditsResponse = creditsResponse

    override suspend fun fetchMovieImages(movieId: Int): ImagesResponse = imagesResponse
}
