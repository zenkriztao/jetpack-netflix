package com.zenkriztao.netflix.ui.filter.genres

import com.zenkriztao.netflix.data.remote.Genre
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GenreUiModelMapperTest {
    @Test
    fun map() {
        val mapper = GenreUiModelMapper()
        val input = Genre(1, "Name")

        val uiModel = mapper.map(input)

        uiModel.genre shouldBe input
    }
}
