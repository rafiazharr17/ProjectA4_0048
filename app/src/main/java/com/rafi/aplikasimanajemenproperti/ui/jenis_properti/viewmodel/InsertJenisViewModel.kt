package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import kotlinx.coroutines.launch

class InsertJenisViewModel(
    private val jenisPropertiRepository: JenisPropertiRepository
): ViewModel(){
    var uiEvent: InsertJenisUiState by mutableStateOf(InsertJenisUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateInsertJenisState(insertJenisUiEvent: InsertJenisUiEvent){
        uiEvent = uiEvent.copy(
            insertJenisUiEvent = insertJenisUiEvent
        )
    }

    fun validateJenisFields(): Boolean {
        val event = uiEvent.insertJenisUiEvent
        val errorState = FormJenisErrorState(
            namaJenis = if (event.namaJenis.isNotEmpty()) null else "Nama Jenis tidak boleh kosong",
            deskripsiJenis = if (event.deskripsiJenis.isNotEmpty()) null else "Deskripsi tidak boleh kosong"
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertJenis(){
        val currentEvent = uiEvent.insertJenisUiEvent

        if (validateJenisFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    jenisPropertiRepository.insertJenisProperti(currentEvent.toJenis())
                    uiState = FormState.Success("Data Berhasil Disimpan")
                } catch (e: Exception) {
                    uiState = FormState.Error("Data Gagal Disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data Tidak Valid")
        }
    }
}

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

data class InsertJenisUiEvent(
    val idJenis: Int = 0,
    val namaJenis: String = "",
    val deskripsiJenis: String = "",
)

data class InsertJenisUiState(
    val insertJenisUiEvent: InsertJenisUiEvent = InsertJenisUiEvent(),
    val isEntryValid: FormJenisErrorState = FormJenisErrorState()
)

fun InsertJenisUiEvent.toJenis(): JenisProperti = JenisProperti(
    idJenis = idJenis,
    namaJenis = namaJenis,
    deskripsiJenis = deskripsiJenis
)

fun JenisProperti.toInsertJenisUiEvent(): InsertJenisUiEvent = InsertJenisUiEvent(
    idJenis = idJenis,
    namaJenis = namaJenis,
    deskripsiJenis = deskripsiJenis
)

fun JenisProperti.toUiStateJenis(): InsertJenisUiState = InsertJenisUiState(
    insertJenisUiEvent = toInsertJenisUiEvent()
)

data class FormJenisErrorState(
    val namaJenis: String? = null,
    val deskripsiJenis: String? = null,
){
    fun isValid() : Boolean {
        return namaJenis == null && deskripsiJenis == null
    }
}