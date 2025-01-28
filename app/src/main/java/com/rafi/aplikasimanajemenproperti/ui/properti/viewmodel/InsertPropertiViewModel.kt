package com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafi.aplikasimanajemenproperti.model.AllJenisResponse
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.model.Properti
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import com.rafi.aplikasimanajemenproperti.repository.PropertiRepository
import kotlinx.coroutines.launch

class InsertPropertiViewModel(
    private val propertiRepository: PropertiRepository,
    private val pemilikRepository: PemilikRepository,
    private val jenisPropertiRepository: JenisPropertiRepository,
    private val manajerPropertiRepository: ManajerPropertiRepository
): ViewModel(){
    var uiEvent: InsertPropertiUiState by mutableStateOf(InsertPropertiUiState())
        private set

    var uiState: FormState by mutableStateOf(FormState.Idle)
        private set

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
            loadPemilik()
            loadJenis()
            loadManajer()
        }
    }

    fun updateInsertPropertiState(insertPropertiUiEvent: InsertPropertiUiEvent){
        uiEvent = uiEvent.copy(
            insertPropertiUiEvent = insertPropertiUiEvent
        )
    }

    fun validatePropertiFields(): Boolean {
        val event = uiEvent.insertPropertiUiEvent
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

        uiEvent = uiEvent.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertProperti(){
        val currentEvent = uiEvent.insertPropertiUiEvent

        if (validatePropertiFields()) {
            viewModelScope.launch {
                uiState = FormState.Loading
                try {
                    propertiRepository.insertProperti(currentEvent.toProperti())
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

data class InsertPropertiUiEvent(
    val idProperti: Int = 0,
    val namaProperti: String = "",
    val deskripsiProperti: String = "",
    val lokasi: String = "",
    val harga: Int = 0,
    val statusProperti: String = "",
    val idJenis: Int = 0,
    val idPemilik: Int = 0,
    val idManajer: Int = 0,
)

data class InsertPropertiUiState(
    val insertPropertiUiEvent: InsertPropertiUiEvent = InsertPropertiUiEvent(),
    val isEntryValid: FormPropertiErrorState = FormPropertiErrorState()
)

fun InsertPropertiUiEvent.toProperti(): Properti = Properti(
    idProperti = idProperti,
    namaProperti = namaProperti,
    deskripsiProperti = deskripsiProperti,
    lokasi = lokasi,
    harga = harga,
    statusProperti = statusProperti,
    idJenis = idJenis,
    idPemilik = idPemilik,
    idManajer = idManajer
)

fun Properti.toInsertPropertiUiEvent(): InsertPropertiUiEvent = InsertPropertiUiEvent(
    idProperti = idProperti,
    namaProperti = namaProperti,
    deskripsiProperti = deskripsiProperti,
    lokasi = lokasi,
    harga = harga,
    statusProperti = statusProperti,
    idJenis = idJenis,
    idPemilik = idPemilik,
    idManajer = idManajer
)

fun Properti.toUiStateProperti(): InsertPropertiUiState = InsertPropertiUiState(
    insertPropertiUiEvent = toInsertPropertiUiEvent()
)

data class FormPropertiErrorState(
    val namaProperti: String? = null,
    val deskripsiProperti: String? = null,
    val lokasi: String? = null,
    val harga: String? = null,
    val statusProperti: String? = null,
    val idJenis: String? = null,
    val idPemilik: String? = null,
    val idManajer: String? = null,
){
    fun isValid() : Boolean {
        return namaProperti == null
                && deskripsiProperti == null
                && lokasi == null
                && harga == null
                && statusProperti == null
                && idJenis == null
                && idPemilik == null
                && idManajer == null
    }
}