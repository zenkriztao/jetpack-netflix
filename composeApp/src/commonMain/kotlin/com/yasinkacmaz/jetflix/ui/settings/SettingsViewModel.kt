package com.zenkriztao.netflix.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenkriztao.netflix.data.service.ConfigurationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val configurationService: ConfigurationService,
    private val languageDataStore: LanguageDataStore,
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        fetchLanguages()
        listenLanguageChanges()
    }

    private fun listenLanguageChanges() = viewModelScope.launch {
        languageDataStore.language
            .collectLatest { language -> _uiState.update { it.copy(selectedLanguage = language) } }
    }

    private fun fetchLanguages() = viewModelScope.launch {
        _uiState.update { it.copy(showLoading = true) }
        val languages = buildList<Language> {
            try {
                addAll(configurationService.fetchLanguages().sortedBy(Language::englishName))
                removeAll { it.iso6391 == Language.default.iso6391 }
                add(0, Language.default)
            } catch (_: Exception) {
            }
        }
        _uiState.update { it.copy(showLoading = false, languages = languages) }
    }

    fun onLanguageSelected(language: Language) {
        viewModelScope.launch {
            languageDataStore.onLanguageSelected(language)
            _uiState.update { it.copy(selectedLanguage = language) }
        }
    }

    data class UiState(
        val showLoading: Boolean = false,
        val languages: List<Language> = emptyList(),
        val selectedLanguage: Language = Language.default,
    )
}
