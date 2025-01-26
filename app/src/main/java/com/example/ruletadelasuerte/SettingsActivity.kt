package com.example.ruletadelasuerte

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ruletadelasuerte.base.BaseActivity
import com.example.ruletadelasuerte.utils.LocaleManager

class SettingsActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        findViewById<Button>(R.id.buttSpanish).setOnClickListener {
            Toast.makeText(this, "Idioma cambiado al español", Toast.LENGTH_SHORT).show()
            val localeManager = LocaleManager(this)

            val newLanguage = "es"
            localeManager.setLocale(newLanguage)

            val intent = Intent("com.example.ruletadelasuerte.LANGUAGE_CHANGED")
            sendBroadcast(intent)

            // Reiniciar la actividad para aplicar el nuevo idioma
            recreate()
        }

        findViewById<Button>(R.id.buttEnglish).setOnClickListener{
            Toast.makeText(this, "Language changed to english", Toast.LENGTH_SHORT).show()
            val localeManager = LocaleManager(this)

            val newLanguage = "en"
            localeManager.setLocale(newLanguage)

            val intent = Intent("com.example.ruletadelasuerte.LANGUAGE_CHANGED")
            sendBroadcast(intent)

            // Reiniciar la actividad para aplicar el nuevo idioma
            recreate()
        }

        findViewById<Button>(R.id.buttBack).setOnClickListener{
            // Reiniciar la app para aplicar el idioma a todas las actividades
            val intent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            pendingIntent.send()

            // Finaliza la actividad actual
            finishAffinity()
        }
    }

}