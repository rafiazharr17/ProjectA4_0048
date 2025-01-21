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

    suspend fun updatePemilik(){
        viewModelScope.launch {
            try {
                pemilikRepository.updatePemilik(_id, updatePemilikUiState.insertPemilikUiEvent.toPemilik())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}