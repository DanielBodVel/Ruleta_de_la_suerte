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
    private lateinit var name1: String
    private lateinit var name2: String
    private lateinit var name3: String

    private lateinit var image1: String
    private lateinit var image2: String
    private lateinit var image3: String

    private var balance1: Int = 0
    private var balance2: Int = 0
    private var balance3: Int = 0

    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextView
    private lateinit var playerSale1: TextView
    private lateinit var playerSale2: TextView
    private lateinit var playerSale3: TextView


    //DATOS FAKE PARA BASE DE DATOS
    private var nombreJugador1: String = "Daniel"
    private var nombreJugador2: String = "Abel"
    private var nombreJugador3: String = "David"
    private var ganancia1: Int = 5000
    private var ganancia2: Int = 200
    private var ganancia3: Int = 800

    private val gameSaver = GameSaver(this)


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

        val bundle = intent.extras
        if (bundle != null) {
            name1 = bundle.getString("name1").toString()
            image1 = bundle.getString("image1").toString()
            balance1 = bundle.getInt("balance1")

            name2 = bundle.getString("name2").toString()
            image2 = bundle.getString("image2").toString()
            balance2 = bundle.getInt("balance2")

            name3 = bundle.getString("name3").toString()
            image3 = bundle.getString("image3").toString()
            balance3 = bundle.getInt("balance3")
        }

        playerImage = findViewById(R.id.playerImageEnd)
        playerName = findViewById(R.id.textWinner)
        playerSale1 = findViewById(R.id.salePlayer1)
        playerSale2 = findViewById(R.id.salePlayer2)
        playerSale3 = findViewById(R.id.salePlayer3)

        if (balance1 > balance2) {
            if (balance1 > balance3) {
                //Personaje 1
                //val res = datosIn["aavatar1"]
                val resId = resources.getIdentifier(image1, "drawable", packageName)
                playerImage.setImageResource(resId)

                playerName.text = "¡Ha ganado $name1 !"
                playerSale1.text = "El jugador $name1 ha ganado: $balance1€"
                playerSale2.text = "El jugador $name2 ha ganado: $balance2€"
                playerSale3.text = "El jugador $name3 ha ganado: $balance3€"
            } else {
                //Personaje 3

                playerName.text = "¡Ha ganado $name3 !"
                playerSale1.text = "El jugador $name1 ha ganado: $balance1€"
                playerSale2.text = "El jugador $name2 ha ganado: $balance2€"
                playerSale3.text = "El jugador $name3 ha ganado: $balance3€"
            }
        } else {
            if (balance2 > balance3) {
                //Personaje 2

                playerName.text = "¡Ha ganado $name2!"
                playerSale1.text = "El jugador $name1 ha ganado: $balance1€"
                playerSale2.text = "El jugador $name2 ha ganado: $balance2€"
                playerSale3.text = "El jugador $name3 ha ganado: $balance3€"
            } else {
                //Personaje 3

                playerName.text = "¡Ha ganado $name3!"
                playerSale1.text = "El jugador $name1 ha ganado: $balance1€"
                playerSale2.text = "El jugador $name2 ha ganado: $balance2€"
                playerSale3.text = "El jugador $name3 ha ganado: $balance3€"
            }
        }

        findViewById<ImageButton>(R.id.imageButtonBack)?.setOnClickListener {
            val id = gameSaver.insertGame(nombreJugador1, ganancia1, nombreJugador2, ganancia2, nombreJugador3, ganancia3)
            if (id != -1L) {
                Toast.makeText(this, "Partida guardada correctamente", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}