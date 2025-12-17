package com.example.flowerdexapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion
import coil.compose.AsyncImage
import java.io.File

@Composable
fun VerifyPage(
    viewModel: FlowerViewModel,
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scanState by viewModel.scanState.collectAsState()
    var showExitDialog by remember { mutableStateOf(false) }

    val onBackAction = {
        viewModel.resetScanState()
        onBackClick()
    }
    BackHandler(enabled = true) {
        showExitDialog = true
    }

    if (scanState is ScanUiState.Saving) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Subiendo flor a la nube...")
                Text("Por favor espera", style = MaterialTheme.typography.bodySmall)
            }
        }
        return
    }

    if (scanState is ScanUiState.Error) {
        val errorMsg = (scanState as ScanUiState.Error).mensaje
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Error al guardar:")
            Text(errorMsg, color = MaterialTheme.colorScheme.error)/*
            Button(onClick = {
                viewModel.guardarFlorVerificada(flor) {
                    onSaveSuccess()
                }
            }) { Text("Reintentar") }*/
            Button(onClick = onBackClick) { Text("Volver") }
        }
        return
    }

    if(scanState !is ScanUiState.Success){
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Error: No hay datos de escaneo para verificar.")
            Button(onClick = onBackClick) { Text("Volver") }
        }
        return
    }

    val successState = scanState as ScanUiState.Success
    val flor = successState.florTemporal
    val imageUri = successState.imageUri

    var scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text(text = "¿Cancelar registro?") },
                text = { Text("Si sales ahora, el escaneo tendrá que realizarse desde cero.") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showExitDialog = false
                            onBackAction()
                        }
                    ) {
                        Text("Salir")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
        // La identación es incorrecta, pero así no será tan exagerado el commit de GitHub
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            ImageElement(
                imageUri = imageUri,
                modifier = Modifier
                    .size(136.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.size(24.dp))
            Column(
                modifier = Modifier.height(136.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Column{
                    Text(
                        text = "Nombre común:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.nombreComun,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
//                Spacer(modifier = Modifier.size(16.dp))
                Column{
                    Text(
                        text = "Nombre científico:",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = flor.nombreCientifico,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.padding(start = 16.dp)) {
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
                SmallInfoElement(
                    title = "Familia:",
                    info = flor.familia
                )
                SmallInfoElement(
                    title = "Colores:",
                    info = flor.colores.joinToString(", ") { it.descripcion }
                )
                SmallInfoElement(
                    title = "Estación preferida:",
                    info = flor.estacionPreferida.descripcion
                )
                SmallInfoElement(
                    title = "Exposición al sol preferida:",
                    info = flor.exposicionSolar.descripcion
                )
                SmallInfoElement(
                    title = "Tipo de suelo / alcalinidad preferida:",
                    info = flor.alcalinidadPreferida
                )
                SmallInfoElement(
                    title = "Toxicidad:",
                    info = if (flor.esToxica) "Tóxica" else "No tóxica",
                    dangerous = flor.esToxica
                )
            }
        }
        Column( // Dedicado a los botones de cancelado y guardado
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            ButtonElement(
                text = "Cancelar",
                textStyle = MaterialTheme.typography.titleMedium,
                onClick = { showExitDialog },
                modifier = Modifier
                    .fillMaxWidth(),
                icon = R.drawable.undo_icon,
                iconSize = 24
            )
            ButtonElement(
                text = "Guardar",
                textStyle = MaterialTheme.typography.titleMedium,
                onClick = {
                    viewModel.guardarFlorVerificada(flor) {
                        onSaveSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                icon = R.drawable.save_icon,
                iconSize = 24
            )
        }
    }
}
}