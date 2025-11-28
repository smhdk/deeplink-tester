package com.kodmap.deeplinktester.ui.deeplinkdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodmap.deeplinktester.data.entity.AppEntity
import com.kodmap.deeplinktester.data.entity.DeeplinkEntity
import com.kodmap.deeplinktester.data.repository.DeeplinkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class DeeplinkDetailUiState(
    val app: AppEntity? = null,
    val deeplinks: List<DeeplinkEntity> = emptyList(),
    val isLoading: Boolean = true,
    val showAddDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val editingDeeplink: DeeplinkEntity? = null,
    val error: String? = null
)

class DeeplinkDetailViewModel(
    private val repository: DeeplinkRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeeplinkDetailUiState())
    val uiState: StateFlow<DeeplinkDetailUiState> = _uiState.asStateFlow()

    fun loadApp(appId: Long) {
        viewModelScope.launch {
            val app = repository.getAppById(appId)
            _uiState.value = _uiState.value.copy(app = app)
            
            if (app != null) {
                loadDeeplinks(appId)
            }
        }
    }

    private fun loadDeeplinks(appId: Long) {
        viewModelScope.launch {
            repository.getDeeplinksForApp(appId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                .collect { deeplinks ->
                    _uiState.value = _uiState.value.copy(
                        deeplinks = deeplinks,
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

    fun showEditDialog(deeplink: DeeplinkEntity) {
        _uiState.value = _uiState.value.copy(
            showEditDialog = true,
            editingDeeplink = deeplink
        )
    }

    fun hideEditDialog() {
        _uiState.value = _uiState.value.copy(
            showEditDialog = false,
            editingDeeplink = null
        )
    }

    fun addDeeplink(link: String) {
        if (link.isBlank()) return
        val appId = _uiState.value.app?.id ?: return
        viewModelScope.launch {
            repository.insertDeeplink(appId, link.trim())
            hideAddDialog()
        }
    }

    fun updateDeeplink(link: String) {
        if (link.isBlank()) return
        val deeplink = _uiState.value.editingDeeplink ?: return
        viewModelScope.launch {
            repository.updateDeeplink(deeplink.copy(link = link.trim()))
            hideEditDialog()
        }
    }

    fun deleteDeeplink(deeplink: DeeplinkEntity) {
        viewModelScope.launch {
            repository.deleteDeeplink(deeplink)
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
