package com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.repository.PemilikRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeuiState{
    data class Success(
        val pemilik: List<Pemilik>
    ): HomeuiState()
    object Error: HomeuiState()
    object Loading: HomeuiState()
}

class HomePemilikViewModel(
    private val pemilikRepository: PemilikRepository
): ViewModel(){
    var pemilikUiState: HomeuiState by mutableStateOf(HomeuiState.Loading)
        private set

    init {
        getPemilik()
    }

    fun getPemilik(){
        viewModelScope.launch {
            pemilikUiState = HomeuiState.Loading
            pemilikUiState = try {
                HomeuiState.Success(pemilikRepository.getPemilik().data)
            } catch (e: IOException){
                HomeuiState.Error
            } catch (e: HttpException){
                HomeuiState.Error
            }
        }
    }

    fun deletePemilik(idPemilik: Int){
        viewModelScope.launch {
            try {
                pemilikRepository.deletePemilik(idPemilik)
            } catch (e: IOException){
                HomeuiState.Error
            } catch (e: HttpException){
                HomeuiState.Error
            }
        }
    }
}