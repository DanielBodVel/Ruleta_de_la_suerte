package com.example.ruletadelasuerte.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Receiver que escucha los cambios en el idioma de la aplicación.
 * Cuando se detecta un cambio de idioma, la actividad actual se reinicia
 * para aplicar la nueva configuración de idioma.
 */
class LanguageChangeReceiver : BroadcastReceiver() {

    /**
     * Método llamado cuando se recibe un broadcast con la acción de cambio de idioma.
     *
     * @param context Contexto de la aplicación o actividad.
     * @param intent Intent que contiene la acción del broadcast.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.example.ruletadelasuerte.LANGUAGE_CHANGED") {
            if (context is Activity) {
                context.recreate()
            } else {
                Log.e("LanguageChangeReceiver", "El contexto recibido no es una actividad.")
            }
        }
    }
}