package com.zenkriztao.netflix.ui.moviedetail.image

import com.zenkriztao.netflix.data.remote.ImagesResponse
import com.zenkriztao.netflix.util.parseJson
import com.zenkriztao.netflix.util.resource.imagesJson
import com.zenkriztao.netflix.util.toOriginalUrl
import io.kotest.matchers.collections.shouldContainAll
import kotlin.test.Test

class ImageMapperTest {
    private val mapper = ImageMapper()

    private val imagesResponse: ImagesResponse = parseJson(imagesJson)

    @Test
    fun `Map backdrops and posters with original url`() {
        val images = mapper.map(imagesResponse)

        val expectedBackdrops = imagesResponse.backdrops.map { Image(it.filePath.toOriginalUrl(), it.voteCount) }
        val expectedPosters = imagesResponse.posters.map { Image(it.filePath.toOriginalUrl(), it.voteCount) }
        images shouldContainAll expectedBackdrops
        images shouldContainAll expectedPosters
    }
}
