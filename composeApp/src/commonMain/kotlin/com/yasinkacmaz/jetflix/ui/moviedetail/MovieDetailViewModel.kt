package com.zenkriztao.netflix.ui.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenkriztao.netflix.data.service.MovieService
import com.zenkriztao.netflix.ui.favorites.FavoritesDataStore
import com.zenkriztao.netflix.ui.moviedetail.credits.Credits
import com.zenkriztao.netflix.ui.moviedetail.credits.CreditsMapper
import com.zenkriztao.netflix.ui.moviedetail.image.Image
import com.zenkriztao.netflix.ui.moviedetail.image.ImageMapper
import com.zenkriztao.netflix.ui.movies.movie.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieId: Int,
    private val movieService: MovieService,
    private val movieDetailMapper: MovieDetailMapper,
    private val creditsMapper: CreditsMapper,
    private val imageMapper: ImageMapper,
    private val favoritesDataStore: FavoritesDataStore,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        fetchMovieDetail(movieId = movieId)
    }

    private fun fetchMovieDetail(movieId: Int) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(loading = true, error = null)
        _uiState.value = try {
            coroutineScope {
                val movieDetailResponse = async { movieService.fetchMovieDetail(movieId) }
                val creditsResponse = async { movieService.fetchMovieCredits(movieId) }
                val imagesResponse = async { movieService.fetchMovieImages(movieId) }
                val isFavorite = async { favoritesDataStore.isFavorite(movieId) }
                _uiState.value.copy(
                    movieDetail = movieDetailMapper.map(movieDetailResponse.await()),
                    credits = creditsMapper.map(creditsResponse.await()),
                    images = imageMapper.map(imagesResponse.await()),
                    isFavorite = isFavorite.await(),
                    loading = false,
                )
            }
        } catch (exception: Exception) {
            _uiState.value.copy(error = exception, loading = false)
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            val movieDetail = _uiState.value.movieDetail ?: return@launch

            val isFavorite = favoritesDataStore.isFavorite(movieId)
            if (isFavorite) {
                favoritesDataStore.removeFromFavorites(movieId)
            } else {
                val movie = Movie(
                    id = movieId,
                    name = movieDetail.title,
                    releaseDate = movieDetail.releaseDate,
                    posterPath = movieDetail.posterUrl,
                    voteAverage = movieDetail.voteAverage,
                    voteCount = movieDetail.voteCount,
                )
                favoritesDataStore.addToFavorites(movie)
            }
            _uiState.value = _uiState.value.copy(isFavorite = !isFavorite)
        }
    }

    data class MovieDetailUiState(
        val movieDetail: MovieDetail? = null,
        val credits: Credits = Credits(listOf(), listOf()),
        val images: List<Image> = listOf(),
        val isFavorite: Boolean = false,
        val loading: Boolean = false,
        val error: Throwable? = null,
    )
}
