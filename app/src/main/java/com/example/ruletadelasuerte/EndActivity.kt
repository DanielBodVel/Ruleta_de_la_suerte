package com.example.ruletadelasuerte

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EndActivity : AppCompatActivity() {
    private lateinit var nombre1: String
    private lateinit var nombre2: String
    private lateinit var nombre3: String

    private lateinit var avatar1: String
    private lateinit var avatar2: String
    private lateinit var avatar3: String

    private var saldo1: Int = 0
    private var saldo2: Int = 0
    private var saldo3: Int = 0

    private lateinit var color1: String
    private lateinit var color2: String
    private lateinit var color3: String

    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextView
    private lateinit var playerSale: TextView
    private lateinit var playerColor: TextView

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        // Recuperar el mapa del Intent
        val hashMap = intent.getSerializableExtra("miMapa") as? HashMap<*, *>

        // Convertir de nuevo a mutableMapOf si es necesario
        val datosIn = hashMap?.toMutableMap()

        nombre1 = datosIn?.get("nombre1") as String
        avatar1 = datosIn["avatar1"] as String
        color1 = datosIn["color1"] as String
        saldo1 = datosIn["sale1"] as Int

        nombre2 = datosIn["nombre2"] as String
        avatar2 = datosIn["avatar2"] as String
        color2 = datosIn["color2"] as String
        saldo2 = datosIn["sale2"] as Int

        nombre3 = datosIn["nombre3"] as String
        avatar3 = datosIn["avatar3"] as String
        color3 = datosIn["color3"] as String
        saldo3 = datosIn["sale3"] as Int

        if (saldo1 > saldo2) {
            if (saldo1 > saldo3) {
                //Personaje 1
                playerImage = findViewById(R.id.playerImageEnd)
                //playerImage.setImageDrawable(R.drawable.avatar1)

                playerName = findViewById(R.id.textWinner)
                playerName.text = "Ha ganado $nombre1"

            } else {
                //Personaje 3
            }
        } else {
            if (saldo2 > saldo3) {
                //Personaje 2

            } else {
                //Personaje 3

            }
        }

        //TODO: código de inserccion a la bbdd

    }
}