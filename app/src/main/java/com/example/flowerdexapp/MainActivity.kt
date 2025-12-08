package com.example.flowerdexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flowerdexapp.ui.theme.FlowerdexAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowerdexAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    IndexPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//Método de guardado, se moverá a otros archivos más adelante
data class Flor(
    val id: Long = 0,
    val nombreCientifico: String,
    val nombreComun: String,
    val familia: String,

    // Requisitos Ambientales
    val exposicionSolar: TipoExposicion,
    val frecuenciaRiego: Int, // Días entre riegos
    val temperaturaMinima: Double,

    // Características Visuales
    val colores: List<String>,
    val esToxica: Boolean,

    // Datos del Usuario
    val fechaAvistamiento: Long? = null,
    val fotoUri: String? = null
)

enum class TipoExposicion {
    SOL_DIRECTO, SEMI_SOMBRA, SOMBRA_TOTAL
}

@Composable
fun IndexPage(modifier: Modifier = Modifier) {
    Text(
        text = "Hello!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowerdexAppTheme {
        IndexPage()
    }
}