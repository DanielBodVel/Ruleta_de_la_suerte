package com.example.ruletadelasuerte

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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