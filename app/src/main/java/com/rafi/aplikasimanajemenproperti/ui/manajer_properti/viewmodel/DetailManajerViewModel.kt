package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.repository.ManajerPropertiRepository
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view.DestinasiDetailManajer
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailManajerUiState {
    data class Success(val manajerProperti: ManajerProperti) : DetailManajerUiState()
    object Error : DetailManajerUiState()
    object Loading : DetailManajerUiState()
}

class DetailManajerViewModel (
    savedStateHandle: SavedStateHandle,
    private val manajerPropertiRepository: ManajerPropertiRepository
) : ViewModel(){
    var manajerDetailState: DetailManajerUiState by mutableStateOf(DetailManajerUiState.Loading)
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiDetailManajer.ID])

    init {
        getManajerbyID()
    }

    fun getManajerbyID() {
        viewModelScope.launch {
            manajerDetailState = DetailManajerUiState.Loading
            manajerDetailState = try {
                val pemilik = manajerPropertiRepository.getManajerPropertibyID(_id)
                DetailManajerUiState.Success(pemilik)
            } catch (e: IOException) {
                DetailManajerUiState.Error
            } catch (e: HttpException) {
                DetailManajerUiState.Error
            }
        }
    }
}