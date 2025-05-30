package com.zenkriztao.netflix.data.client

import com.zenkriztao.netflix.data.service.ConfigurationService
import com.zenkriztao.netflix.ui.settings.Language
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ConfigurationClient(private val httpClient: HttpClient) : ConfigurationService {
    override suspend fun fetchLanguages(): List<Language> = httpClient.get("configuration/languages").body()
}
