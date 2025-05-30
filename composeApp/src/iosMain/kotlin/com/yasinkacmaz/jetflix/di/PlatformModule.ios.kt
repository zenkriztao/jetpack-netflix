package com.zenkriztao.netflix.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.zenkriztao.netflix.data.local.DataStoreFilePathProvider
import com.zenkriztao.netflix.data.local.IOSDataStore
import com.zenkriztao.netflix.data.local.LocalDataStore
import okio.Path.Companion.toPath
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule = module {
    singleOf(::DataStoreFilePathProvider)
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath { get<DataStoreFilePathProvider>().provide().toPath() }
    }
    single<LocalDataStore> { IOSDataStore(get()) }
}
