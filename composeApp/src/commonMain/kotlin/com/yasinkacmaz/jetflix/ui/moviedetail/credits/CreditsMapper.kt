package com.zenkriztao.netflix.ui.moviedetail.credits

import com.zenkriztao.netflix.data.remote.CreditsResponse
import com.zenkriztao.netflix.util.Mapper
import com.zenkriztao.netflix.util.toProfilePhotoUrl

class CreditsMapper : Mapper<CreditsResponse, Credits> {
    override fun map(input: CreditsResponse): Credits {
        val cast = input.cast.map { cast ->
            Person(cast.name, cast.character, cast.profilePath?.toProfilePhotoUrl(), cast.gender.toGender(), cast.id)
        }
        val crew = input.crew.map { crew ->
            Person(crew.name, crew.job, crew.profilePath?.toProfilePhotoUrl(), crew.gender.toGender(), crew.id)
        }
        return Credits(cast, crew)
    }

    private fun Int.toGender() = if (this == 1) Gender.FEMALE else Gender.MALE
}
