package com.example.ruletadelasuerte

import android.content.Intent
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class PlayerSelect : AppCompatActivity() {

    private val playerName = findViewById<TextInputEditText>(R.id.nameIn)
    private val playerGenderGroup = findViewById<RadioGroup>(R.id.genderGroup)

    private val playerImage = findViewById<ImageView>(R.id.playerImage)
    private val secondImage1 = findViewById<ImageView>(R.id.imageSecondary1)
    private val secondImage2 = findViewById<ImageView>(R.id.imageSecondary2)
    private val secondImage3 = findViewById<ImageView>(R.id.imageSecondary3)

    private val playerColorGroup = findViewById<RadioGroup>(R.id.colorGroup)
    private val selectedGender = playerGenderGroup.checkedRadioButtonId
    private val selectedColor = playerColorGroup.checkedRadioButtonId

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

        playerGenderGroup.setOnCheckedChangeListener { _, _ ->
            updateImage()
        }
        playerColorGroup.setOnCheckedChangeListener { _, _ ->
            updateImage()
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
            if (playerInputName.isEmpty()) {
                Toast.makeText(this, "No hay ningun nombre", Toast.LENGTH_SHORT).show()
            }

            var playerColorString: String? = null

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

            if (playerColorString != null) {
                val intent = Intent(this, TalkActivity::class.java)
                //intent.putExtra("PlayerImage", playerImage)
                intent.putExtra("PlayerColor", playerColorString)
                intent.putExtra("PlayerName", playerInputName)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateImage() {
        when {
            selectedGender == R.id.male && selectedColor == R.id.red -> {
                playerImage.setImageResource(R.drawable.hombrerojo1)
                secondImage1.setImageResource(R.drawable.hombrerojo1)
                secondImage2.setImageResource(R.drawable.hombrerojo2)
                secondImage3.setImageResource(R.drawable.hombrerojo3)
            }

            selectedGender == R.id.female && selectedColor == R.id.red -> {
                playerImage.setImageResource(R.drawable.mujerroja1)
                secondImage1.setImageResource(R.drawable.mujerroja1)
                secondImage2.setImageResource(R.drawable.mujerroja2)
                secondImage3.setImageResource(R.drawable.mujerroja3)
            }

            selectedGender == R.id.male && selectedColor == R.id.blue -> {
                playerImage.setImageResource(R.drawable.hombreazul1)
                secondImage1.setImageResource(R.drawable.hombreazul1)
                secondImage2.setImageResource(R.drawable.hombreazul2)
                secondImage3.setImageResource(R.drawable.hombreazul3)
            }

            selectedGender == R.id.female && selectedColor == R.id.blue -> {
                playerImage.setImageResource(R.drawable.mujerazul1)
                secondImage1.setImageResource(R.drawable.mujerazul1)
                secondImage2.setImageResource(R.drawable.mujerazul2)
                secondImage3.setImageResource(R.drawable.mujerazul3)
            }

            selectedGender == R.id.male && selectedColor == R.id.yellow -> {
                playerImage.setImageResource(R.drawable.hombreamarillo1)
                secondImage1.setImageResource(R.drawable.hombreamarillo1)
                secondImage2.setImageResource(R.drawable.hombreamarillo2)
                secondImage3.setImageResource(R.drawable.hombreamarillo3)
            }

            selectedGender == R.id.female && selectedColor == R.id.yellow -> {
                playerImage.setImageResource(R.drawable.mujeramarilla1)
                secondImage1.setImageResource(R.drawable.mujeramarilla1)
                secondImage2.setImageResource(R.drawable.mujeramarilla2)
                secondImage3.setImageResource(R.drawable.mujeramarilla3)
            }

            else -> {
                playerImage.setImageResource(R.drawable.hombrerojo1)
                secondImage1.setImageResource(R.drawable.hombrerojo1)
                secondImage2.setImageResource(R.drawable.hombrerojo2)
                secondImage3.setImageResource(R.drawable.hombrerojo3)
            }
        }
    }
}