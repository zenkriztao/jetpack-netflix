package com.zenkriztao.netflix.ui.movies

import com.zenkriztao.netflix.ui.filter.FilterDataStore
import com.zenkriztao.netflix.ui.filter.MovieRequestOptionsMapper
import com.zenkriztao.netflix.ui.movies.movie.MovieMapper
import com.zenkriztao.netflix.util.FakeStringDataStore
import com.zenkriztao.netflix.util.client.FakeMovieClient
import com.zenkriztao.netflix.util.json
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class MoviesPagingSourceTest {

    private val movieService = FakeMovieClient()

    private val movieMapper = MovieMapper()
    private val movieRequestOptionsMapper = MovieRequestOptionsMapper()

    private val filterDataStore = FilterDataStore(json, FakeStringDataStore())

    private val moviesPagingSource =
        MoviesPagingSource(movieService, filterDataStore, movieMapper, movieRequestOptionsMapper)

    @Test
    fun `Should call movies endpoint when query is empty`() = runTest {
        val loadResult = moviesPagingSource.load(1, "")

        loadResult.shouldBeInstanceOf<MoviesPagingSource.LoadResult>()
        loadResult.movies shouldBe movieService.moviesResponse.movies.map(movieMapper::map)
    }

    @Test
    fun `Should call search endpoint when query is not empty`() = runTest {
        val loadResult = moviesPagingSource.load(1, "query")

        loadResult.shouldBeInstanceOf<MoviesPagingSource.LoadResult>()
        loadResult.movies shouldBe movieService.searchResponse.movies.map(movieMapper::map)
    }
}
