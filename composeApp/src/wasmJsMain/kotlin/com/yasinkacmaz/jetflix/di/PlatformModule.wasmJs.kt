package com.zenkriztao.netflix.di

import com.zenkriztao.netflix.data.local.DataStoreFilePathProvider
import com.zenkriztao.netflix.data.local.LocalDataStore
import com.zenkriztao.netflix.data.local.WasmJsDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::DataStoreFilePathProvider)
    single<LocalDataStore> { WasmJsDataStore() }
}
