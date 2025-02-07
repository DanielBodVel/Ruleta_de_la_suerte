package com.example.ruletadelasuerte.utils

import android.content.Context
import java.util.Locale

/**
 * Actualiza el idioma de la aplicación estableciendo un nuevo contexto configurado con el idioma seleccionado.
 *
 * @param context Contexto actual de la aplicación.
 * @param language Código del idioma a aplicar (por ejemplo, "es" para español, "en" para inglés).
 * @return Nuevo contexto configurado con el idioma seleccionado.
 */
fun updateLocale(context: Context, language: String): Context {
    // Crea un nuevo objeto Locale con el idioma seleccionado
    val locale = Locale(language)

    // Establece el nuevo idioma como predeterminado
    Locale.setDefault(locale)

    // Obtiene la configuración actual de los recursos
    val config = context.resources.configuration

    // Aplica la nueva configuración de idioma
    config.setLocale(locale)
    config.setLayoutDirection(locale) // Ajusta la dirección del texto si es necesario (por ejemplo, para árabe o hebreo)

    // Retorna un nuevo contexto con la configuración actualizada
    return context.createConfigurationContext(config)
}