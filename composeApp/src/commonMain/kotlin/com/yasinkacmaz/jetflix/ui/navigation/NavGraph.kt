package com.zenkriztao.netflix.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.zenkriztao.netflix.LocalNavController
import com.zenkriztao.netflix.ui.favorites.FavoritesScreen
import com.zenkriztao.netflix.ui.moviedetail.MovieDetailScreen
import com.zenkriztao.netflix.ui.moviedetail.MovieDetailViewModel
import com.zenkriztao.netflix.ui.moviedetail.image.ImagesScreen
import com.zenkriztao.netflix.ui.moviedetail.person.PeopleGridScreen
import com.zenkriztao.netflix.ui.movies.MoviesScreen
import com.zenkriztao.netflix.ui.profile.ProfileScreen
import com.zenkriztao.netflix.ui.splash.SplashScreen
import jetflix.composeapp.generated.resources.Res
import jetflix.composeapp.generated.resources.title_cast
import jetflix.composeapp.generated.resources.title_crew
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
private fun movieDetailViewModel(movieId: Int): MovieDetailViewModel =
    koinViewModel(key = movieId.toString()) { parametersOf(movieId) }

@Composable
fun SetupNavGraph(startScreen: Screen = Screen.Movies) {
    NavHost(navController = LocalNavController.current, startDestination = startScreen) {
        composable<Screen.Splash> {
            SplashScreen()
        }

        composable<Screen.Movies> {
            MoviesScreen(moviesViewModel = koinViewModel(), filterViewModel = koinViewModel())
        }

        composable<Screen.MovieDetail> {
            MovieDetailScreen(movieDetailViewModel(it.toRoute<Screen.MovieDetail>().movieId))
        }

        composable<Screen.MovieImages> {
            val screen = it.toRoute<Screen.MovieImages>()
            val images = movieDetailViewModel(screen.movieId).uiState.value.images
            ImagesScreen(images, screen.initialPage)
        }

        composable<Screen.MovieCast> {
            val movieDetail = movieDetailViewModel(it.toRoute<Screen.MovieCast>().movieId).uiState.value
            PeopleGridScreen(
                stringResource(Res.string.title_cast, movieDetail.movieDetail?.title.orEmpty()),
                movieDetail.credits.cast,
            )
        }

        composable<Screen.MovieCrew> {
            val movieDetail = movieDetailViewModel(it.toRoute<Screen.MovieCast>().movieId).uiState.value
            PeopleGridScreen(
                stringResource(Res.string.title_crew, movieDetail.movieDetail?.title.orEmpty()),
                movieDetail.credits.crew,
            )
        }

        composable<Screen.Profile> {
            ProfileScreen(koinViewModel { parametersOf(it.toRoute<Screen.Profile>().personId) })
        }

        composable<Screen.Favorites> {
            FavoritesScreen(favoritesViewModel = koinViewModel())
        }
    }
}
