package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.UpdateManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateManajer : DestinasiNavigasi {
    override val route = "update_manajer"
    override val titleRes = "Update Manajer"
    const val ID = "idManajer"
    val routeWithArg = "$route/{$ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateManajerView(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateManajer.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                onRefresh = {},
                navigateUp = onBack
            )
        }
    ){ padding ->
        InsertBodyManajer(
            modifier = Modifier.padding(padding),
            onValueChange = viewModel::updateInsertManajerState,
            uiState = viewModel.updateManajerUiState,
            onClick = {
                coroutineScope.launch {
                    if (viewModel.validateManajerFields()){
                        viewModel.updateManajer()
                        delay(600)
                        withContext(Dispatchers.Main){
                            onNavigate()
                        }
                    }
                }
            }
        )
    }
}