package com.example.ruletadelasuerte.base

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.util.Log
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
        if (newBase == null) {
            Log.e("BaseActivity", "El contexto base es null, usando contexto predeterminado")
            super.attachBaseContext(this) // Usa el contexto actual si `newBase` es null
            return
        }

        val localeManager = LocaleManager(newBase)
        val context = updateLocale(newBase, localeManager.getLocale())

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

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(
                    languageChangeReceiver,
                    intentFilter,
                    Context.RECEIVER_NOT_EXPORTED
                )
            } else {
                registerReceiver(languageChangeReceiver, intentFilter)
            }
        } catch (e: Exception) {
            Log.e("BaseActivity", "Error al registrar receiver: ${e.message}")
        }
    }

    /**
     * Se ejecuta cuando la actividad ya no está en primer plano.
     * Se encarga de desregistrar el BroadcastReceiver para evitar fugas de memoria.
     */
    override fun onPause() {
        super.onPause()
        try {
            unregisterReceiver(languageChangeReceiver)
        } catch (e: Exception) {
            Log.e("BaseActivity", "Receiver no registrado: ${e.message}")
        }
    }
}