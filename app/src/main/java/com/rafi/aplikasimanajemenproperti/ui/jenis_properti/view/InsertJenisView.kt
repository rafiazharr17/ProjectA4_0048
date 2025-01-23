package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.FormJenisErrorState
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.InsertJenisUiEvent
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.InsertJenisUiState
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.InsertJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInsertJenis: DestinasiNavigasi {
    override val route = "insert_jenis"
    override val titleRes = "Tambah Jenis Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertJenisView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertJenis.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        InsertBodyJenis(
            uiState = viewModel.uiEvent,
            onValueChange = viewModel::updateInsertJenisState,
            onClick = {
                if (viewModel.validateJenisFields()){
                    coroutineScope.launch {
                        viewModel.insertJenis()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InsertBodyJenis(
    modifier: Modifier = Modifier,
    onValueChange: (InsertJenisUiEvent) -> Unit,
    uiState: InsertJenisUiState,
    onClick: () -> Unit
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormJenis(
            insertJenisUiEvent = uiState.insertJenisUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button (
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormJenis(
    insertJenisUiEvent: InsertJenisUiEvent = InsertJenisUiEvent(),
    onValueChange: (InsertJenisUiEvent) -> Unit,
    errorState: FormJenisErrorState = FormJenisErrorState(),
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertJenisUiEvent.namaJenis,
            onValueChange = {
                onValueChange(insertJenisUiEvent.copy(namaJenis = it))
            },
            label = { Text("Nama Jenis Properti") },
            isError = errorState.namaJenis != null,
            placeholder = { Text("Masukkan Nama Jenis Properti") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.namaJenis ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertJenisUiEvent.deskripsiJenis,
            onValueChange = {
                onValueChange(insertJenisUiEvent.copy(deskripsiJenis = it))
            },
            label = { Text("Deskripsi") },
            isError = errorState.deskripsiJenis != null,
            placeholder = { Text("Masukkan Deskripsi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.deskripsiJenis ?: "",
            color = Color.Red
        )

        Divider(thickness = 5.dp)

        Text("ISI SEMUA DATA")
    }
}