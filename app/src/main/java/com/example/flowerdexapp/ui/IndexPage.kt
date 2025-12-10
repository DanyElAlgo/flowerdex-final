package com.example.flowerdexapp.ui

import android.widget.GridLayout
import android.widget.GridView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion
import kotlin.collections.get

@Composable
fun IndexPage(
    viewModel: FlowerViewModel,
    onFlowerClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val listaFlores by viewModel.flores.collectAsState(initial = emptyList())
    var isListView by remember { mutableStateOf(true) }
    if (listaFlores.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize(),
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
            Button(onClick = {
                // Crear una flor de prueba por el momento
                val florPrueba = Flor(
                    nombreCientifico = "Rosa",
                    nombreComun = "Rosa común",
                    familia = "Rosáceas",
                    exposicionSolar = TipoExposicion.SOL_DIRECTO,
                    frecuenciaRiego = 1,
                    estacionPreferida = TipoEstacion.PRIMAVERA,
                    alcalinidadPreferida = "Media",
                    colores = listOf(TipoColor.ROJO, TipoColor.AMARILLO),
                    esToxica = false
                )
                viewModel.agregarFlor(florPrueba)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.photo_camera_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Agregar flor",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Agregar flor")
            }
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
                    .clickable { /* TODO: Open Sort Dialog */ }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Sort,
                    contentDescription = "Sort",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ordenar",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
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
            LazyHorizontalGrid(GridCells.Fixed(2)) {
                items(listaFlores.size) { index ->
                    FlowerBlockItem(
                        flower = listaFlores[index],
                        onClick = { onFlowerClick(listaFlores[index].id) }
                    )
                }
            }
        }
        Button(onClick = { //DEBUG, TODO: ELIMINAR LUEGO
            // Crear una flor de prueba por el momento
            val florPrueba = Flor(
                nombreCientifico = "Rosa",
                nombreComun = "Rosa común",
                familia = "Rosáceas",
                exposicionSolar = TipoExposicion.SOL_DIRECTO,
                frecuenciaRiego = 1,
                estacionPreferida = TipoEstacion.PRIMAVERA,
                alcalinidadPreferida = "Media",
                colores = listOf(TipoColor.ROJO, TipoColor.AMARILLO),
                esToxica = false
            )
            viewModel.agregarFlor(florPrueba)
        }) {
            Image(
                painter = painterResource(id = R.drawable.photo_camera_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                contentDescription = "Agregar flor (DEBUG)",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Agregar flor (DEBUG)")
        }
    }
}