package com.zenkriztao.netflix.di

import com.zenkriztao.netflix.data.client.ConfigurationClient
import com.zenkriztao.netflix.data.client.MovieClient
import com.zenkriztao.netflix.data.client.PersonClient
import com.zenkriztao.netflix.data.service.ConfigurationService
import com.zenkriztao.netflix.data.service.MovieService
import com.zenkriztao.netflix.data.service.PersonService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val servicesModule = module {
    singleOf(::MovieClient).bind(MovieService::class)
    singleOf(::ConfigurationClient).bind(ConfigurationService::class)
    singleOf(::PersonClient).bind(PersonService::class)
}
