package com.example.ruletadelasuerte

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ruletadelasuerte.utils.GameSaver

class EndActivity : AppCompatActivity() {
    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextView
    private lateinit var playerSale1: TextView
    private lateinit var playerSale2: TextView
    private lateinit var playerSale3: TextView

    private val gameSaver = GameSaver(this)

    private lateinit var nombreJugador1: String
    private var ganancia1: Int = 0
    private lateinit var jugador1Color: String
    private var jugador1Imagen: Int = 0
    private lateinit var nombreJugador2: String
    private var ganancia2: Int = 0
    private lateinit var jugador2Color: String
    private var jugador2Imagen: Int = 0
    private lateinit var nombreJugador3: String
    private var ganancia3: Int = 0
    private lateinit var jugador3Color: String
    private var jugador3Imagen: Int = 0

//    private lateinit var salePlayer1: TextView
//    private lateinit var salePlayer2: TextView
//    private lateinit var salePlayer3: TextView


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
        playerName = findViewById(R.id.textWinner)
        playerSale1 = findViewById(R.id.salePlayer1)
        playerSale2 = findViewById(R.id.salePlayer2)
        playerSale3 = findViewById(R.id.salePlayer3)
        playerImage = findViewById(R.id.playerImageEnd)

        inicializarJugadores()
        mostrarDatosJugadores()

        findViewById<ImageButton>(R.id.imageButtonBack)?.setOnClickListener {
            val id = gameSaver.insertGame(
                nombreJugador1,
                ganancia1,
                nombreJugador2,
                ganancia2,
                nombreJugador3,
                ganancia3
            )
            if (id != -1L) {
                Toast.makeText(this, "Partida guardada correctamente", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inicializarJugadores() {
        nombreJugador1 = intent.getStringExtra("jugador1Nombre") ?: "Jugador 1"
        ganancia1 = intent.getIntExtra("saldo1Jugador", 0)
        jugador1Color = intent.getStringExtra("jugador1Color") ?: "#FFFFFF"
        jugador1Imagen = intent.getIntExtra("jugador1Imagen", R.drawable.mujer1)

        nombreJugador2 = intent.getStringExtra("jugador2Nombre") ?: "Jugador 2"
        ganancia2 = intent.getIntExtra("saldo2Jugador", 0)
        jugador2Color = intent.getStringExtra("jugador2Color") ?: "#FFFFFF"
        jugador2Imagen = intent.getIntExtra("jugador2Imagen", R.drawable.hombre1)

        nombreJugador3 = intent.getStringExtra("jugador3Nombre") ?: "Jugador 3"
        ganancia3 = intent.getIntExtra("saldo3Jugador", 0)
        jugador3Color = intent.getStringExtra("jugador3Color") ?: "#FFFFFF"
        jugador3Imagen = intent.getIntExtra("jugador3Imagen", R.drawable.mujer2)

    }

    private fun mostrarDatosJugadores() {
        val maxBalance = maxOf(ganancia1, ganancia2, ganancia3)
        when (maxBalance) {
            ganancia1 -> {
                playerImage.setImageResource(jugador1Imagen)
                playerName.text = "¡Ha ganado $nombreJugador1!"
            }

            ganancia2 -> {
                playerImage.setImageResource(jugador2Imagen)
                playerName.text = "¡Ha ganado $nombreJugador2!"
            }

            ganancia3 -> {
                playerImage.setImageResource(jugador3Imagen)
                playerName.text = "¡Ha ganado $nombreJugador3!"
            }
        }
        playerSale1.text = "El jugador $nombreJugador1 ha ganado: $ganancia1€"
        playerSale2.text = "El jugador $nombreJugador2 ha ganado: $ganancia2€"
        playerSale3.text = "El jugador $nombreJugador3 ha ganado: $ganancia3€"
    }
}