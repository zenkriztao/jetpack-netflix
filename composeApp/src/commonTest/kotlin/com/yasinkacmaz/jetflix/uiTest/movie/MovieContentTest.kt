package com.zenkriztao.netflix.uiTest.movie

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.zenkriztao.netflix.ui.movies.movie.Movie
import com.zenkriztao.netflix.ui.movies.movie.MovieItem
import com.zenkriztao.netflix.uiTest.util.setTestContent
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class MovieContentTest {

    @Test
    fun `Should render movie item correctly`() = runComposeUiTest {
        val movie = Movie(
            id = 1337,
            name = "Movie Name",
            releaseDate = "01.03.1337",
            posterPath = "",
            voteAverage = 9.24,
            voteCount = 1337,
        )

        setTestContent {
            LazyVerticalGrid(GridCells.Fixed(2)) {
                item {
                    MovieItem(movie)
                }
            }
        }

        onNodeWithText(movie.name, useUnmergedTree = true).assertIsDisplayed()
        onNodeWithText(movie.releaseDate, useUnmergedTree = true).assertIsDisplayed()
        onNodeWithText(movie.voteCount.toString(), useUnmergedTree = true).assertIsDisplayed()
        onNodeWithText(movie.voteAverage.toString(), useUnmergedTree = true).assertIsDisplayed()
    }
}
