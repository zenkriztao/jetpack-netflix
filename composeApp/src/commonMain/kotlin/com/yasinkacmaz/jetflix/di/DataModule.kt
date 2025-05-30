package com.zenkriztao.netflix.di

import com.zenkriztao.netflix.ui.favorites.FavoritesDataStore
import com.zenkriztao.netflix.ui.filter.FilterDataStore
import com.zenkriztao.netflix.ui.settings.LanguageDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::LanguageDataStore)
    singleOf(::FilterDataStore)
    singleOf(::FavoritesDataStore)
}
