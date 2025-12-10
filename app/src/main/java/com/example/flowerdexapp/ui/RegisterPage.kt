package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion

@Composable
fun RegisterPage(
    onBackClick: () -> Unit,
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
){
//    Text(text="work in progress")
    Column(
        modifier = modifier.padding(16.dp)
    ){
        // Imagen cuadrada que cubra el width del padre
        Image(
            painter = painterResource(id = R.drawable.placeholder),
            contentDescription = "Imagen",
            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        )
        Spacer(modifier = Modifier.size(16.dp))
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp).fillMaxWidth()
        ){
            // Botones de acceso a cámara o galería
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick= {/*TODO: Acceder a cámara*/}) {
                Image(
                    painter = painterResource(id = R.drawable.add_a_photo),
                    contentDescription = "Subir fotografía desde la cámara",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text="Cámara",
                    style = MaterialTheme.typography.titleMedium)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick={/*TODO: Pedir acceso a galería*/}){
                Image(
                    painter = painterResource(id = R.drawable.image_arrow_up),
                    contentDescription = "Subir fotografía desde la galería",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text="Galería",
                    style = MaterialTheme.typography.titleMedium)
            }
        }
        // Botón de escaneo/verificación
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onScanClick()/*TODO: Agregar acción para escaneo*/ }) {
            Image(
                painter = painterResource(id = R.drawable.image_search),
                contentDescription = "Escanear imagen seleccionada",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text="Escanear",
                style = MaterialTheme.typography.headlineSmall)
        }
//        TODO: Corregir alineamiento vertical del texto
    }
}