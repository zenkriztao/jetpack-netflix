package com.zenkriztao.netflix.ui.movies

import com.zenkriztao.netflix.ui.filter.FilterDataStore
import com.zenkriztao.netflix.ui.filter.MovieRequestOptionsMapper
import com.zenkriztao.netflix.ui.movies.movie.MovieMapper
import com.zenkriztao.netflix.ui.settings.LanguageDataStore
import com.zenkriztao.netflix.util.FakeStringDataStore
import com.zenkriztao.netflix.util.ViewModelTest
import com.zenkriztao.netflix.util.client.FakeMovieClient
import com.zenkriztao.netflix.util.json
import com.zenkriztao.netflix.util.test
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class MoviesViewModelTest : ViewModelTest() {
    private val movieService = FakeMovieClient()
    private val filterDataStore = FilterDataStore(json, FakeStringDataStore())
    private val languageDataStore = LanguageDataStore(json, FakeStringDataStore())
    private val movieMapper = MovieMapper()
    private val movieRequestOptionsMapper = MovieRequestOptionsMapper()
    private val moviesPagingSource = MoviesPagingSource(
        movieService,
        filterDataStore,
        movieMapper,
        movieRequestOptionsMapper,
    )

    @Test
    fun `Should not send search query change event when query is empty`() = runTest {
        val moviesViewModel = createViewModel()
        val queryChanges = moviesViewModel.searchQueryChanges.test()

        moviesViewModel.onSearch("")

        queryChanges.shouldBeEmpty()
    }

    @Test
    fun `Should not send search query change event when query is less than threshold`() = runTest {
        val moviesViewModel = createViewModel()
        val queryChanges = moviesViewModel.searchQueryChanges.test()

        moviesViewModel.onSearch("qu")

        queryChanges.shouldBeEmpty()
    }

    @Test
    fun `Should send search query change event when query is valid`() = runTest {
        val moviesViewModel = createViewModel()
        val queryChanges = moviesViewModel.searchQueryChanges.test()

        val query = "query"
        moviesViewModel.onSearch(query)

        queryChanges.shouldNotBeEmpty()
        queryChanges.last() shouldBe query
    }

    @Test
    fun `Should set search query when searched`() = runTest {
        val moviesViewModel = createViewModel()

        val query = "query"
        moviesViewModel.onSearch(query)

        moviesViewModel.searchQuery.value shouldBe query
    }

    private fun createViewModel() = MoviesViewModel(filterDataStore, languageDataStore, moviesPagingSource)
}
