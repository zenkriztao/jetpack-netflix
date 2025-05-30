package com.zenkriztao.netflix.ui.moviedetail

import com.zenkriztao.netflix.ui.favorites.FavoritesDataStore
import com.zenkriztao.netflix.ui.moviedetail.credits.CreditsMapper
import com.zenkriztao.netflix.ui.moviedetail.image.ImageMapper
import com.zenkriztao.netflix.util.FakeStringDataStore
import com.zenkriztao.netflix.util.ViewModelTest
import com.zenkriztao.netflix.util.client.FakeMovieClient
import com.zenkriztao.netflix.util.json
import com.zenkriztao.netflix.util.test
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException

class MovieDetailViewModelTest : ViewModelTest() {

    private val movieService = FakeMovieClient()

    private val movieId = 1337

    private val movieDetailMapper = MovieDetailMapper()
    private val creditsMapper = CreditsMapper()
    private val imageMapper = ImageMapper()

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Test
    fun `fetchMovieDetail success`() = runTest {
        movieDetailViewModel = createViewModel()

        val stateValues = movieDetailViewModel.uiState.test()

        val expectedState = MovieDetailViewModel.MovieDetailUiState(
            movieDetailMapper.map(movieService.movieDetailResponse),
            creditsMapper.map(movieService.creditsResponse),
            imageMapper.map(movieService.imagesResponse),
            isFavorite = false,
            loading = false,
        )
        stateValues.last() shouldBe expectedState
    }

    @Test
    fun `fetchMovieDetail error`() = runTest {
        movieService.movieDetailException = IOException()

        movieDetailViewModel = createViewModel()
        val stateValues = movieDetailViewModel.uiState.test()

        stateValues.last().error.shouldBeInstanceOf<IOException>()
    }

    @Test
    fun `Should add to favorites when onFavoriteClicked`() = runTest {
        movieDetailViewModel = createViewModel()
        val stateValues = movieDetailViewModel.uiState.test()

        movieDetailViewModel.onFavoriteClicked()

        stateValues.last().isFavorite shouldBe true
    }

    @Test
    fun `Should remove favorite movie from favorites when onFavoriteClicked`() = runTest {
        movieDetailViewModel = createViewModel()
        val stateValues = movieDetailViewModel.uiState.test()

        movieDetailViewModel.onFavoriteClicked()
        movieDetailViewModel.onFavoriteClicked()

        stateValues.get(stateValues.lastIndex - 1).isFavorite shouldBe true
        stateValues.last().isFavorite shouldBe false
    }

    private fun createViewModel() = MovieDetailViewModel(
        movieId,
        movieService,
        movieDetailMapper,
        creditsMapper,
        imageMapper,
        FavoritesDataStore(json, FakeStringDataStore()),
    )
}
