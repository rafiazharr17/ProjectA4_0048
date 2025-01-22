package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DestinasiUpdateManajer
import kotlinx.coroutines.launch

class UpdateManajerViewModel(
    savedStateHandle: SavedStateHandle,
    private val manajerPropertiRepository: ManajerPropertiRepository
): ViewModel() {
    var updateManajerUiState by mutableStateOf(InsertManajerUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateManajer.ID])

    init {
        viewModelScope.launch {
            updateManajerUiState = manajerPropertiRepository.getManajerPropertibyID(_id)
                .toUiStateManajer()
        }
    }

    fun updateInsertManajerState(insertManajerUiEvent: InsertManajerUiEvent){
        updateManajerUiState = InsertManajerUiState(insertManajerUiEvent = insertManajerUiEvent)
    }

    suspend fun updateManajer(){
        viewModelScope.launch {
            try {
                manajerPropertiRepository.updateManajerProperti(_id, updateManajerUiState.insertManajerUiEvent.toManajer())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}