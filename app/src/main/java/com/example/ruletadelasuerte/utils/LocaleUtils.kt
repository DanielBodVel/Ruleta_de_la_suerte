package com.example.ruletadelasuerte.utils

import android.content.Context
import java.util.Locale

fun updateLocale(context: Context, language: String): Context {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return context.createConfigurationContext(config)
}