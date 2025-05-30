package com.zenkriztao.netflix.ui.filter

import com.zenkriztao.netflix.ui.filter.genres.GenreUiModel
import com.zenkriztao.netflix.ui.filter.option.FilterOption
import com.zenkriztao.netflix.ui.filter.option.GenresFilterOption
import com.zenkriztao.netflix.ui.filter.option.GenresOption
import com.zenkriztao.netflix.ui.filter.option.IncludeAdultOption
import com.zenkriztao.netflix.ui.filter.option.SortBy
import com.zenkriztao.netflix.ui.filter.option.SortByOption
import com.zenkriztao.netflix.ui.filter.option.SortOrder
import com.zenkriztao.netflix.ui.filter.option.SortOrderOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class FilterState(
    @SerialName("sort_order") val sortOrder: SortOrder = SortOrder.DESCENDING,
    @SerialName("sort_by") val sortBy: SortBy = SortBy.POPULARITY,
    @SerialName("includeAdult") val includeAdult: Boolean = false,
    @SerialName("selected_genre_ids") val selectedGenreIds: List<Int> = emptyList(),
    @Transient val genres: List<GenreUiModel> = emptyList(),
)

fun FilterState.toFilterOptions(): List<FilterOption<*>> = buildList {
    add(SortOrderOption(sortOrder))
    add(SortByOption(sortBy))
    if (genres.isNotEmpty()) {
        add(GenresOption(GenresFilterOption(genres, selectedGenreIds.toMutableList())))
    }
    add(IncludeAdultOption(includeAdult))
}
