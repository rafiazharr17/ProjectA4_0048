package com.rafi.aplikasimanajemenproperti.ui.home

import androidx.compose.animation.core.Spring
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.rafi.aplikasimanajemenproperti.ui.navigation.DestinasiNavigasi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafi.aplikasimanajemenproperti.ui.customwidget.CostumeTopAppBar
import kotlinx.coroutines.delay

object DestinasiHomePage : DestinasiNavigasi {
    override val route = "home_page"
    override val titleRes = "Pilih Menu"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageView(
    onPemilikClick: () -> Unit,
    onPropertiClick: () -> Unit,
    onManajerPropertiClick: () -> Unit,
    onJenisPropertiClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePage.titleRes,
                canNavigateBack = false,
                canRefresh = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {}
            )
        },
        content = { paddingValues ->
            BodyHomeView(
                onPropertiClick = { onPropertiClick() },
                onPemilikClick = { onPemilikClick() },
                onJenisPropertiClick = { onJenisPropertiClick() },
                onManajerPropertiClick = { onManajerPropertiClick() },
                paddingValues = paddingValues,
            )
        }
    )
}

@Composable
fun BodyHomeView(
    onPemilikClick: () -> Unit = {},
    onPropertiClick: () -> Unit = {},
    onManajerPropertiClick: () -> Unit = {},
    onJenisPropertiClick: () -> Unit = {},
    paddingValues: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                EnhancedGradientCard(
                    title = "Properti",
                    icon = Icons.Filled.Home,
                    gradient = Brush.linearGradient(colors = listOf(Color(0xFF4CAF50), Color(0xFF8BC34A))),
                    onClick = onPropertiClick
                )
            }
            item {
                EnhancedGradientCard(
                    title = "Pemilik",
                    icon = Icons.Filled.Person,
                    gradient = Brush.linearGradient(colors = listOf(Color(0xFF2196F3), Color(0xFF64B5F6))),
                    onClick = onPemilikClick
                )
            }
            item {
                EnhancedGradientCard(
                    title = "Manajer",
                    icon = Icons.Filled.AccountCircle,
                    gradient = Brush.linearGradient(colors = listOf(Color(0xFFFF9800), Color(0xFFFFB74D))),
                    onClick = onManajerPropertiClick
                )
            }
            item {
                EnhancedGradientCard(
                    title = "Jenis Properti",
                    icon = Icons.Filled.ShoppingCart,
                    gradient = Brush.linearGradient(colors = listOf(Color(0xFFF44336), Color(0xFFE57373))),
                    onClick = onJenisPropertiClick
                )
            }
        }
    }
}

@Composable
fun EnhancedGradientCard(
    title: String,
    icon: ImageVector,
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val rotation by animateFloatAsState(
        targetValue = if (clicked) 5f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                clicked = true
                onClick()
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(61.dp),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    LaunchedEffect(clicked) {
        if (clicked) {
            delay(300)
            clicked = false
        }
    }
}

