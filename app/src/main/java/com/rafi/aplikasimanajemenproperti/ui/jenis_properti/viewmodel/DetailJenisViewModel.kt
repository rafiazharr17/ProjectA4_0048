package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.DestinasiDetailJenis
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailJenisUiState {
    data class Success(val jenisProperti: JenisProperti) : DetailJenisUiState()
    object Error : DetailJenisUiState()
    object Loading : DetailJenisUiState()
}

class DetailJenisViewModel (
    savedStateHandle: SavedStateHandle,
    private val jenisPropertiRepository: JenisPropertiRepository
) : ViewModel(){
    var jenisDetailState: DetailJenisUiState by mutableStateOf(DetailJenisUiState.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailJenis.ID])

    init {
        getJenisbyID()
    }

    fun getJenisbyID() {
        viewModelScope.launch {
            jenisDetailState = DetailJenisUiState.Loading
            jenisDetailState = try {
                val jenis = jenisPropertiRepository.getJenisPropertibyID(_id)
                DetailJenisUiState.Success(jenis)
            } catch (e: IOException) {
                DetailJenisUiState.Error
            } catch (e: HttpException) {
                DetailJenisUiState.Error
            }
        }
    }
}