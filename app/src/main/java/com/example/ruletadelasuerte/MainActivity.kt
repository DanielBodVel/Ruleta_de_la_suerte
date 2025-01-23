package com.example.ruletadelasuerte

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }


        //TODO: Ver con que lincar los otros botones
        findViewById<Button>(R.id.empezar)?.setOnClickListener {
            val intent = Intent(this, PlayerSelect::class.java)
            startActivity(intent)
        }

//        findViewById<Button>(R.id.historial)?.setOnClickListener{
//
//        }
//
//        findViewById<Button>(R.id.button3)?.setOnClickListener{
//
//        }
//
//        findViewById<Button>(R.id.configuracion)?.setOnClickListener{
//
//        }
    }
}