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

        secondImage1.setOnClickListener {
            playerImage.setImageDrawable(secondImage1.drawable)
            imagenSeleccionada = getImageResourceFromDrawable(secondImage1.drawable)
        }

        secondImage2.setOnClickListener {
            playerImage.setImageDrawable(secondImage2.drawable)
            imagenSeleccionada = getImageResourceFromDrawable(secondImage2.drawable)
        }

        secondImage3.setOnClickListener {
            playerImage.setImageDrawable(secondImage3.drawable)
            imagenSeleccionada = getImageResourceFromDrawable(secondImage3.drawable)
        }
        playerGenderGroup.setOnCheckedChangeListener { _, _ ->
            updateImage()
        }

        findViewById<Button>(R.id.next)?.setOnClickListener {
            val playerInputName = playerName.text.toString().trim()
            val playerColor = when (playerColorGroup.checkedRadioButtonId) {
                R.id.red -> R.color.redUser
                R.id.blue -> R.color.blueUser
                R.id.yellow -> R.color.yellowUser
                else -> "#FFFFFF"
            }

            if (playerColorGroup.checkedRadioButtonId != -1 && playerInputName.isNotEmpty() && playerGenderGroup.checkedRadioButtonId != -1) {
                when (contadorJugadores) {
                    1 -> {
                        jugador1Nombre = playerInputName
                        jugador1Color = playerColor.toString()
                        jugador1Imagen = R.drawable.hombre1
                        contadorJugadores++
                        Toast.makeText(this, "Siguiente jugador", Toast.LENGTH_SHORT).show()
                        playerName.text?.clear()
                    }

                    2 -> {
                        jugador2Nombre = playerInputName
                        jugador2Color = playerColor.toString()
                        jugador2Imagen = imagenSeleccionada
                        contadorJugadores++
                        Toast.makeText(this, "Siguiente jugador", Toast.LENGTH_SHORT).show()
                        playerName.text?.clear()
                    }

                    3 -> {
                        jugador3Nombre = playerInputName
                        jugador3Color = playerColor.toString()
                        jugador3Imagen = imagenSeleccionada

                        val intent = Intent(this, TalkActivity::class.java)
                        intent.putExtra("jugador1Nombre", jugador1Nombre)
                        intent.putExtra("jugador1Color", jugador1Color)
                        intent.putExtra("jugador1Imagen", jugador1Imagen)

                        intent.putExtra("jugador2Nombre", jugador2Nombre)
                        intent.putExtra("jugador2Color", jugador2Color)
                        intent.putExtra("jugador2Imagen", jugador2Imagen)

                        intent.putExtra("jugador3Nombre", jugador3Nombre)
                        intent.putExtra("jugador3Color", jugador3Color)
                        intent.putExtra("jugador3Imagen", jugador3Imagen)

                        startActivity(intent)
                    }
                }
            } else {
                Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateImage() {
        val selectedGender = playerGenderGroup.checkedRadioButtonId
        when {
            selectedGender == R.id.female -> {
                playerImage.setImageResource(R.drawable.mujer1)
                secondImage1.setImageResource(R.drawable.mujer1)
                secondImage2.setImageResource(R.drawable.mujer2)
                secondImage3.setImageResource(R.drawable.mujer3)
            }

            else -> {
                playerImage.setImageResource(R.drawable.hombre1)
                secondImage1.setImageResource(R.drawable.hombre1)
                secondImage2.setImageResource(R.drawable.hombre2)
                secondImage3.setImageResource(R.drawable.hombre3)
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
            else -> R.drawable.mujer1
        }
    }

    private fun changeRadioButtonColor() {
        val checkedColor = ContextCompat.getColor(this, R.color.red)
        val uncheckedColor = ContextCompat.getColor(this, R.color.darkBlue)

        val radioButtonMale = findViewById<RadioButton>(R.id.male)
        val radioButtonFemale = findViewById<RadioButton>(R.id.female)

        val radioButtonRed = findViewById<RadioButton>(R.id.red)
        val radioButtonBlue = findViewById<RadioButton>(R.id.blue)
        val radioButtonYellow = findViewById<RadioButton>(R.id.yellow)

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(checkedColor, uncheckedColor)
        )
        radioButtonMale.buttonTintList = colorStateList
        radioButtonFemale.buttonTintList = colorStateList
        radioButtonRed.buttonTintList = colorStateList
        radioButtonBlue.buttonTintList = colorStateList
        radioButtonYellow.buttonTintList = colorStateList
    }
}