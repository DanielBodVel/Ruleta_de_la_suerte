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

class PlayerSelectActivity : AppCompatActivity() {
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

        val playerName = findViewById<TextInputEditText>(R.id.nameIn)
        val playerGenderGroup = findViewById<RadioGroup>(R.id.genderGroup)
        val playerColorGroup = findViewById<RadioGroup>(R.id.colorGroup)

        val playerImage = findViewById<ImageView>(R.id.playerImage)
        val secondImage1 = findViewById<ImageView>(R.id.imageSecondary1)
        val secondImage2 = findViewById<ImageView>(R.id.imageSecondary2)
        val secondImage3 = findViewById<ImageView>(R.id.imageSecondary3)

        val selectedColor = playerColorGroup.checkedRadioButtonId
        var playerColorString: String? = null

        playerGenderGroup.setOnCheckedChangeListener { _, _ ->
            updateImage(
                playerGenderGroup,
                playerImage,
                secondImage1,
                secondImage2,
                secondImage3
            )
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
            if (playerInputName.isEmpty()) {
                Toast.makeText(this, "No hay ningun nombre", Toast.LENGTH_SHORT).show()
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

    private fun updateImage(
        playerGenderGroup: RadioGroup,
        playerImage: ImageView,
        secondImage1: ImageView,
        secondImage2: ImageView,
        secondImage3: ImageView
    ) {
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
}