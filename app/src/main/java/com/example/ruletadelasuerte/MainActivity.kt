package com.example.ruletadelasuerte

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ruletadelasuerte.base.BaseActivity

class MainActivity : BaseActivity() {
    private lateinit var mediaPlayer: MediaPlayer // Reproductor de audio
    private val handler =
        Handler(Looper.getMainLooper()) // Handler para ejecutar tareas en segundo plano
    private val fadeDuration = 1500 // Duración del efecto de desvanecimiento en milisegundos
    private val fadeSteps = 30 // Número de pasos para la atenuación del sonido
    private val delay = fadeDuration / fadeSteps // Retraso entre cada paso de atenuación

    private lateinit var imagenRuleta: ImageView
    private lateinit var imagenPresentadores: ImageView
    private lateinit var imagenTitulo: ImageView
    private lateinit var textoComenzar: TextView

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita la visualización sin bordes en la pantalla
        setContentView(R.layout.activity_main)

        // Ajusta los márgenes de los elementos de la UI según las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Oculta la barra de estado para una experiencia de pantalla completa
        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        musicaStart() // Inicia la música de fondo
        animacionesStart() // Ejecuta las animaciones de la pantalla de inicio

        // Al hacer clic en la pantalla, se abre la actividad de selección de jugadores
        findViewById<ConstraintLayout>(R.id.main).setOnClickListener {
            val intent = Intent(this@MainActivity, PlayerSelectActivity::class.java)
            startActivity(intent)
        }

        // Botón para ir al historial de partidas
        findViewById<ImageButton>(R.id.historial)?.setOnClickListener {
            val intent = Intent(this@MainActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Botón para acceder a la configuración
        findViewById<ImageButton>(R.id.configuracion)?.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    // Método para iniciar las animaciones de los elementos de la UI
    private fun animacionesStart() {
        imagenRuleta = findViewById(R.id.ruleta)
        imagenPresentadores = findViewById(R.id.presentadores)
        imagenTitulo = findViewById(R.id.titulo)
        textoComenzar = findViewById(R.id.comenzar)

        // Animación de la ruleta: rotación, aparición y escalado
        val animatorRuleta = ObjectAnimator.ofPropertyValuesHolder(
            imagenRuleta,
            PropertyValuesHolder.ofFloat("rotation", 0f, 360f), // Gira 360 grados
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f), // Aparece progresivamente
            PropertyValuesHolder.ofFloat("scaleX",0f,1f), // Escala en X desde 0 hasta su tamaño normal
            PropertyValuesHolder.ofFloat("scaleY",0f,1f)  // Escala en Y desde 0 hasta su tamaño normal
        ).apply {
            duration = 5000 // Duración de 5 segundos
            interpolator =
                AccelerateDecelerateInterpolator() // Suaviza el inicio y fin de la animación
            repeatMode = ValueAnimator.REVERSE
        }

        // Animación de los presentadores: aparición y desplazamiento vertical
        val animatorPresentadores = ObjectAnimator.ofPropertyValuesHolder(
            imagenPresentadores,
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f), // Aparece progresivamente
            PropertyValuesHolder.ofFloat("translationY", 300f, 0f) // Se desliza hacia arriba
        ).apply {
            duration = 5000
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Animación del título: rotación inversa y aparición
        val animatorTitulo = ObjectAnimator.ofPropertyValuesHolder(
            imagenTitulo,
            PropertyValuesHolder.ofFloat("rotation",360f, 0f), // Gira en sentido contrario a la ruleta
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f)
        ).apply {
            duration = 7000
            interpolator = AccelerateDecelerateInterpolator()
        }

        // Animación del texto "Comenzar": latido (zoom in y out)
        val animatorTexto = ObjectAnimator.ofPropertyValuesHolder(
            textoComenzar,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f)
        ).apply {
            duration = 1000
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE // Se repite indefinidamente
            interpolator = LinearInterpolator()
        }

        // Inicia todas las animaciones
        animatorRuleta.start()
        animatorPresentadores.start()
        animatorTitulo.start()
        animatorTexto.start()
    }

    // Método para iniciar la música de fondo
    private fun musicaStart() {
        mediaPlayer = MediaPlayer.create(this, R.raw.entrada_ruleta) // Carga el archivo de audio
        mediaPlayer.start() // Reproduce la música

        val songDuration = mediaPlayer.duration // Obtiene la duración total de la música
        val fadeStartTime = songDuration - fadeDuration // Momento en el que debe empezar el fade out

        // Programa el inicio del fade out antes de que termine la música
        handler.postDelayed({ fadeOutAndStop() }, fadeStartTime.toLong())
    }

    // Método para reducir gradualmente el volumen y detener la música
    private fun fadeOutAndStop() {
        var volume = 1.0f // Volumen inicial al 100%
        val step = volume / fadeSteps // Paso de reducción del volumen

        handler.post(object : Runnable {
            override fun run() {
                if (volume > 0 && this@MainActivity::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
                    volume -= step
                    if (volume < 0) volume = 0f // Asegura que el volumen no sea negativo
                    mediaPlayer.setVolume(volume, volume) // Aplica el volumen reducido
                    handler.postDelayed(this, delay.toLong()) // Repite el proceso
                } else {
                    if (this@MainActivity::mediaPlayer.isInitialized) {
                        mediaPlayer.stop() // Detiene la música cuando el volumen llega a 0
                        mediaPlayer.release() // Libera los recursos del MediaPlayer
                    }
                }
            }
        })
    }

    // Método llamado al destruir la actividad, libera los recursos del MediaPlayer
    override fun onDestroy() {
        super.onDestroy()
        if (this@MainActivity::mediaPlayer.isInitialized) { // Verifica si el MediaPlayer fue inicializado
            mediaPlayer.release()
        }
    }
}
