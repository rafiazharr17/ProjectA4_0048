package com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.model.JenisProperti
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.DetailJenisUiState
import com.rafi.aplikasimanajemenproperti.ui.jenis_properti.viewmodel.DetailJenisViewModel
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi

object DestinasiDetailJenis : DestinasiNavigasi {
    override val route = "detail_jenis"
    override val titleRes = "Detail Jenis Properti"
    const val ID = "idJenis"
    val routeWithArg = "$route/{$ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailJenis.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                onRefresh = {},
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        DetailJenisStatus(
            modifier = Modifier.padding(innerPadding),
            detailJenisUiState = viewModel.jenisDetailState,
            retryAction = { viewModel.getJenisbyID() }
        )
    }
}

@Composable
fun DetailJenisStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailJenisUiState: DetailJenisUiState
) {
    when (detailJenisUiState) {
        is DetailJenisUiState.Loading -> com.rafi.aplikasimanajemenproperti.ui.pemilik.view.OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailJenisUiState.Success -> {
            if (detailJenisUiState.jenisProperti.idJenis.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailJenis(
                    jenisProperti = detailJenisUiState.jenisProperti,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailJenisUiState.Error -> com.rafi.aplikasimanajemenproperti.ui.jenis_properti.view.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailJenis(
    modifier: Modifier = Modifier,
    jenisProperti: JenisProperti
) {
    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailJenis(judul = "ID Jenis", isinya = jenisProperti.idJenis.toString())
            ComponentDetailJenis(judul = "Nama Jenis", isinya = jenisProperti.namaJenis)
            ComponentDetailJenis(judul = "Deskripsi Jenis", isinya = jenisProperti.deskripsiJenis)
        }
    }
}

@Composable
fun ComponentDetailJenis(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}