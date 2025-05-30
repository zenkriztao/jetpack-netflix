package com.zenkriztao.netflix.ui.moviedetail

import com.zenkriztao.netflix.data.remote.Genre
import com.zenkriztao.netflix.data.remote.MovieDetailResponse
import com.zenkriztao.netflix.util.Mapper
import com.zenkriztao.netflix.util.parseAsDate
import com.zenkriztao.netflix.util.toBackdropUrl
import com.zenkriztao.netflix.util.toPosterUrl

class MovieDetailMapper : Mapper<MovieDetailResponse, MovieDetail> {
    override fun map(input: MovieDetailResponse): MovieDetail {
        val productionCompanies = input.productionCompanies.map {
            ProductionCompany(it.name, it.logoPath.orEmpty().toPosterUrl())
        }
        return MovieDetail(
            id = input.id,
            title = input.title,
            originalTitle = input.originalTitle,
            overview = input.overview,
            tagline = input.tagline.dropLastWhile { it == '.' },
            backdropUrl = input.backdropPath.orEmpty().toBackdropUrl(),
            posterUrl = input.posterPath.orEmpty().toPosterUrl(),
            genres = input.genres.mapNotNull(Genre::name).take(4),
            releaseDate = input.releaseDate.parseAsDate(),
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            duration = input.runtime ?: -1,
            productionCompanies = productionCompanies,
            homepage = input.homepage,
        )
    }
}
