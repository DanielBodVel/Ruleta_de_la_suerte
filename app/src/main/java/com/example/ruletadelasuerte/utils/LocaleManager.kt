package com.example.ruletadelasuerte.utils

import android.content.Context

class LocaleManager(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        const val LANGUAGE_KEY = "language_key"
    }

    fun setLocale(language: String) {
        prefs.edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun getLocale(): String {
        return prefs.getString(LANGUAGE_KEY, "es") ?: "es" // Idioma por defecto: Español
    }
}
