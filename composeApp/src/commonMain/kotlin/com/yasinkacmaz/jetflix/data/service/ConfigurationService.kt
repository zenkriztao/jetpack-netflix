package com.zenkriztao.netflix.data.service

import com.zenkriztao.netflix.ui.settings.Language

interface ConfigurationService {
    suspend fun fetchLanguages(): List<Language>
}
