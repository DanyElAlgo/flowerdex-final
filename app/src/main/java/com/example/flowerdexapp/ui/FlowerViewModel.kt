package com.example.flowerdexapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flowerdexapp.data.Flor
import com.example.flowerdexapp.data.FlorDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlowerViewModel(private val dao: FlorDao) : ViewModel() {

    val flores: StateFlow<List<Flor>> = dao.obtenerTodas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarFlor(flor: Flor) {
        viewModelScope.launch {
            dao.insertar(flor)
        }
    }
    fun obtenerFlor(id: Long): Flow<Flor?> {
        return dao.obtenerFlorPorId(id)
    }
}

class FlowerViewModelFactory(private val dao: FlorDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlowerViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}