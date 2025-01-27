package com.example.ruletadelasuerte

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TalkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_talk)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recuperar el mapa del Intent
        val hashMap = intent.getSerializableExtra("miMapa") as? HashMap<*, *>

        // Convertir de nuevo a mutableMapOf si es necesario
        val mutableMap = hashMap?.toMutableMap()

        // Mostrar el mapa en Logcat (o usarlo como desees)
        Log.d("SecondActivity", "Recibí el mapa: $mutableMap")
    }
}