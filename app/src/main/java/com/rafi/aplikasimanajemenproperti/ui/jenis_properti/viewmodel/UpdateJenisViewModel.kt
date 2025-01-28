package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DestinasiUpdateJenis
import kotlinx.coroutines.launch

class UpdateJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisPropertiRepository: JenisPropertiRepository
): ViewModel() {
    var updateJenisUiState by mutableStateOf(InsertJenisUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateJenis.ID])

    init {
        viewModelScope.launch {
            updateJenisUiState = jenisPropertiRepository.getJenisPropertibyID(_id)
                .toUiStateJenis()
        }
    }

    fun updateInsertJenisState(insertJenisUiEvent: InsertJenisUiEvent){
        updateJenisUiState = InsertJenisUiState(insertJenisUiEvent = insertJenisUiEvent)
    }

    fun validateJenisFields(): Boolean {
        val event = updateJenisUiState.insertJenisUiEvent
        val errorState = FormJenisErrorState(
            namaJenis = if (event.namaJenis.isNotEmpty()) null else "Nama Jenis tidak boleh kosong",
            deskripsiJenis = if (event.deskripsiJenis.isNotEmpty()) null else "Deskripsi tidak boleh kosong"
        )

        updateJenisUiState = updateJenisUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun updateJenis(){
        val currentEvent = updateJenisUiState.insertJenisUiEvent

        if (validateJenisFields()){
            viewModelScope.launch {
                try {
                    jenisPropertiRepository.updateJenisProperti(_id, currentEvent.toJenis())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}