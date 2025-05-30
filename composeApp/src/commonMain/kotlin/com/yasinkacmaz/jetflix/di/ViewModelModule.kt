package com.zenkriztao.netflix.di

import com.zenkriztao.netflix.ui.favorites.FavoritesViewModel
import com.zenkriztao.netflix.ui.filter.FilterViewModel
import com.zenkriztao.netflix.ui.moviedetail.MovieDetailViewModel
import com.zenkriztao.netflix.ui.movies.MoviesViewModel
import com.zenkriztao.netflix.ui.profile.ProfileViewModel
import com.zenkriztao.netflix.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MoviesViewModel)
    viewModelOf(::MovieDetailViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::FavoritesViewModel)
}
