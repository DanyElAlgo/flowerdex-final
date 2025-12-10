package com.example.flowerdexapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.R
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion

@Composable
fun HomePage(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // Este debería ser el logo, sin embargo, dado que aún no fue diseñado, se añadirá solo un texto de prueba
        Text(text = "Flowerdex")

        Column(){ //Botones de redirección
            Button(onClick = {
                // TODO: Redirigir a la página de registro

            }) {
                Image(
                    painter = painterResource(id = R.drawable.photo_camera_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Registrar",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Registrar")
            }
            Button(onClick = {
                // TODO: Redirigir a la página de enciclopedia

            }) {
                Image(
                    painter = painterResource(id = R.drawable.photo_camera_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    contentDescription = "Registrar",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Registrar")
            }
        }
        Text(text = "Version 1.0.0")
    }
}