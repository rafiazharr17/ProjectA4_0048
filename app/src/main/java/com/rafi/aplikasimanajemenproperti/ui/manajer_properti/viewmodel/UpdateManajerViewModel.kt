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

    fun validateManajerFields(): Boolean {
        val event = updateManajerUiState.insertManajerUiEvent
        val errorState = FormManajerErrorState(
            namaManajer = if (event.namaManajer.isNotEmpty()) null else "Nama Manajer tidak boleh kosong",
            kontakManajer = if (event.kontakManajer.isNotEmpty()) null else "Kontak tidak boleh kosong"
        )

        updateManajerUiState = updateManajerUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun updateManajer(){
        val currentEvent = updateManajerUiState.insertManajerUiEvent

        if (validateManajerFields()){
            viewModelScope.launch {
                try {
                    manajerPropertiRepository.updateManajerProperti(_id, currentEvent.toManajer())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}