package com.example.ruletadelasuerte

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ruletadelasuerte.utils.GameSaver

class HistoryActivity : AppCompatActivity() {
    private val gameDAO = GameSaver(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val games = gameDAO.getAllGames()
        games.forEach { game ->
            Toast.makeText(
                this,
                "Partida ID ${game["id"]}, Ganador: ${game["winner"]}, Fecha: ${game["date_time"]}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}