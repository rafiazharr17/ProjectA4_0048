package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeManajeruiState{
    data class Success(
        val manajer: List<ManajerProperti>
    ): HomeManajeruiState()
    object Error: HomeManajeruiState()
    object Loading: HomeManajeruiState()
}

class HomeManajerViewModel(
    private val manajerPropertiRepository: ManajerPropertiRepository
): ViewModel(){
    var manajerUiState: HomeManajeruiState by mutableStateOf(HomeManajeruiState.Loading)
        private set

    init {
        getManajer()
    }

    fun getManajer(){
        viewModelScope.launch {
            manajerUiState = HomeManajeruiState.Loading
            manajerUiState = try {
                HomeManajeruiState.Success(manajerPropertiRepository.getManajerProperti().data)
            } catch (e: IOException){
                HomeManajeruiState.Error
            } catch (e: HttpException){
                HomeManajeruiState.Error
            }
        }
    }

    fun deleteManajer(idManajer: Int){
        viewModelScope.launch {
            try {
                manajerPropertiRepository.deleteManajerProperti(idManajer)
            } catch (e: IOException){
                HomeManajeruiState.Error
            } catch (e: HttpException){
                HomeManajeruiState.Error
            }
        }
    }
}