package com.zenkriztao.netflix.ui.movies.movie

import com.zenkriztao.netflix.data.remote.MovieResponse
import com.zenkriztao.netflix.util.Mapper
import com.zenkriztao.netflix.util.parseAsDate
import com.zenkriztao.netflix.util.toPosterUrl

class MovieMapper : Mapper<MovieResponse, Movie> {
    override fun map(input: MovieResponse) = Movie(
        id = input.id,
        name = input.name,
        releaseDate = input.firstAirDate.parseAsDate(),
        posterPath = input.posterPath.orEmpty().toPosterUrl(),
        voteAverage = (input.voteAverage * 10).toInt() / 10.0,
        voteCount = input.voteCount,
    )
}
