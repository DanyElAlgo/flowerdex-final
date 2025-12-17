package com.example.flowerdexapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowerdexapp.data.AppTheme
import com.example.flowerdexapp.data.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val preferences = ThemePreferences(application)

    private val _theme = MutableStateFlow(preferences.getTheme())
    val theme: StateFlow<AppTheme> = _theme

    fun toggleTheme() {
        val newTheme = when (_theme.value) {
            AppTheme.SYSTEM -> AppTheme.LIGHT
            AppTheme.LIGHT -> AppTheme.DARK
            AppTheme.DARK -> AppTheme.SYSTEM
        }
        updateTheme(newTheme)
    }

    fun updateTheme(newTheme: AppTheme) {
        viewModelScope.launch {
            preferences.saveTheme(newTheme)
            _theme.value = newTheme
        }
    }
}