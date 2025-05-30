package com.zenkriztao.netflix.ui.profile

import com.zenkriztao.netflix.data.remote.ProfileResponse
import com.zenkriztao.netflix.util.Mapper
import com.zenkriztao.netflix.util.toImdbProfileUrl
import com.zenkriztao.netflix.util.toOriginalUrl

class ProfileMapper : Mapper<ProfileResponse, Profile> {
    override fun map(input: ProfileResponse) = Profile(
        name = input.name,
        biography = input.biography,
        birthday = input.birthday.orEmpty(),
        placeOfBirth = input.placeOfBirth.orEmpty(),
        alsoKnownAs = input.alsoKnownAs,
        imdbProfileUrl = input.imdbId?.toImdbProfileUrl(),
        profilePhotoUrl = input.profilePath?.toOriginalUrl().orEmpty(),
        knownFor = input.knownForDepartment,
    )
}
