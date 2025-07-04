package com.zenkriztao.netflix.ui.filter.genres

import androidx.compose.ui.graphics.Color
import com.zenkriztao.netflix.data.remote.Genre
import com.zenkriztao.netflix.util.randomColor
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class GenreUiModel(val genre: Genre = Genre(-1, "")) {
    @Transient
    val primaryColor: Color = Color.randomColor()

    @Transient
    val secondaryColor: Color = Color.randomColor()
}
