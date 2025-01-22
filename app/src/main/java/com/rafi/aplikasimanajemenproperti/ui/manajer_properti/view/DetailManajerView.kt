package com.rafi.aplikasimanajemenproperti.ui.manajer_properti.view

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
import com.rafi.aplikasimanajemenproperti.model.ManajerProperti
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.DetailManajerUiState
import com.rafi.aplikasimanajemenproperti.ui.manajer_properti.viewmodel.DetailManajerViewModel
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import com.rafi.aplikasimanajemenproperti.ui.pemilik.view.DestinasiDetailPemilik

object DestinasiDetailManajer : DestinasiNavigasi {
    override val route = "detail_manajer"
    override val titleRes = "Detail Manajer Properti"
    const val ID = "idManajer"
    val routeWithArg = "$route/{$ID}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailManajerView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPemilik.titleRes,
                canNavigateBack = true,
                canRefresh = false,
                onRefresh = {},
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        DetailManajerStatus(
            modifier = Modifier.padding(innerPadding),
            detailManajerUiState = viewModel.manajerDetailState,
            retryAction = { viewModel.getManajerbyID() }
        )
    }
}

@Composable
fun DetailManajerStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailManajerUiState: DetailManajerUiState
) {
    when (detailManajerUiState) {
        is DetailManajerUiState.Loading -> com.rafi.aplikasimanajemenproperti.ui.pemilik.view.OnLoading(
            modifier = modifier.fillMaxSize()
        )

        is DetailManajerUiState.Success -> {
            if (detailManajerUiState.manajerProperti.idManajer.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailManajer(
                    manajerProperti = detailManajerUiState.manajerProperti,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailManajerUiState.Error -> com.rafi.aplikasimanajemenproperti.ui.pemilik.view.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailManajer(
    modifier: Modifier = Modifier,
    manajerProperti: ManajerProperti
) {
    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailManajer(judul = "ID Manajer", isinya = manajerProperti.idManajer.toString())
            ComponentDetailManajer(judul = "Nama Manajer", isinya = manajerProperti.namaManajer)
            ComponentDetailManajer(judul = "Kontak Manajer", isinya = manajerProperti.kontakManajer)
        }
    }
}

@Composable
fun ComponentDetailManajer(
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