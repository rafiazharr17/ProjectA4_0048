package com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiDetailPemilik
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPemilikUiState {
    data class Success(val pemilik: Pemilik) : DetailPemilikUiState()
    object Error : DetailPemilikUiState()
    object Loading : DetailPemilikUiState()
}

class DetailPemilikViewModel (
    savedStateHandle: SavedStateHandle,
    private val pemilikRepository: PemilikRepository
) : ViewModel(){
    var pemilikDetailState: DetailPemilikUiState by mutableStateOf(DetailPemilikUiState.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailPemilik.ID])

    init {
        getPemilikbyID()
    }

    fun getPemilikbyID() {
        viewModelScope.launch {
            pemilikDetailState = DetailPemilikUiState.Loading
            pemilikDetailState = try {
                val pemilik = pemilikRepository.getPemilikbyID(_id)
                DetailPemilikUiState.Success(pemilik)
            } catch (e: IOException) {
                DetailPemilikUiState.Error
            } catch (e: HttpException) {
                DetailPemilikUiState.Error
            }
        }
    }
}