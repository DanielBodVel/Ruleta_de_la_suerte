package com.example.ruletadelasuerte

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class PlayerSelectActivity : AppCompatActivity() {
    private var contadorJugadores = 1

    private lateinit var jugador1Nombre: String
    private lateinit var jugador1Color: String
    private var jugador1Imagen: Int = 0

    private lateinit var jugador2Nombre: String
    private lateinit var jugador2Color: String
    private var jugador2Imagen: Int = 0

    private lateinit var jugador3Nombre: String
    private lateinit var jugador3Color: String
    private var jugador3Imagen: Int = 0

    private lateinit var playerName: TextInputEditText
    private lateinit var playerGenderGroup: RadioGroup
    private lateinit var playerColorGroup: RadioGroup

    private lateinit var playerImage: ImageView
    private lateinit var secondImage1: ImageView
    private lateinit var secondImage2: ImageView
    private lateinit var secondImage3: ImageView

    private var imagenSeleccionada: Int = 0

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_player_select)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        changeRadioButtonColor()

        playerName = findViewById(R.id.nameIn)
        playerGenderGroup = findViewById(R.id.genderGroup)
        playerColorGroup = findViewById(R.id.colorGroup)

        playerImage = findViewById(R.id.playerImage)
        secondImage1 = findViewById(R.id.imageSecondary1)
        secondImage2 = findViewById(R.id.imageSecondary2)
        secondImage3 = findViewById(R.id.imageSecondary3)

        updateImage()

        secondImage1.setOnClickListener {
            seleccionarImagen(secondImage1)
        }

        secondImage2.setOnClickListener {
            seleccionarImagen(secondImage2)
        }

        secondImage3.setOnClickListener {
            seleccionarImagen(secondImage3)
        }

        playerGenderGroup.setOnCheckedChangeListener { _, _ ->
            updateImage()
        }

        findViewById<Button>(R.id.next)?.setOnClickListener {
            procesarJugador()
        }
    }

    private fun seleccionarImagen(imageView: ImageView) {
        playerImage.setImageDrawable(imageView.drawable)
        imagenSeleccionada = getImageResourceFromDrawable(imageView.drawable)
    }

    private fun procesarJugador() {
        val playerInputName = playerName.text.toString().trim()
        val playerColor = when (playerColorGroup.checkedRadioButtonId) {
            R.id.red -> "#e72853"
            R.id.blue -> "#0099d6"
            R.id.yellow -> "#ffe800"
            else -> "#FFFFFF"
        }

        when {
            playerGenderGroup.checkedRadioButtonId == -1 -> mostrarMensaje("Faltan campos por completar")
            playerInputName.isEmpty() -> mostrarMensaje("Introduce un nombre")
            playerColorGroup.checkedRadioButtonId == -1 -> mostrarMensaje("Selecciona un Color")
            imagenSeleccionada == 0 -> mostrarMensaje("Selecciona una imagen")
            else -> {
                when (contadorJugadores) {
                    1 -> {
                        jugador1Nombre = playerInputName
                        jugador1Color = playerColor
                        jugador1Imagen = imagenSeleccionada
                        siguienteJugador()
                    }

                    2 -> {
                        jugador2Nombre = playerInputName
                        jugador2Color = playerColor
                        jugador2Imagen = imagenSeleccionada
                        siguienteJugador()
                    }

                    3 -> {
                        jugador3Nombre = playerInputName
                        jugador3Color = playerColor
                        jugador3Imagen = imagenSeleccionada
                        iniciarJuego()
                    }
                }
            }
        }
    }

    private fun siguienteJugador() {
        contadorJugadores++
        mostrarMensaje("Siguiente jugador")
        playerName.text?.clear()
    }

    private fun iniciarJuego() {
        val intent = Intent(this, TalkActivity::class.java).apply {
            putExtra("jugador1Nombre", jugador1Nombre)
            putExtra("jugador1Color", jugador1Color)
            putExtra("jugador1Imagen", jugador1Imagen)

            putExtra("jugador2Nombre", jugador2Nombre)
            putExtra("jugador2Color", jugador2Color)
            putExtra("jugador2Imagen", jugador2Imagen)

            putExtra("jugador3Nombre", jugador3Nombre)
            putExtra("jugador3Color", jugador3Color)
            putExtra("jugador3Imagen", jugador3Imagen)
        }
        startActivity(intent)
    }

    private fun updateImage() {
        val selectedGender = playerGenderGroup.checkedRadioButtonId
        when (selectedGender) {
            R.id.female -> {
                playerImage.setImageResource(R.drawable.mujer1)
                secondImage1.setImageResource(R.drawable.mujer1)
                secondImage2.setImageResource(R.drawable.mujer2)
                secondImage3.setImageResource(R.drawable.mujer3)
                imagenSeleccionada = R.drawable.mujer1
            }

            else -> {
                playerImage.setImageResource(R.drawable.hombre1)
                secondImage1.setImageResource(R.drawable.hombre1)
                secondImage2.setImageResource(R.drawable.hombre2)
                secondImage3.setImageResource(R.drawable.hombre3)
                imagenSeleccionada = R.drawable.hombre1
            }
        }
    }

    private fun getImageResourceFromDrawable(drawable: android.graphics.drawable.Drawable): Int {
        return when (drawable.constantState) {
            ContextCompat.getDrawable(this, R.drawable.mujer1)?.constantState -> R.drawable.mujer1
            ContextCompat.getDrawable(this, R.drawable.mujer2)?.constantState -> R.drawable.mujer2
            ContextCompat.getDrawable(this, R.drawable.mujer3)?.constantState -> R.drawable.mujer3
            ContextCompat.getDrawable(this, R.drawable.hombre1)?.constantState -> R.drawable.hombre1
            ContextCompat.getDrawable(this, R.drawable.hombre2)?.constantState -> R.drawable.hombre2
            ContextCompat.getDrawable(this, R.drawable.hombre3)?.constantState -> R.drawable.hombre3
            else -> 0
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun changeRadioButtonColor() {
        val checkedColor = ContextCompat.getColor(this, R.color.red)
        val uncheckedColor = ContextCompat.getColor(this, R.color.darkBlue)
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(checkedColor, uncheckedColor)
        )
        listOf(R.id.male, R.id.female, R.id.red, R.id.blue, R.id.yellow).forEach {
            findViewById<RadioButton>(it).buttonTintList = colorStateList
        }
    }
}
