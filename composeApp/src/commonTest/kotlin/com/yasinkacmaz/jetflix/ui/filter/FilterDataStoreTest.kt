package com.zenkriztao.netflix.ui.filter

import com.zenkriztao.netflix.ui.filter.option.SortBy
import com.zenkriztao.netflix.util.FakeStringDataStore
import com.zenkriztao.netflix.util.json
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class FilterDataStoreTest {
    private val fakeStringDataStore = FakeStringDataStore()

    @Test
    fun `Should set filterState as default when preference is not exists`() = runTest {
        fakeStringDataStore.set("", "")

        val filterDataStore = createFilterDataStore()

        filterDataStore.filterState.first() shouldBe FilterState()
    }

    @Test
    fun `Should set filterState when preference is exists`() = runTest {
        val filterState = FilterState(sortBy = SortBy.VOTE_AVERAGE)
        fakeStringDataStore.set(filterState)

        val filterDataStore = createFilterDataStore()

        filterDataStore.filterState.first() shouldBe filterState
    }

    private fun createFilterDataStore() = FilterDataStore(json, fakeStringDataStore)
}
