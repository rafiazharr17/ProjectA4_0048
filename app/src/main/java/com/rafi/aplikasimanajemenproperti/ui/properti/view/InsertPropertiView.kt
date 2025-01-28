package com.rafi.aplikasimanajemenproperti.ui.properti.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.customwidget.GeneralDropdown
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel.FormPropertiErrorState
import com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel.InsertPropertiUiEvent
import com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel.InsertPropertiUiState
import com.rafi.aplikasimanajemenproperti.ui.properti.viewmodel.InsertPropertiViewModel
import kotlinx.coroutines.launch

object DestinasiInsertProperti: DestinasiNavigasi {
    override val route = "insert_properti"
    override val titleRes = "Tambah Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPropertiView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPropertiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertProperti.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {},
                navigateUp = navigateBack
            )
        }
    ){ innerPadding ->
        InsertBodyProperti(
            uiState = viewModel.uiEvent,
            onValueChange = viewModel::updateInsertPropertiState,
            onClick = {
                if (viewModel.validatePropertiFields()) {
                    coroutineScope.launch {
                        viewModel.insertProperti()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            jenisList = viewModel.jenisList,
            pemilikList = viewModel.pemilikList,
            manajerList = viewModel.manajerList,
        )
    }
}

@Composable
fun InsertBodyProperti(
    modifier: Modifier = Modifier,
    onValueChange: (InsertPropertiUiEvent) -> Unit,
    uiState: InsertPropertiUiState,
    onClick: () -> Unit,
    jenisList: List<JenisProperti>,
    pemilikList: List<Pemilik>,
    manajerList: List<ManajerProperti>,
){
    if (pemilikList.isEmpty() || jenisList.isEmpty() || manajerList.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }

    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormProperti(
            insertPropertiUiEvent = uiState.insertPropertiUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            jenisList = jenisList,
            pemilikList = pemilikList,
            manajerList = manajerList
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
fun FormProperti(
    jenisList: List<JenisProperti>,
    pemilikList: List<Pemilik>,
    manajerList: List<ManajerProperti>,
    insertPropertiUiEvent: InsertPropertiUiEvent = InsertPropertiUiEvent(),
    onValueChange: (InsertPropertiUiEvent) -> Unit,
    errorState: FormPropertiErrorState = FormPropertiErrorState(),
    modifier: Modifier = Modifier
){
    val statusProperti = listOf("Tersedia", "Tersewa", "Dijual")

    Column (
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPropertiUiEvent.namaProperti,
            onValueChange = {
                onValueChange(insertPropertiUiEvent.copy(namaProperti = it))
            },
            label = { Text("Nama Properti") },
            isError = errorState.namaProperti != null,
            placeholder = { Text("Masukkan Nama Properti") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.namaProperti ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPropertiUiEvent.deskripsiProperti,
            onValueChange = {
                onValueChange(insertPropertiUiEvent.copy(deskripsiProperti = it))
            },
            label = { Text("Deskripsi") },
            isError = errorState.deskripsiProperti != null,
            placeholder = { Text("Masukkan Deskripsi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.deskripsiProperti ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPropertiUiEvent.lokasi,
            onValueChange = {
                onValueChange(insertPropertiUiEvent.copy(lokasi = it))
            },
            label = { Text("Lokasi") },
            isError = errorState.lokasi != null,
            placeholder = { Text("Masukkan Lokasi") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.lokasi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = insertPropertiUiEvent.harga.toString(),
            onValueChange = {
                val newHarga = if (it.isEmpty()) 0 else it.toIntOrNull() ?: 0
                onValueChange(insertPropertiUiEvent.copy(harga = newHarga))
            },
            label = { Text("Harga") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan Harga") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.harga ?: "",
            color = Color.Red
        )

        Text(text = "Status Properti")
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            statusProperti.forEach { sp ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = insertPropertiUiEvent.statusProperti == sp,
                        onClick = {
                            onValueChange(insertPropertiUiEvent.copy(statusProperti = sp))
                        },
                    )
                    Text(text = sp,)
                }
            }
        }
        Text(
            text = errorState.statusProperti ?: "",
            color = Color.Red
        )

        if (pemilikList.isNotEmpty()) {
            GeneralDropdown(
                label = "Pemilik Properti",
                itemList = pemilikList,
                selectedId = insertPropertiUiEvent.idPemilik,
                onItemSelected = { idPemilik ->
                    onValueChange(insertPropertiUiEvent.copy(idPemilik = idPemilik))
                },
                getItemLabel = { it.namaPemilik },
                getItemId = { it.idPemilik }
            )
            Text(text = errorState.idPemilik ?: "", color = Color.Red)
        }

        if (jenisList.isNotEmpty()) {
            GeneralDropdown(
                label = "Jenis Properti",
                itemList = jenisList,
                selectedId = insertPropertiUiEvent.idJenis,
                onItemSelected = { idJenis ->
                    onValueChange(insertPropertiUiEvent.copy(idJenis = idJenis))
                },
                getItemLabel = { it.namaJenis },
                getItemId = { it.idJenis }
            )
            Text(text = errorState.idJenis ?: "", color = Color.Red)
        }

        if (manajerList.isNotEmpty()) {
            GeneralDropdown(
                label = "Manajer Properti",
                itemList = manajerList,
                selectedId = insertPropertiUiEvent.idManajer,
                onItemSelected = { idManajer ->
                    onValueChange(insertPropertiUiEvent.copy(idManajer = idManajer))
                },
                getItemLabel = { it.namaManajer },
                getItemId = { it.idManajer }
            )
            Text(text = errorState.idManajer ?: "", color = Color.Red)
        }

        Divider(thickness = 5.dp)

        Text("ISI SEMUA DATA")
    }
}