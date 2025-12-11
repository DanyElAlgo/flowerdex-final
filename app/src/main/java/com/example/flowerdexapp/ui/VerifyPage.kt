package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun VerifyPage(
    datos: Flor,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val flor = datos
    var scrollState = rememberScrollState()
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen de la flor",
                contentScale = ContentScale.Crop,
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
        Column( // Dedicado a los botones de cancelado y guardado
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Button(
                border = ButtonDefaults.outlinedButtonBorder,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick= { onBackClick() }) {
                Image(
                    painter = painterResource(id = R.drawable.undo_icon),
                    contentDescription = "Volver",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text="Cancelar",
                    style = MaterialTheme.typography.titleMedium)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick={ onSaveClick() /*TODO: Guardar nueva adquisición*/}){
                Image(
                    painter = painterResource(id = R.drawable.save_icon),
                    contentDescription = "Guardar nueva adquisición",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text="Guardar",
                    style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyPagePreview(){
    VerifyPage(datos = Flor(
        nombreCientifico = "Rosa",
        nombreComun = "Rosa común",
        familia = "Rosáceas",
        exposicionSolar = TipoExposicion.SOL_DIRECTO,
        frecuenciaRiego = 1,
        estacionPreferida = TipoEstacion.PRIMAVERA,
        alcalinidadPreferida = "Media",
        colores = listOf(TipoColor.ROJO, TipoColor.AMARILLO),
        esToxica = false
    ), onBackClick = {}, onSaveClick = {})
}