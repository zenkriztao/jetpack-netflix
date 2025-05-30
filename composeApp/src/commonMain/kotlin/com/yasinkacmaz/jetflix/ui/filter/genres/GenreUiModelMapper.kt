package com.zenkriztao.netflix.ui.filter.genres

import com.zenkriztao.netflix.data.remote.Genre
import com.zenkriztao.netflix.util.Mapper

class GenreUiModelMapper : Mapper<Genre, GenreUiModel> {
    override fun map(input: Genre) = GenreUiModel(input)
}
