package com.example.flowerdexapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flowerdexapp.data.AppDatabase
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion
import com.example.flowerdexapp.ui.FlowerPage
import com.example.flowerdexapp.ui.FlowerViewModel
import com.example.flowerdexapp.ui.FlowerViewModelFactory
import com.example.flowerdexapp.ui.HomePage
import com.example.flowerdexapp.ui.IndexPage
import com.example.flowerdexapp.ui.RegisterPage
import com.example.flowerdexapp.ui.VerifyPage
import com.example.flowerdexapp.ui.theme.FlowerdexAppTheme

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Register : Screen("register")
    object Verify : Screen("verify")
    object Index : Screen("index")
    object Detail : Screen("detail/{flowerId}"){
        fun createRoute(flowerId: Long) = "detail/$flowerId"
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val viewModelFactory = FlowerViewModelFactory(this.application, database.florDao())

        setContent {
            FlowerdexAppTheme {
                val viewModel: FlowerViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = viewModelFactory
                )
                MainNavigationWrapper(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigationWrapper(viewModel: FlowerViewModel) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
//            TODO: Desaparecer el TopBar en la pantalla de inicio (Home)
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                title = {
                    val titulo = if (currentRoute?.startsWith("detail") == true) "Detalle de Flor" else "Enciclopedia"
//                    TODO: Mejorar el manejo de títulos dinámicos
                    Text(titulo, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    if (currentRoute != Screen.Index.route && currentRoute != Screen.Home.route) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás"
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // RUTA 1: Lista de Flores (Index)
            composable(Screen.Index.route) {
                IndexPage(
                    viewModel = viewModel,
                    onFlowerClick = { flowerId ->
                        navController.navigate(Screen.Detail.createRoute(flowerId))
                    },
                    onRegisterClick = { navController.navigate(Screen.Register.route){
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    } }
                )
            }

            // RUTA 2: Detalle de Flor
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("flowerId") { type = NavType.LongType })
            ) { backStackEntry ->
                val flowerId = backStackEntry.arguments?.getLong("flowerId") ?: 0L

                FlowerPage(
                    flowerId = flowerId,
                    viewModel = viewModel
                )
            }

            // RUTA 3: MENÚ DE INICIO (Home)
            composable(
                route = Screen.Home.route
            ) {
                HomePage(
                    onRegisterClick = { navController.navigate(Screen.Register.route) },
                    onIndexClick = { navController.navigate(Screen.Index.route) }
                )
            }

            // RUTA 4: Registro de nueva flor (Register)
            composable(
                route = Screen.Register.route
            ) {
                RegisterPage(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onScanSuccess = { navController.navigate(Screen.Verify.route) }
                )
            }

            // RUTA 5: Verificación de nueva flor (Verify)
            composable(
                route = Screen.Verify.route
            ) {
                VerifyPage(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onSaveSuccess = { navController.navigate(Screen.Index.route){
                        popUpTo(Screen.Home.route) { inclusive = false }
                    } }
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowerdexAppTheme {
        val viewModel: FlowerViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        MainNavigationWrapper(viewModel = viewModel)
    }
}
