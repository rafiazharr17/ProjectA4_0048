package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view

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
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.FormManajerErrorState
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.InsertManajerUiEvent
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.InsertManajerUiState
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.InsertManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInsertManajer: DestinasiNavigasi {
    override val route = "insert_manajer"
    override val titleRes = "Tambah Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertManajerView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertManajer.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        InsertBodyManajer(
            uiState = viewModel.uiEvent,
            onValueChange = viewModel::updateInsertManajerState,
            onClick = {
                if (viewModel.validateManajerFields()){
                    coroutineScope.launch {
                        viewModel.insertManjer()
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
fun InsertBodyManajer(
    modifier: Modifier = Modifier,
    onValueChange: (InsertManajerUiEvent) -> Unit,
    uiState: InsertManajerUiState,
    onClick: () -> Unit
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormManajer(
            insertManajerUiEvent = uiState.insertManajerUiEvent,
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
fun FormManajer(
    insertManajerUiEvent: InsertManajerUiEvent = InsertManajerUiEvent(),
    onValueChange: (InsertManajerUiEvent) -> Unit,
    errorState: FormManajerErrorState = FormManajerErrorState(),
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertManajerUiEvent.namaManajer,
            onValueChange = {
                onValueChange(insertManajerUiEvent.copy(namaManajer = it))
            },
            label = { Text("Nama Manajer") },
            isError = errorState.namaManajer != null,
            placeholder = { Text("Masukkan Nama Manajer") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.namaManajer ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertManajerUiEvent.kontakManajer,
            onValueChange = {
                onValueChange(insertManajerUiEvent.copy(kontakManajer = it))
            },
            label = { Text("Kontak") },
            isError = errorState.kontakManajer != null,
            placeholder = { Text("Masukkan Kontak") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kontakManajer ?: "",
            color = Color.Red
        )

        Divider(thickness = 5.dp)

        Text("ISI SEMUA DATA")
    }
}