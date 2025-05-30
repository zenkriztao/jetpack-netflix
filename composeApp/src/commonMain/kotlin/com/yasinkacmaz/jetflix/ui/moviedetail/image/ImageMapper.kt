package com.zenkriztao.netflix.ui.moviedetail.image

import com.zenkriztao.netflix.data.remote.ImagesResponse
import com.zenkriztao.netflix.util.Mapper
import com.zenkriztao.netflix.util.toOriginalUrl

class ImageMapper : Mapper<ImagesResponse, List<Image>> {
    override fun map(input: ImagesResponse): List<Image> = buildList {
        addAll(input.backdrops.map { Image(it.filePath.toOriginalUrl(), it.voteCount) })
        addAll(input.posters.map { Image(it.filePath.toOriginalUrl(), it.voteCount) })
        sortByDescending { it.voteCount }
    }
}
