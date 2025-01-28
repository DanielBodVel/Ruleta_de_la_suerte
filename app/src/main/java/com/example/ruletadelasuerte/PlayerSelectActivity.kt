package com.example.ruletadelasuerte

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class PlayerSelectActivity : AppCompatActivity() {
    private var contadorJugadores = 1

    private val mapaJugadores: MutableMap<String, Any?> = mutableMapOf(
        Pair("name1", null),
        Pair("image1", null),
        Pair("color1", null),
        Pair("name2", null),
        Pair("image2", null),
        Pair("color2", null),
        Pair("name3", null),
        Pair("image3", null),
        Pair("color3", null)
    )

    private lateinit var playerName: TextInputEditText
    private lateinit var playerGenderGroup: RadioGroup
    private lateinit var playerColorGroup: RadioGroup

    private lateinit var playerImage: ImageView
    private lateinit var secondImage1: ImageView
    private lateinit var secondImage2: ImageView
    private lateinit var secondImage3: ImageView

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

        playerName = findViewById(R.id.nameIn)
        playerGenderGroup = findViewById(R.id.genderGroup)
        playerColorGroup = findViewById(R.id.colorGroup)

        playerImage = findViewById(R.id.playerImage)
        secondImage1 = findViewById(R.id.imageSecondary1)
        secondImage2 = findViewById(R.id.imageSecondary2)
        secondImage3 = findViewById(R.id.imageSecondary3)

        val selectedColor = playerColorGroup.checkedRadioButtonId
        var playerColorString: String? = null

        playerGenderGroup.setOnCheckedChangeListener { _, _ ->
            updateImage()
        }

        playerColorGroup.setOnCheckedChangeListener { _, _ ->
            when (selectedColor) {
                R.id.red -> {
                    val buttonSelected = findViewById<RadioButton>(R.id.red)
                    playerColorString = buttonSelected.text.toString()
                }

                R.id.blue -> {
                    val buttonSelected = findViewById<RadioButton>(R.id.blue)
                    playerColorString = buttonSelected.text.toString()
                }

                R.id.yellow -> {
                    val buttonSelected = findViewById<RadioButton>(R.id.yellow)
                    playerColorString = buttonSelected.text.toString()
                }
            }
        }

        secondImage1.setOnClickListener {
            val principalImage = secondImage1.drawable
            playerImage.setImageDrawable(principalImage)
        }

        secondImage2.setOnClickListener {
            val principalImage = secondImage2.drawable
            playerImage.setImageDrawable(principalImage)
        }

        secondImage3.setOnClickListener {
            val principalImage = secondImage3.drawable
            playerImage.setImageDrawable(principalImage)
        }

        findViewById<Button>(R.id.next)?.setOnClickListener {
            val playerInputName = playerName.text.toString().trim()
            val platerImage = getImageNameFromResource(this, R.id.playerImage)

            if (playerColorGroup.checkedRadioButtonId != -1 && playerInputName.isNotEmpty() && playerGenderGroup.checkedRadioButtonId != -1) {
                if (contadorJugadores <= 3) {
                    when (contadorJugadores) {
                        1 -> {
                            mapaJugadores["name1"] = playerInputName
                            mapaJugadores["image1"] = platerImage
                            mapaJugadores["color1"] = playerColorString
                        }

                        2 -> {
                            mapaJugadores["name2"] = playerInputName
                            mapaJugadores["image2"] = platerImage
                            mapaJugadores["color2"] = playerColorString
                        }

                        3 -> {
                            mapaJugadores["name3"] = playerInputName
                            mapaJugadores["image3"] = platerImage
                            mapaJugadores["color3"] = playerColorString
                        }

                        else -> Log.d(
                            "Error",
                            "Fallo en agragagación del jugador $contadorJugadores"
                        )
                    }
                    Toast.makeText(this, "Siguiente jugador", Toast.LENGTH_SHORT).show()
                    contadorJugadores += 1
                } else {
                    // Convertir el mutableMapOf a HashMap (compatible con el Intent)
                    val hashMapJugadores = HashMap(mapaJugadores)
                    val intent = Intent(this, TalkActivity::class.java)
                    intent.putExtra("Players", hashMapJugadores)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show()
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

    private fun getImageNameFromResource(context: Context, resourceId: Int): String {
        return context.resources.getResourceEntryName(resourceId)
    }
}