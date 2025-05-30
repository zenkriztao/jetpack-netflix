package com.zenkriztao.netflix.util.client

import com.zenkriztao.netflix.data.remote.ProfileResponse
import com.zenkriztao.netflix.data.service.PersonService
import com.zenkriztao.netflix.util.parseJson
import com.zenkriztao.netflix.util.resource.personJson

class FakePersonClient : PersonService {
    var fetchProfileException: Exception? = null
    val profileResponse = parseJson<ProfileResponse>(personJson)

    override suspend fun profile(id: Int): ProfileResponse = if (fetchProfileException == null) {
        profileResponse
    } else {
        throw fetchProfileException!!.also { fetchProfileException = null }
    }
}
