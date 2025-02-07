package com.example.ruletadelasuerte

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class TalkActivity : AppCompatActivity() {
    private lateinit var textoFrase: TextView
    private lateinit var barraProgreso: ProgressBar
    private lateinit var imagenGif: ImageView

    private lateinit var frase1: String
    private lateinit var frase2: String
    private lateinit var frase3: String
    private lateinit var frase4: String
    private lateinit var frase5: String
    private lateinit var frases: List<String>

    private var indiceFraseActual = 0
    private var progreso = 0
    private val manejador = Handler(Looper.getMainLooper())
    private lateinit var startButton: Button




    //Valores de los jugadores
    private lateinit var imagenJugador1: ImageView
    private lateinit var textoJugador1: TextView
    private lateinit var imagenJugador2: ImageView
    private lateinit var textoJugador2: TextView
    private lateinit var imagenJugador3: ImageView
    private lateinit var textoJugador3: TextView

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_talk)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textoJugador1 = findViewById(R.id.textoJugador1)
        imagenJugador1 = findViewById(R.id.imagenJugador1)
        textoJugador2 = findViewById(R.id.textoJugador2)
        imagenJugador2 = findViewById(R.id.imagenJugador2)
        textoJugador3 = findViewById(R.id.textoJugador3)
        imagenJugador3 = findViewById(R.id.imagenJugador3)
        inicializarJugadores()

        frase1 = getString(R.string.talk1)
        frase2 = getString(R.string.talk2)
        frase3 = getString(R.string.talk3)
        frase4 = getString(R.string.talk4)
        frase5 = getString(R.string.talk5)
        frases = listOf(frase1, frase2, frase3, frase4, frase5)


        //Pantalla completa
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        textoFrase = findViewById(R.id.phraseText)
        barraProgreso = findViewById(R.id.progressBar)
        imagenGif = findViewById(R.id.gifImage)
        startButton = findViewById(R.id.startButton)
        startButton.setOnClickListener {
            val intent = Intent(this, MultiGameActivity::class.java)
            startActivity(intent)
        }
        imagenGif.setImageResource(R.drawable.presentador)


        iniciarCambioDeFrases()

        findViewById<LinearLayout>(R.id.contentLayout).setOnClickListener { actualizarFrase() }
    }


    private fun inicializarJugadores() {
        val jugador1Nombre = intent.getStringExtra("jugador1Nombre") ?: "Jugador 1"
        val jugador1Color = intent.getStringExtra("jugador1Color") ?: "#FFFFFF"
        val jugador1Imagen = intent.getIntExtra("jugador1Imagen", R.drawable.mujer1)

        val jugador2Nombre = intent.getStringExtra("jugador2Nombre") ?: "Jugador 2"
        val jugador2Color = intent.getStringExtra("jugador2Color") ?: "#FFFFFF"
        val jugador2Imagen = intent.getIntExtra("jugador2Imagen", R.drawable.hombre1)

        val jugador3Nombre = intent.getStringExtra("jugador3Nombre") ?: "Jugador 3"
        val jugador3Color = intent.getStringExtra("jugador3Color") ?: "#FFFFFF"
        val jugador3Imagen = intent.getIntExtra("jugador3Imagen", R.drawable.mujer2)

        textoJugador1.text = jugador1Nombre
        textoJugador1.setBackgroundColor(Color.parseColor(jugador1Color))
        imagenJugador1.setImageResource(jugador1Imagen)

        textoJugador2.text = jugador2Nombre
        textoJugador2.setBackgroundColor(Color.parseColor(jugador2Color))
        imagenJugador2.setImageResource(jugador2Imagen)

        textoJugador3.text = jugador3Nombre
        textoJugador3.setBackgroundColor(Color.parseColor(jugador3Color))
        imagenJugador3.setImageResource(jugador3Imagen)
    }


    private fun iniciarCambioDeFrases() {
        manejador.postDelayed(object : Runnable {
            override fun run() {
                actualizarFrase()
                manejador.postDelayed(this, 4000)
            }
        }, 4000)
    }

    private fun actualizarFrase() {
        if (indiceFraseActual < frases.size) {
            textoFrase.text = frases[indiceFraseActual]
            progreso = (progreso + 20)
            barraProgreso.progress = progreso

            if (indiceFraseActual == frases.size - 1) {
                progreso = 100
                startButton.visibility = Button.VISIBLE
            }

            indiceFraseActual++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        manejador.removeCallbacksAndMessages(null)
    }
}