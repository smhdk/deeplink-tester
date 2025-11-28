package com.kodmap.deeplinktester.ui.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodmap.deeplinktester.data.entity.AppWithDeeplinks
import com.kodmap.deeplinktester.data.repository.DeeplinkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class AppListUiState(
    val apps: List<AppWithDeeplinks> = emptyList(),
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false,
    val error: String? = null
)

class AppListViewModel(
    private val repository: DeeplinkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppListUiState())
    val uiState: StateFlow<AppListUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            repository.getAllAppsWithDeeplinks()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                .collect { apps ->
                    _uiState.value = _uiState.value.copy(
                        apps = apps,
                        isLoading = false
                    )
                }
        }
    }

    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true)
    }

    fun hideAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }

    fun addApp(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.insertApp(name.trim())
            hideAddDialog()
        }
    }

    fun deleteApp(appWithDeeplinks: AppWithDeeplinks) {
        viewModelScope.launch {
            repository.deleteApp(appWithDeeplinks.app)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
