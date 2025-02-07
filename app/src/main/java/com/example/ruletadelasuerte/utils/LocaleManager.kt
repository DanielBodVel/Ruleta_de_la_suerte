package com.example.ruletadelasuerte.utils

import android.content.Context

/**
 * Clase encargada de gestionar la configuración del idioma en la aplicación.
 * Permite guardar y recuperar el idioma seleccionado por el usuario.
 * @param context Contexto de la aplicación o actividad.
 */
class LocaleManager(context: Context) {

    // Preferencias compartidas donde se almacena el idioma seleccionado
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    companion object {
        // Clave utilizada para guardar el idioma en SharedPreferences
        const val LANGUAGE_KEY = "language_key"
    }

    /**
     * Guarda el idioma seleccionado en las preferencias compartidas.
     *
     * @param language Código del idioma a establecer (por ejemplo, "es" para español, "en" para inglés).
     */
    fun setLocale(language: String) {
        prefs.edit().putString(LANGUAGE_KEY, language).apply()
    }

    /**
     * Recupera el idioma actualmente almacenado en las preferencias compartidas.
     *
     * @return Código del idioma guardado o "es" (Español) si no se ha establecido ninguno.
     */
    fun getLocale(): String {
        return prefs.getString(LANGUAGE_KEY, "es") ?: "es" // Idioma por defecto: Español
    }
}