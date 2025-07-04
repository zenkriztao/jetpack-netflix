package com.zenkriztao.netflix.data.local

private const val JETFLIX_SETTINGS = "jetflix_settings.preferences_pb"

expect class DataStoreFilePathProvider {
    fun provide(preferencesFileName: String = JETFLIX_SETTINGS): String
}
