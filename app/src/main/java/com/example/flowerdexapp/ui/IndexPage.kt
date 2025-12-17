package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion

@Composable
fun IndexPage(
    viewModel: FlowerViewModel,
    onFlowerClick: (Long) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listaFlores by viewModel.flores.collectAsState(initial = emptyList())
    val filterState by viewModel.filterState.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

    if (showFilterSheet) {
        FilterBottomSheet(
        currentFilter = filterState,
        onFilterChange = { newState -> viewModel.updateFilter(newState) },
        onDismiss = { showFilterSheet = false }
        )
    }

    IndexPageContent(
        listaFlores = listaFlores,
        onSortClick = { showFilterSheet = true },
        onFlowerClick = onFlowerClick,
        onRegisterClick = onRegisterClick,
        modifier = modifier
    )
}
@Composable
fun IndexPageContent(
    listaFlores: List<Flor>,
    onSortClick: () -> Unit,
    onFlowerClick: (Long) -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isListView by remember { mutableStateOf(true) }

    if (listaFlores.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No hay flores registradas.\nEmpieza a capturar flores para\nllenar la enciclopedia.",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .drawWithCache {
                        val paint = Paint().apply {
                            blendMode = BlendMode.Multiply
                        }
                        onDrawWithContent {
                            drawIntoCanvas { canvas ->
                                canvas.saveLayer(size.toRect(), paint)
                                drawContent()
                                canvas.restore()
                            }
                        }
                    }
            )
            ButtonElement(
                text = "Agregar flor",
                textStyle = MaterialTheme.typography.bodyLarge,
                onClick = onRegisterClick,
                icon = R.drawable.photo_camera,
                spacing = 8,
                iconSize = 24
            )
        }
        return
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onSortClick() }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ordenar",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = { isListView = !isListView }) {
                Icon(
                    imageVector = if (isListView) Icons.Default.GridView else Icons.Default.List,
                    contentDescription = "Switch View",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (isListView) {
            LazyColumn {
                items(listaFlores.size) { index ->
                    FlowerListItem(
                        flower = listaFlores[index],
                        onClick = { onFlowerClick(listaFlores[index].id) }
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(listaFlores.size) { index ->
                    FlowerBlockItem(
                        flower = listaFlores[index],
                        onClick = { onFlowerClick(listaFlores[index].id) }
                    )
                }
            }
        }
    }
}
