package com.example.flowerdexapp.data

import android.content.Context
import android.content.SharedPreferences

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}

class ThemePreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("flower_prefs", Context.MODE_PRIVATE)

    fun saveTheme(theme: AppTheme) {
        prefs.edit().putString("app_theme", theme.name).apply()
    }

    fun getTheme(): AppTheme {
        val themeName = prefs.getString("app_theme", AppTheme.SYSTEM.name)
        return try {
            AppTheme.valueOf(themeName ?: AppTheme.SYSTEM.name)
        } catch (e: IllegalArgumentException) {
            AppTheme.SYSTEM
        }
    }
}