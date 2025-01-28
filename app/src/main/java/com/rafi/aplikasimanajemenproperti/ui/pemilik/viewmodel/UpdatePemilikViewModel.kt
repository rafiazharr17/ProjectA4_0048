package com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiUpdatePemilik
import kotlinx.coroutines.launch

class UpdatePemilikViewModel (
    savedStateHandle: SavedStateHandle,
    private val pemilikRepository: PemilikRepository
): ViewModel(){
    var updatePemilikUiState by mutableStateOf(InsertPemilikUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdatePemilik.ID])

    init {
        viewModelScope.launch {
            updatePemilikUiState = pemilikRepository.getPemilikbyID(_id)
                .toUiStatePemilik()
        }
    }

    fun updateInsertPemilikState(insertPemilikUiEvent: InsertPemilikUiEvent){
        updatePemilikUiState = InsertPemilikUiState(insertPemilikUiEvent = insertPemilikUiEvent)
    }

    fun validatePemilikFields(): Boolean {
        val event = updatePemilikUiState.insertPemilikUiEvent
        val errorState = FormPemilikErrorState(
            namaPemilik = if (event.namaPemilik.isNotEmpty()) null else "Nama Pemilik tidak boleh kosong",
            kontakPemilik = if (event.kontakPemilik.isNotEmpty()) null else "Kontak tidak boleh kosong"
        )

        updatePemilikUiState = updatePemilikUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun updatePemilik(){
        val currentEvent = updatePemilikUiState.insertPemilikUiEvent

        if (validatePemilikFields()){
            viewModelScope.launch {
                try {
                    pemilikRepository.updatePemilik(_id, currentEvent.toPemilik())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}