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
    private lateinit var avatarLayout: LinearLayout

    private lateinit var name1: String
    private lateinit var image1: String
    private lateinit var color1: String

    private lateinit var name2: String
    private lateinit var image2: String
    private lateinit var color2: String

    private lateinit var name3: String
    private lateinit var image3: String
    private lateinit var color3: String

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


    private fun inicializarJugadores(){
        val bundle = intent.extras
        if (bundle != null) {
            name1 = bundle.getString("name1").toString()
            image1 = bundle.getString("image1").toString()
            color1 = bundle.getString("color1").toString()

            name2 = bundle.getString("name2").toString()
            image2 = bundle.getString("image2").toString()
            color2 = bundle.getString("color2").toString()

            name3 = bundle.getString("name3").toString()
            image3 = bundle.getString("image3").toString()
            color3 = bundle.getString("color3").toString()
        }

        // Mostrar los datos del bundle en un Toast
        val toastMessage = "Jugador 1: $name1, Imagen: $image1, Color: $color1\n" +
                "Jugador 2: $name2, Imagen: $image2, Color: $color2\n" +
                "Jugador 3: $name3, Imagen: $image3, Color: $color3"
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()

        // Configuración de la interfaz con los valores recibidos
        textoFrase = findViewById(R.id.phraseText)
        barraProgreso = findViewById(R.id.progressBar)
        imagenGif = findViewById(R.id.gifImage)
        avatarLayout = findViewById(R.id.avatarsLayout)

        // Setear los avatares y nombres de los jugadores
        val avatar1 = avatarLayout.getChildAt(0) as LinearLayout
        val avatar2 = avatarLayout.getChildAt(1) as LinearLayout
        val avatar3 = avatarLayout.getChildAt(2) as LinearLayout

        // Persona 1
        val imgAvatar1 = avatar1.getChildAt(0) as ImageView
        val txtName1 = avatar1.getChildAt(1) as TextView
        imgAvatar1.setImageResource(resources.getIdentifier(image1, "drawable", packageName))
        txtName1.text = name1
        txtName1.setBackgroundColor(Color.parseColor(color1))

        // Persona 2
        val imgAvatar2 = avatar2.getChildAt(0) as ImageView
        val txtName2 = avatar2.getChildAt(1) as TextView
        imgAvatar2.setImageResource(resources.getIdentifier(image2, "drawable", packageName))  // Imagen dinámica
        txtName2.text = name2
        txtName2.setBackgroundColor(Color.parseColor(color2))

        // Persona 3
        val imgAvatar3 = avatar3.getChildAt(0) as ImageView
        val txtName3 = avatar3.getChildAt(1) as TextView
        imgAvatar3.setImageResource(resources.getIdentifier(image3, "drawable", packageName))  // Imagen dinámica
        txtName3.text = name3
        txtName3.setBackgroundColor(Color.parseColor(color3))
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