package com.rafi.aplikasimanajemenproperti.ui.pemilik.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rafi.aplikasimanajemenproperti.R
import com.rafi.aplikasimanajemenproperti.model.Pemilik
import com.rafi.aplikasimanajemenproperti.ui.PenyediaViewModel
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeBottomAppBar
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import com.rafi.aplikasimanajemenproperti.ui.customwidget.TopAppBarHome
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.HomePemilikViewModel
import com.rafi.aplikasimanajemenproperti.ui.pemilik.viewmodel.HomeuiState
import com.rafi.aplikasimanajemenproperti.ui.properti.view.DestinasiHomeProperti

object DestinasiHomePemilik : DestinasiNavigasi {
    override val route = "home_pemilik"
    override val titleRes = "List Pemilik Properti"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePemilikView(
    navigatePemilik: () -> Unit = {},
    navigateManajer: () -> Unit = {},
    navigateProperti: () -> Unit = {},
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    activeMenu: String,
    viewModel: HomePemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarHome(
                onRefresh = {
                    viewModel.getPemilik()
                },
                title = DestinasiHomePemilik.titleRes
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
        bottomBar = {
            CostumeBottomAppBar(
                navigatePemilik = navigatePemilik,
                navigateProperti = navigateProperti,
                navigateManajer = navigateManajer,
                activeMenu = activeMenu
            )
        }
    ){ innerPadding ->
        HomePemilikStatus(
            homeUiState = viewModel.pemilikUiState,
            retryAction = { viewModel.getPemilik() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePemilik(it.idPemilik)
                viewModel.getPemilik()
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun HomePemilikStatus(
    homeUiState: HomeuiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Pemilik) -> Unit = {},
    onEditClick: (String) -> Unit = {}
){
    when (homeUiState) {
        is HomeuiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeuiState.Success ->
            if (homeUiState.pemilik.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pemilik", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                PemilikLayout(
                    pemilik = homeUiState.pemilik,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPemilik.toString())
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {onEditClick(it.idPemilik.toString())}
                )
            }
        is HomeuiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.connection_error),
            contentDescription = ""
        )
        Text(
            text = "Loading Failed",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Retry", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun PemilikLayout(
    pemilik: List<Pemilik>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pemilik) -> Unit,
    onDeleteClick: (Pemilik) -> Unit = {},
    onEditClick: (Pemilik) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pemilik) { pmlk ->
            PemilikCard(
                pemilik = pmlk,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pmlk) },
                onDeleteClick = {
                    onDeleteClick(pmlk)
                },
                onEditClick = {
                    onEditClick(pmlk)
                }
            )
        }
    }
}

@Composable
fun PemilikCard(
    pemilik: Pemilik,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pemilik) -> Unit = {},
    onEditClick: (Pemilik) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card (
        modifier = modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                                )
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = pemilik.namaPemilik.take(1).uppercase(),
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = pemilik.namaPemilik,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp),
                        thickness = 2.dp,
                        color = Color.Black
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = pemilik.kontakPemilik,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.error.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                IconButton(
                    onClick = { onEditClick(pemilik) },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Confirmation dialog for delete action
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDeleteClick(pemilik)
                },
                onDeleteCancel = {
                    deleteConfirmationRequired = false
                }, modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog (
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Delete Data", style = MaterialTheme.typography.titleMedium) },
        text = { Text("Apakah anda yakin ingin menghapus data?", style = MaterialTheme.typography.bodyMedium) },
        modifier = modifier,
        dismissButton = {
            TextButton (onClick = onDeleteCancel) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.primary)
            }
        },
        confirmButton = {
            Button(
                onClick = onDeleteConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Yes", color = MaterialTheme.colorScheme.onError)
            }
        }
    )
}