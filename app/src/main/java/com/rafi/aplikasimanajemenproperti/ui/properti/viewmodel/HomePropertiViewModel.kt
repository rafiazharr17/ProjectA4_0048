package com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.Properti
import com.rafi.aplikasimanajemenproperti.repository.PropertiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePropertiuiState{
    data class Success(
        val properti: List<Properti>
    ): HomePropertiuiState()
    object Error: HomePropertiuiState()
    object Loading: HomePropertiuiState()
}

class HomePropertiViewModel(
    private val propertiRepository: PropertiRepository
): ViewModel(){
    var propertiUiState: HomePropertiuiState by mutableStateOf(HomePropertiuiState.Loading)
        private set

    init {
        getProperti()
    }

    fun getProperti(){
        viewModelScope.launch {
            propertiUiState = HomePropertiuiState.Loading
            propertiUiState = try {
                HomePropertiuiState.Success(propertiRepository.getProperti().data)
            } catch (e: IOException){
                HomePropertiuiState.Error
            } catch (e: HttpException){
                HomePropertiuiState.Error
            }
        }
    }

    fun deleteProperti(idProperti: Int){
        viewModelScope.launch {
            try {
                propertiRepository.deleteProperti(idProperti)
            } catch (e: IOException){
                HomePropertiuiState.Error
            } catch (e: HttpException){
                HomePropertiuiState.Error
            }
        }
    }
}