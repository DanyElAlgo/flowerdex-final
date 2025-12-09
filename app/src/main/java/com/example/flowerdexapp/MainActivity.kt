package com.example.flowerdexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flowerdexapp.ui.theme.FlowerdexAppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.time.Instant


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

//Base de datos de ejemplo, contiene solo 10 flores
var flowerDatabase = listOf(
    Flor(
        id = 1,
        nombreCientifico = "Rosa gallica",
        nombreComun = "Rosa de Castilla",
        familia = "Rosaceae",
        exposicionSolar = TipoExposicion.SOL_DIRECTO,
        frecuenciaRiego = 5,
        temperaturaMinima = -15.0,
        colores = listOf("Rojo", "Rosa", "Blanco"),
        esToxica = false,
        // Ejemplo de datos de usuario (opcional)
        fechaAvistamiento = Instant.parse("2024-10-20T10:00:00Z").epochSecond
    ),
    Flor(
        id = 2,
        nombreCientifico = "Lavandula angustifolia",
        nombreComun = "Lavanda",
        familia = "Lamiaceae",
        exposicionSolar = TipoExposicion.SOL_DIRECTO,
        frecuenciaRiego = 10,
        temperaturaMinima = -10.0,
        colores = listOf("Morado", "Azul"),
        esToxica = false
    ),
    Flor(
        id = 3,
        nombreCientifico = "Ficus lyrata",
        nombreComun = "Higuera Hoja de Violín",
        familia = "Moraceae",
        exposicionSolar = TipoExposicion.SEMI_SOMBRA,
        frecuenciaRiego = 7,
        temperaturaMinima = 10.0,
        colores = listOf("Verde"),
        esToxica = true
    ),
    Flor(
        id = 4,
        nombreCientifico = "Orchis mascula",
        nombreComun = "Orquídea Macho",
        familia = "Orchidaceae",
        exposicionSolar = TipoExposicion.SEMI_SOMBRA,
        frecuenciaRiego = 4,
        temperaturaMinima = 5.0,
        colores = listOf("Púrpura", "Rosa"),
        esToxica = false
    ),
    Flor(
        id = 5,
        nombreCientifico = "Monstera deliciosa",
        nombreComun = "Costilla de Adán",
        familia = "Araceae",
        exposicionSolar = TipoExposicion.SEMI_SOMBRA,
        frecuenciaRiego = 8,
        temperaturaMinima = 15.0,
        colores = listOf("Verde", "Blanco"),
        esToxica = true
    ),
    Flor(
        id = 6,
        nombreCientifico = "Viola tricolor",
        nombreComun = "Pensamiento Silvestre",
        familia = "Violaceae",
        exposicionSolar = TipoExposicion.SOL_DIRECTO,
        frecuenciaRiego = 5,
        temperaturaMinima = -5.0,
        colores = listOf("Púrpura", "Amarillo", "Blanco"),
        esToxica = false
    ),
    Flor(
        id = 7,
        nombreCientifico = "Spathiphyllum wallisii",
        nombreComun = "Espatifilo",
        familia = "Araceae",
        exposicionSolar = TipoExposicion.SOMBRA_TOTAL,
        frecuenciaRiego = 3,
        temperaturaMinima = 18.0,
        colores = listOf("Blanco", "Verde"),
        esToxica = true
    ),
    Flor(
        id = 8,
        nombreCientifico = "Tulipa gesneriana",
        nombreComun = "Tulipán",
        familia = "Liliaceae",
        exposicionSolar = TipoExposicion.SOL_DIRECTO,
        frecuenciaRiego = 6,
        temperaturaMinima = -20.0,
        colores = listOf("Rojo", "Amarillo", "Rosa", "Púrpura"),
        esToxica = false
    ),
    Flor(
        id = 9,
        nombreCientifico = "Saintpaulia ionantha",
        nombreComun = "Violeta Africana",
        familia = "Gesneriaceae",
        exposicionSolar = TipoExposicion.SOMBRA_TOTAL,
        frecuenciaRiego = 5,
        temperaturaMinima = 16.0,
        colores = listOf("Violeta", "Azul", "Rosa", "Blanco"),
        esToxica = false
    ),
    Flor(
        id = 10,
        nombreCientifico = "Dianthus caryophyllus",
        nombreComun = "Clavel",
        familia = "Caryophyllaceae",
        exposicionSolar = TipoExposicion.SOL_DIRECTO,
        frecuenciaRiego = 7,
        temperaturaMinima = 0.0,
        colores = listOf("Rojo", "Rosa", "Blanco", "Amarillo"),
        esToxica = false
    )
)

//Base de datos vacía para pruebas
var flowerDatabaseEmpty = listOf<Flor>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
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
fun ImageExample(
    modifier: Modifier = Modifier,
    sizeDp: Int = 200,
    borderWidthDp: Int = 2
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .clip(shape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "Imagen",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .border(borderWidthDp.dp, Color.Black, shape)
            )
        }
    }
}

@Composable
fun FlowerListItem(flower: Flor, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        // datos de la flor
        Row(modifier = Modifier.padding(16.dp)) {
            ImageExample(modifier = Modifier.width(56.dp))
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = flower.nombreComun,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = flower.nombreCientifico,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Fecha obtención: ${flower.fechaAvistamiento ?: "Desconocida"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        // linea divisoria
        HorizontalDivider(thickness = 2.dp)
    }

}
fun FlowerBlockItem(flower: Flor, modifier: Modifier = Modifier) {
    // Aquí se definirá el diseño del bloque de cada flor
}

@Composable
fun IndexPage(modifier: Modifier = Modifier) {
    var isListView by remember { mutableStateOf(true) }
//    Validar que la base tenga datos, caso contrario, mostrar mensaje de "No hay datos"
    if (flowerDatabaseEmpty.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay flores en la base de datos.",
                style = MaterialTheme.typography.bodyLarge
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
                items(flowerDatabase.size) { index ->
                    FlowerListItem(flower = flowerDatabase[index])
                }
            }
        } else {
            // Implementar vista de bloques aquí
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