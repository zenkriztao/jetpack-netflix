package com.zenkriztao.netflix.util.client

import com.zenkriztao.netflix.data.service.ConfigurationService
import com.zenkriztao.netflix.ui.settings.Language

class FakeConfigurationClient : ConfigurationService {
    var fetchLanguagesException: Exception? = null
    var languages = listOf<Language>()

    override suspend fun fetchLanguages(): List<Language> = if (fetchLanguagesException == null) {
        languages
    } else {
        throw fetchLanguagesException!!.also { fetchLanguagesException = null }
    }
}
