package com.zenkriztao.netflix.data.local

// NO-OP: Androidx DataStore doesn't support wasmJs
actual class DataStoreFilePathProvider {
    actual fun provide(preferencesFileName: String): String = ""
}
