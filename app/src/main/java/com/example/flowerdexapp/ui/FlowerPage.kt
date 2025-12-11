package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R


@Composable
fun FlowerPage(
    flowerId: Long,
    viewModel: FlowerViewModel,
    modifier: Modifier = Modifier
) {
    val florState by viewModel.obtenerFlor(flowerId).collectAsState(initial = null)
    val flor = florState

    if (flor == null) {
//        TODO: Mejorar la pantalla de cargado
        Text("Cargando...", modifier = modifier.padding(16.dp))
        return
    }
    var scrollState = rememberScrollState()
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "Imagen de la flor",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Column(){
                Text(
                    text = flor.nombreComun,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = flor.nombreCientifico,
                    style = MaterialTheme.typography.titleMedium
                )
                Text( // TODO: Obtener solo el DD/MM/AAAA de la variable fechaAvistameinto
                    text = "Fecha obtención: ${flor.fechaAvistamiento ?: "Desconocida"}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Descripción general:",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = flor.descripcion ?: "Descripción no disponible.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Información adicional:",
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column() {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Familia:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.familia,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Estación preferida:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.estacionPreferida.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Exposición al sol preferida:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.exposicionSolar.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Alcalinidad preferida:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.alcalinidadPreferida,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}