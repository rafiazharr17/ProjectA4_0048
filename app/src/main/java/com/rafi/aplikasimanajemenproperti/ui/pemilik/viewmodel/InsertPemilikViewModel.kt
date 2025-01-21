package com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import kotlinx.coroutines.launch

class InsertPemilikViewModel(
    private val pemilikRepository: PemilikRepository
): ViewModel(){
    var uiEvent: InsertPemilikUiState by mutableStateOf(InsertPemilikUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateInsertPemilikState(insertPemilikUiEvent: InsertPemilikUiEvent){
        uiEvent = uiEvent.copy(
            insertPemilikUiEvent = insertPemilikUiEvent
        )
    }

    fun validatePemilikFields(): Boolean {
        val event = uiEvent.insertPemilikUiEvent
        val errorState = FormPemilikErrorState(
            namaPemilik = if (event.namaPemilik.isNotEmpty()) null else "Nama Pemilik tidak boleh kosong",
            kontakPemilik = if (event.kontakPemilik.isNotEmpty()) null else "Kontak tidak boleh kosong"
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertPemilik(){
        if (validatePemilikFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    pemilikRepository.insertPemilik(uiEvent.insertPemilikUiEvent.toPemilik())
                    uiState = FormState.Success("Data Berhasil Disimpan")
                } catch (e: Exception) {
                    uiState = FormState.Error("Data Gagal Disimpan")
                }
            }
        } else {
            uiState = FormState.Error("Data Tidak Valid")
        }
    }

    fun resetSnackBarMessage(){
        uiState = FormState.Idle
    }
}

sealed class FormState {
    object Idle : FormState()
    object Loading : FormState()
    data class Success(val message: String) : FormState()
    data class Error(val message: String) : FormState()
}

data class InsertPemilikUiEvent(
    val idPemilik: Int = 0,
    val namaPemilik: String = "",
    val kontakPemilik: String = "",
)

data class InsertPemilikUiState(
    val insertPemilikUiEvent: InsertPemilikUiEvent = InsertPemilikUiEvent(),
    val isEntryValid: FormPemilikErrorState = FormPemilikErrorState()
)

fun InsertPemilikUiEvent.toPemilik(): Pemilik = Pemilik(
    idPemilik = idPemilik,
    namaPemilik = namaPemilik,
    kontakPemilik = kontakPemilik
)

fun Pemilik.toInsertPemilikUiEvent(): InsertPemilikUiEvent = InsertPemilikUiEvent(
    idPemilik = idPemilik,
    namaPemilik = namaPemilik,
    kontakPemilik = kontakPemilik
)

fun Pemilik.toUiStatePemilik(): InsertPemilikUiState = InsertPemilikUiState(
    insertPemilikUiEvent = toInsertPemilikUiEvent()
)

data class FormPemilikErrorState(
    val namaPemilik: String? = null,
    val kontakPemilik: String? = null,
){
    fun isValid() : Boolean {
        return namaPemilik == null && kontakPemilik == null
    }
}