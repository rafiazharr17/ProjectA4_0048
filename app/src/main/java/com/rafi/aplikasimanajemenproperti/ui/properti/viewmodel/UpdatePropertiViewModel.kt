package com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import com.rafi.aplikasimanajemenproperti.repository.PropertiRepository
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiUpdateProperti
import kotlinx.coroutines.launch

class UpdatePropertiViewModel (
    savedStateHandle: SavedStateHandle,
    private val propertiRepository: PropertiRepository,
    private val pemilikRepository: PemilikRepository,
    private val jenisPropertiRepository: JenisPropertiRepository,
    private val manajerPropertiRepository: ManajerPropertiRepository,
): ViewModel(){
    var updatePropertiUiState by mutableStateOf(InsertPropertiUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateProperti.ID])

    var pemilikList by mutableStateOf<List<Pemilik>>(listOf())
        private set

    var jenisList by mutableStateOf<List<JenisProperti>>(listOf())
        private set

    var manajerList by mutableStateOf<List<ManajerProperti>>(listOf())
        private set

    private suspend fun loadPemilik(){
        pemilikList = pemilikRepository.getAllPemilik()
        pemilikList.forEach { pemilik ->
            println("Pemilik: ${pemilik.idPemilik}")
        }
    }

    private suspend fun loadJenis(){
        jenisList = jenisPropertiRepository.getAllJenis()
        jenisList.forEach { jenis ->
            println("Jenis: ${jenis.idJenis}")
        }
    }

    private suspend fun loadManajer(){
        manajerList = manajerPropertiRepository.getAllManajer()
        manajerList.forEach { manajer ->
            println("Manajer: ${manajer.idManajer}")
        }
    }

    init {
        viewModelScope.launch {
            val properti = propertiRepository.getPropertibyID(_id)
            updatePropertiUiState = properti.toUiStateProperti()

            loadPemilik()
            loadJenis()
            loadManajer()

            updatePropertiUiState = updatePropertiUiState.copy(
                insertPropertiUiEvent = updatePropertiUiState.insertPropertiUiEvent.copy(
                    idPemilik = pemilikList.find { it.idPemilik == properti.idPemilik }?.idPemilik ?: 0,
                    idJenis = jenisList.find { it.idJenis == properti.idJenis }?.idJenis ?: 0,
                    idManajer = manajerList.find { it.idManajer == properti.idManajer }?.idManajer ?: 0
                )
            )
        }
    }

    fun updateInsertPropertiState(insertPropertiUiEvent: InsertPropertiUiEvent){
        updatePropertiUiState = InsertPropertiUiState(insertPropertiUiEvent = insertPropertiUiEvent)
    }

    fun validatePropertiFields(): Boolean {
        val event = updatePropertiUiState.insertPropertiUiEvent
        val errorState = FormPropertiErrorState(
            namaProperti = if (event.namaProperti.isNotEmpty()) null else "Nama Properti tidak boleh kosong",
            deskripsiProperti = if (event.deskripsiProperti.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            lokasi = if (event.lokasi.isNotEmpty()) null else "Lokasi tidak boleh kosong",
            harga = if (event.harga > 0) null else "Harga tidak boleh kosong",
            statusProperti = if (event.statusProperti.isNotEmpty()) null else "Status tidak boleh kosong",
            idJenis = if (event.idJenis > 0) null else "Jenis tidak boleh kosong",
            idPemilik = if (event.idPemilik > 0) null else "Pemilik tidak boleh kosong",
            idManajer = if (event.idManajer > 0) null else "Manajer tidak boleh kosong",
        )

        updatePropertiUiState = updatePropertiUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun updateProperti(){
        val currentEvent = updatePropertiUiState.insertPropertiUiEvent

        if (validatePropertiFields()){
            viewModelScope.launch {
                try {
                    propertiRepository.updateProperti(_id, currentEvent.toProperti())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}