package com.example.flowerdexapp.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.FlorDao
import com.example.flowerdexapp.data.FlorDto
import com.example.flowerdexapp.data.GeminiFlowerdexService
import com.example.flowerdexapp.data.TipoColor
import com.example.flowerdexapp.data.TipoEstacion
import com.example.flowerdexapp.data.TipoExposicion
import com.example.flowerdexapp.utils.ImageUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScanUiState {
    object Initial : ScanUiState()
    object Loading : ScanUiState()
    data class Success(val florTemporal: Flor, val imageUri: Uri) : ScanUiState()
    data class Error(val mensaje: String) : ScanUiState()
}

class FlowerViewModel(
    application: Application,
    private val dao: FlorDao
) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val geminiService = GeminiFlowerdexService()

    val flores: StateFlow<List<Flor>> = dao.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _scanState = MutableStateFlow<ScanUiState>(ScanUiState.Initial)
    val scanState = _scanState.asStateFlow()

    var currentPhotoUri: Uri? = null
        private set

    fun onPhotoSelected(uri: Uri) {
        currentPhotoUri = uri
        _scanState.value = ScanUiState.Initial
    }

    fun escanearFlor() {
        val uri = currentPhotoUri ?: return
        _scanState.value = ScanUiState.Loading

        viewModelScope.launch {
            val dto = geminiService.identificarFlor(context, uri)
            if (dto != null) {
                val florTemporal = mapearDtoAEntidad(dto)
                if(florTemporal.nombreComun == "Desconocido"){ // Caso en que Gemini no pueda identificar la flor o la imagen no es una flor
                    _scanState.value = ScanUiState.Error("No se pudo identificar la flor o la imagen es inválida. Intenta de nuevo con una imagen diferente.")
                } else { // Caso exitoso
                    _scanState.value = ScanUiState.Success(florTemporal, uri)
                }
            } else { // Caso donde hubo un problema de servidor
                _scanState.value = ScanUiState.Error("No se pudo identificar la flor. Verifica tu conexión a internet e intenta de nuevo.")
            }
        }
    }

    fun obtenerFlor(id: Long): Flow<Flor?> {
        return dao.obtenerFlorPorId(id)
    }

    fun guardarFlorVerificada(flor: Flor) {
        val uriTemporal = (scanState.value as? ScanUiState.Success)?.imageUri ?: return

        viewModelScope.launch {
            val rutaFinal = ImageUtils.saveImageToInternalStorage(context, uriTemporal)
            val florFinal = flor.copy(
                fotoUri = rutaFinal, // Guardamos el path string, no el URI temporal
                fechaAvistamiento = System.currentTimeMillis()
            )
            dao.insertar(florFinal)
            _scanState.value = ScanUiState.Initial
            currentPhotoUri = null
        }
    }

    fun resetScanState() {
        _scanState.value = ScanUiState.Initial
        currentPhotoUri = null
    }

    private fun mapearDtoAEntidad(dto: FlorDto): Flor {
        return Flor(
            nombreCientifico = dto.nombreCientifico ?: "Desconocido",
            nombreComun = dto.nombreComun ?: "Desconocido",
            familia = dto.familia ?: "Desconocida",
            descripcion = dto.descripcion,
            exposicionSolar = try { TipoExposicion.valueOf(dto.exposicionSolar ?: "") } catch (e: Exception) { TipoExposicion.SEMI_SOMBRA },
            frecuenciaRiego = 1, // Failsafe, TODO: Mejorar
            estacionPreferida = try { TipoEstacion.valueOf(dto.estacionPreferida ?: "") } catch (e: Exception) { TipoEstacion.NINGUNA },
            alcalinidadPreferida = dto.alcalinidadPreferida ?: "Desconocida",
            colores = dto.colores?.mapNotNull { colorName ->
                try { TipoColor.valueOf(colorName) } catch (e: Exception) { null }
            } ?: emptyList(),
            esToxica = dto.esToxica ?: false
        )
    }
}

class FlowerViewModelFactory(
    private val application: Application,
    private val dao: FlorDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlowerViewModel(application, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}