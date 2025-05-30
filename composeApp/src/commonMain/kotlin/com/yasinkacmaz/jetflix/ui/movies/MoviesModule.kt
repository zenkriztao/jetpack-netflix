package com.zenkriztao.netflix.ui.movies

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val moviesModule = module {
    singleOf(::MoviesPagingSource)
}
