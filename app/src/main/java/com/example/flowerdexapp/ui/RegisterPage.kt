package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
            modifier = Modifier.padding(start = 16.dp)
        ){
            // Botones de acceso a cámara o galería
            Button(onClick= {/*TODO: Acceder a cámara*/}) {
                Text(text="Cámara")
            }
            Button(onClick={/*TODO: Pedir acceso a galería*/}){
                Text(text="Galería")
            }
        }
        // Botón de escaneo/verificación
        Button(onClick = { onScanClick()/*TODO: Agregar acción para escaneo*/ }) {
            Text(text="Escanear")
        }
    }
}