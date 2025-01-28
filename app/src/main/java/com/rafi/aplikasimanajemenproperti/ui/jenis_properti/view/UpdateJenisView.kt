package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view

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
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.UpdateJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateJenis : DestinasiNavigasi {
    override val route = "update_jenis"
    override val titleRes = "Update Jenis Properti"
    const val ID = "idJenis"
    val routeWithArg = "$route/{$ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisView(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateJenis.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                onRefresh = {},
                navigateUp = onBack
            )
        }
    ){ padding ->
        InsertBodyJenis(
            modifier = Modifier.padding(padding),
            onValueChange = viewModel::updateInsertJenisState,
            uiState = viewModel.updateJenisUiState,
            onClick = {
                coroutineScope.launch {
                    if (viewModel.validateJenisFields()){
                        viewModel.updateJenis()
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