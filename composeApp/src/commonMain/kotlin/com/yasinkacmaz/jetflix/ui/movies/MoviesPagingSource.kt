package com.zenkriztao.netflix.ui.movies

import com.zenkriztao.netflix.data.service.MovieService
import com.zenkriztao.netflix.ui.filter.FilterDataStore
import com.zenkriztao.netflix.ui.filter.MovieRequestOptionsMapper
import com.zenkriztao.netflix.ui.movies.movie.Movie
import com.zenkriztao.netflix.ui.movies.movie.MovieMapper
import kotlinx.coroutines.flow.first

class MoviesPagingSource(
    private val movieService: MovieService,
    private val filterDataStore: FilterDataStore,
    private val movieMapper: MovieMapper,
    private val movieRequestOptionsMapper: MovieRequestOptionsMapper,
) {

    suspend fun load(page: Int, searchQuery: String = ""): LoadResult = try {
        val options = movieRequestOptionsMapper.map(filterDataStore.filterState.first())
        val moviesResponse = if (searchQuery.isNotBlank()) {
            movieService.search(page, searchQuery)
        } else {
            movieService.fetchMovies(page, options)
        }
        val movies = moviesResponse.movies.map(movieMapper::map)
        LoadResult(
            movies = movies,
            isLastPage = page >= moviesResponse.totalPages,
            error = null,
        )
    } catch (exception: Exception) {
        LoadResult(
            movies = emptyList(),
            isLastPage = false,
            error = exception,
        )
    }

    data class LoadResult(
        val movies: List<Movie> = emptyList(),
        val isLastPage: Boolean = false,
        val error: Throwable? = null,
    )
}
