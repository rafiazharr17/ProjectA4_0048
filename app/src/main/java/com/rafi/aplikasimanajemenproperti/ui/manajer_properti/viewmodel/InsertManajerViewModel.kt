package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import kotlinx.coroutines.launch

class InsertManajerViewModel(
    private val manajerPropertiRepository: ManajerPropertiRepository
): ViewModel(){
    var uiEvent: InsertManajerUiState by mutableStateOf(InsertManajerUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

    fun updateInsertManajerState(insertManajerUiEvent: InsertManajerUiEvent){
        uiEvent = uiEvent.copy(
            insertManajerUiEvent = insertManajerUiEvent
        )
    }

    fun validateManajerFields(): Boolean {
        val event = uiEvent.insertManajerUiEvent
        val errorState = FormManajerErrorState(
            namaManajer = if (event.namaManajer.isNotEmpty()) null else "Nama Manajer tidak boleh kosong",
            kontakManajer = if (event.kontakManajer.isNotEmpty()) null else "Kontak tidak boleh kosong"
        )

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertManjer(){
        val currentEvent = uiEvent.insertManajerUiEvent

        if (validateManajerFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    manajerPropertiRepository.insertManajerProperti(currentEvent.toManajer())
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

data class InsertManajerUiEvent(
    val idManajer: Int = 0,
    val namaManajer: String = "",
    val kontakManajer: String = "",
)

data class InsertManajerUiState(
    val insertManajerUiEvent: InsertManajerUiEvent = InsertManajerUiEvent(),
    val isEntryValid: FormManajerErrorState = FormManajerErrorState()
)

fun InsertManajerUiEvent.toManajer(): ManajerProperti = ManajerProperti(
    idManajer = idManajer,
    namaManajer = namaManajer,
    kontakManajer = kontakManajer
)

fun ManajerProperti.toInsertManajerUiEvent(): InsertManajerUiEvent = InsertManajerUiEvent(
    idManajer = idManajer,
    namaManajer = namaManajer,
    kontakManajer = kontakManajer
)

fun ManajerProperti.toUiStateManajer(): InsertManajerUiState = InsertManajerUiState(
    insertManajerUiEvent = toInsertManajerUiEvent()
)

data class FormManajerErrorState(
    val namaManajer: String? = null,
    val kontakManajer: String? = null,
){
    fun isValid() : Boolean {
        return namaManajer == null && kontakManajer == null
    }
}