package com.rafi.aplikasimanajemenproperti.ui.pemilik.view

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.FormPemilikErrorState
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.FormState
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.InsertPemilikUiEvent
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.InsertPemilikUiState
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.InsertPemilikViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DestinasiInsertPemilik: DestinasiNavigasi{
    override val route = "insert_pemilik"
    override val titleRes = "Tambah Pemilik"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPemilikView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertPemilik.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        InsertBodyPemilik(
            uiState = viewModel.uiEvent,
            onValueChange = viewModel::updateInsertPemilikState,
            onClick = {
                if (viewModel.validatePemilikFields()){
                    coroutineScope.launch {
                        viewModel.insertPemilik()
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
fun InsertBodyPemilik(
    modifier: Modifier = Modifier,
    onValueChange: (InsertPemilikUiEvent) -> Unit,
    uiState: InsertPemilikUiState,
    onClick: () -> Unit
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormPemilik(
            insertPemilikUiEvent = uiState.insertPemilikUiEvent,
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
fun FormPemilik(
    insertPemilikUiEvent: InsertPemilikUiEvent = InsertPemilikUiEvent(),
    onValueChange: (InsertPemilikUiEvent) -> Unit,
    errorState: FormPemilikErrorState = FormPemilikErrorState(),
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPemilikUiEvent.namaPemilik,
            onValueChange = {
                onValueChange(insertPemilikUiEvent.copy(namaPemilik = it))
            },
            label = { Text("Nama Pemilik") },
            isError = errorState.namaPemilik != null,
            placeholder = { Text("Masukkan Nama Pemilik") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.namaPemilik ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPemilikUiEvent.kontakPemilik,
            onValueChange = {
                onValueChange(insertPemilikUiEvent.copy(kontakPemilik = it))
            },
            label = { Text("Kontak") },
            isError = errorState.kontakPemilik != null,
            placeholder = { Text("Masukkan Kontak") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kontakPemilik ?: "",
            color = Color.Red
        )

        Divider(thickness = 5.dp)

        Text("ISI SEMUA DATA")
    }
}