package com.zenkriztao.netflix.ui.filter.option

import androidx.compose.runtime.Composable
import com.zenkriztao.netflix.ui.filter.FilterState

interface FilterOption<Type : Any> {
    val defaultValue: Type
    var currentValue: Type

    fun modifyFilterState(filterState: FilterState): FilterState

    @Composable fun Render(onChanged: () -> Unit)
}
