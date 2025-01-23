package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.repository.JenisPropertiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeJenisuiState{
    data class Success(
        val jenisProperti: List<JenisProperti>
    ): HomeJenisuiState()
    object Error: HomeJenisuiState()
    object Loading: HomeJenisuiState()
}

class HomeJenisViewModel(
    private val jenisPropertiRepository: JenisPropertiRepository
): ViewModel(){
    var jenisUiState: HomeJenisuiState by mutableStateOf(HomeJenisuiState.Loading)
        private set

    init {
        getJenis()
    }

    fun getJenis(){
        viewModelScope.launch {
            jenisUiState = HomeJenisuiState.Loading
            jenisUiState = try {
                HomeJenisuiState.Success(jenisPropertiRepository.getJenisProperti().data)
            } catch (e: IOException){
                HomeJenisuiState.Error
            } catch (e: HttpException){
                HomeJenisuiState.Error
            }
        }
    }

    fun deleteJenis(idJenis: Int){
        viewModelScope.launch {
            try {
                jenisPropertiRepository.deleteJenisProperti(idJenis)
            } catch (e: IOException){
                HomeJenisuiState.Error
            } catch (e: HttpException){
                HomeJenisuiState.Error
            }
        }
    }
}