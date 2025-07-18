package com.zenkriztao.netflix.ui.settings

import com.zenkriztao.netflix.util.FakeStringDataStore
import com.zenkriztao.netflix.util.json
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class LanguageDataStoreTest {

    private val fakeStringDataStore = FakeStringDataStore()

    @Test
    fun `Should return default language when preference is not present`() = runTest {
        fakeStringDataStore.set("", "")

        val languageDataStore = createLanguageDataStore()

        languageDataStore.language.first() shouldBe Language.default
    }

    @Test
    fun `Should return saved language when preference is present`() = runTest {
        val turkishLanguage = Language(englishName = "Turkish", iso6391 = "tr", name = "Türkçe")
        fakeStringDataStore.set(turkishLanguage)

        val languageDataStore = createLanguageDataStore()

        languageDataStore.language.first() shouldBe turkishLanguage
    }

    private fun createLanguageDataStore() = LanguageDataStore(json, fakeStringDataStore)
}
