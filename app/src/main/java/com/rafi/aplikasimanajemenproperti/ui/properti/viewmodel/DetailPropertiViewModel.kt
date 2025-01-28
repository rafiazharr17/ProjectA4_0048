package com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.model.Properti
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import com.rafi.aplikasimanajemenproperti.repository.PropertiRepository
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiDetailProperti
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPropertiUiState {
    data class Success(val properti: Properti) : DetailPropertiUiState()
    object Error : DetailPropertiUiState()
    object Loading : DetailPropertiUiState()
}

class DetailPropertiViewModel (
    savedStateHandle: SavedStateHandle,
    private val propertiRepository: PropertiRepository,
    private val pemilikRepository: PemilikRepository,
    private val jenisPropertiRepository: JenisPropertiRepository,
    private val manajerPropertiRepository: ManajerPropertiRepository
) : ViewModel(){
    var propertiDetailState: DetailPropertiUiState by mutableStateOf(DetailPropertiUiState.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailProperti.ID])

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
        getPropertibyID()
        viewModelScope.launch {
            loadManajer()
            loadJenis()
            loadPemilik()
        }
    }

    fun getPropertibyID() {
        viewModelScope.launch {
            propertiDetailState = DetailPropertiUiState.Loading
            propertiDetailState = try {
                val properti = propertiRepository.getPropertibyID(_id)
                DetailPropertiUiState.Success(properti)
            } catch (e: IOException) {
                DetailPropertiUiState.Error
            } catch (e: HttpException) {
                DetailPropertiUiState.Error
            }
        }
    }
}