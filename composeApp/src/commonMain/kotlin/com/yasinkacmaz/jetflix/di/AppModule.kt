package com.zenkriztao.netflix.di

import com.zenkriztao.netflix.ui.filter.MovieRequestOptionsMapper
import com.zenkriztao.netflix.ui.filter.genres.GenreUiModelMapper
import com.zenkriztao.netflix.ui.moviedetail.MovieDetailMapper
import com.zenkriztao.netflix.ui.moviedetail.credits.CreditsMapper
import com.zenkriztao.netflix.ui.moviedetail.image.ImageMapper
import com.zenkriztao.netflix.ui.movies.movie.MovieMapper
import com.zenkriztao.netflix.ui.movies.moviesModule
import com.zenkriztao.netflix.ui.profile.ProfileMapper
import com.zenkriztao.netflix.util.AppDispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    single { AppDispatchers() }
    singleOf(::MovieRequestOptionsMapper)
    singleOf(::GenreUiModelMapper)
    singleOf(::MovieDetailMapper)
    singleOf(::CreditsMapper)
    singleOf(::ImageMapper)
    singleOf(::MovieMapper)
    singleOf(::ProfileMapper)
    includes(moviesModule)
}
