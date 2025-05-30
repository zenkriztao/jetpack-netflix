package com.zenkriztao.netflix.data.service

import com.zenkriztao.netflix.data.remote.ProfileResponse

interface PersonService {
    suspend fun profile(id: Int): ProfileResponse
}
