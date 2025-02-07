package com.example.ruletadelasuerte.base

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ruletadelasuerte.utils.LanguageChangeReceiver
import com.example.ruletadelasuerte.utils.LocaleManager
import com.example.ruletadelasuerte.utils.updateLocale

/**
 * Clase base para todas las actividades de la aplicación.
 * Se encarga de manejar el cambio de idioma dinámicamente.
 */
abstract class BaseActivity : AppCompatActivity() {

    // Receiver para detectar cambios en el idioma
    private val languageChangeReceiver = LanguageChangeReceiver()

    /**
     * Se encarga de actualizar el contexto de la actividad con el idioma configurado.
     * Esto asegura que la aplicación refleje el cambio de idioma correctamente.
     * @param newBase Contexto de la aplicación o actividad.
     */
    override fun attachBaseContext(newBase: Context?) {
        // Crea un gestor de configuración regional con el contexto base
        val localeManager = LocaleManager(newBase!!)

        // Actualiza el contexto con el idioma configurado
        val context = updateLocale(newBase, localeManager.getLocale())

        // Aplica el contexto actualizado a la actividad
        super.attachBaseContext(context)
    }

    /**
     * Se ejecuta cuando la actividad se vuelve visible al usuario.
     * Registra el BroadcastReceiver para escuchar cambios de idioma.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("com.example.ruletadelasuerte.LANGUAGE_CHANGED")

        // Registra el receiver para escuchar eventos de cambio de idioma
        registerReceiver(languageChangeReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
    }

    /**
     * Se ejecuta cuando la actividad ya no está en primer plano.
     * Se encarga de desregistrar el BroadcastReceiver para evitar fugas de memoria.
     */
    override fun onPause() {
        super.onPause()
        unregisterReceiver(languageChangeReceiver)
    }
}