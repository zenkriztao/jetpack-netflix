package com.zenkriztao.netflix.util

import com.zenkriztao.netflix.data.local.LocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeStringDataStore : LocalDataStore {
    private var values: MutableStateFlow<String> = MutableStateFlow("")

    override fun get(key: String): Flow<String> = values

    override suspend fun set(key: String, newValue: String) {
        values.value = newValue
    }

    suspend inline fun <reified T : Any> set(newValue: T) {
        set("", json.encodeToString(newValue))
    }
}
