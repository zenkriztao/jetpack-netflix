package com.zenkriztao.netflix.data.client

import com.zenkriztao.netflix.data.remote.ProfileResponse
import com.zenkriztao.netflix.data.service.PersonService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PersonClient(private val httpClient: HttpClient) : PersonService {
    override suspend fun profile(id: Int): ProfileResponse = httpClient.get("person/$id").body()
}
