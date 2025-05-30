package com.zenkriztao.netflix.ui.profile

import com.zenkriztao.netflix.data.remote.ProfileResponse
import com.zenkriztao.netflix.util.parseJson
import com.zenkriztao.netflix.util.resource.personJson
import com.zenkriztao.netflix.util.toImdbProfileUrl
import com.zenkriztao.netflix.util.toOriginalUrl
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class ProfileMapperTest {
    private val mapper = ProfileMapper()

    private val profileResponse = parseJson<ProfileResponse>(personJson)

    @Test
    fun map() {
        val profile = mapper.map(profileResponse)

        profile.name shouldBe profileResponse.name
        profile.biography shouldBe profileResponse.biography
        profile.birthday shouldBe profileResponse.birthday
        profile.placeOfBirth shouldBe profileResponse.placeOfBirth
        profile.alsoKnownAs shouldBe profileResponse.alsoKnownAs
        profile.imdbProfileUrl shouldBe profileResponse.imdbId?.toImdbProfileUrl()
        profile.profilePhotoUrl shouldBe profileResponse.profilePath?.toOriginalUrl()
        profile.knownFor shouldBe profileResponse.knownForDepartment
    }
}
