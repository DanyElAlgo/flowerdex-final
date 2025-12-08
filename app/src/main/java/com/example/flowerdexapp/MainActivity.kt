package com.example.flowerdexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.flowerdexapp.ui.theme.FlowerdexAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowerdexAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SmallTopAppBarExample(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Enciclopedia",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        IndexPage(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun FlowerListItem(flower: Flor, modifier: Modifier = Modifier) {
    Text(
        text = flower.nombreComun,
        modifier = modifier
    )
}
fun FlowerBlockItem(flower: Flor, modifier: Modifier = Modifier) {
    // Aquí se definirá el diseño del bloque de cada flor
}

@Composable
fun IndexPage(modifier: Modifier = Modifier) {
    Text(
        text = "Hello!",
        modifier = modifier
    )
//    Añadir un elmemento que permita organizar las flores por diferentes criterios y cambiar la vista entre lista y bloques
    LazyColumn {
        items(10) { index ->
            FlowerListItem(
                flower = Flor(
                    id = index.toLong(),
                    nombreCientifico = "Flor $index",
                    nombreComun = "Flor Común $index",
                    familia = "Familia $index",
                    exposicionSolar = TipoExposicion.SOL_DIRECTO,
                    frecuenciaRiego = 7,
                    temperaturaMinima = 15.0,
                    colores = listOf("Rojo", "Verde"),
                    esToxica = false // Placeholder
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowerdexAppTheme {
        SmallTopAppBarExample()
    }
}