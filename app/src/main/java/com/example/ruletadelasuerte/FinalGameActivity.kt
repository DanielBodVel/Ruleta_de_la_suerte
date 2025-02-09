package com.example.ruletadelasuerte

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.atan2
import kotlin.random.Random

class FinalGameActivity : AppCompatActivity() {
    private lateinit var ruleta: ImageView
    private lateinit var textoPista: TextView
    private lateinit var panelLetras: GridLayout
    private lateinit var contenedorRuleta: LinearLayout
    private lateinit var puntero: ImageView
    private lateinit var textoInstruccion: TextView
    private lateinit var contenedorEntradas: LinearLayout
    private lateinit var botonMostrar: Button
    private lateinit var entradaConsonante1: EditText
    private lateinit var entradaConsonante2: EditText
    private lateinit var entradaConsonante3: EditText
    private lateinit var entradaVocal: EditText
    private lateinit var textoAdivinarFrase: TextView
    private lateinit var contador: TextView
    private lateinit var entradaFrase: EditText
    private lateinit var botonComprobar: Button
    private var inicioAngulo = 0f
    private var rotacionFinal = 0f
    private var fraseActual: String = ""
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var pistasYFrases: Array<Pair<String, String>>
    private var contadorActivo = true
    private lateinit var frase: String
    private lateinit var pista: String


    private lateinit var jugador1Nombre: String
    private var saldo1Jugador: Int = 0
    private lateinit var jugador1Color: String
    private var jugador1Imagen: Int = 0
    private lateinit var jugador2Nombre: String
    private var saldo2Jugador: Int = 0
    private lateinit var jugador2Color: String
    private var jugador2Imagen: Int = 0
    private lateinit var jugador3Nombre: String
    private var saldo3Jugador: Int = 0
    private lateinit var jugador3Color: String
    private var jugador3Imagen: Int = 0


    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.insetsController?.let {
            it.hide(WindowInsets.Type.statusBars())
            it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        puntero = findViewById(R.id.puntero)
        val animator = ObjectAnimator.ofFloat(puntero, "translationY", -10f, 5f).apply {
            duration = 1000 // Duración de 1 segundo
            interpolator = LinearInterpolator()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
        }
        animator.start()

        pistasYFrases = arrayOf(
            Pair(getString(R.string.end_panelFinal_sinonimos), getString(R.string.end_FraseFinal1)),
            Pair(getString(R.string.end_panelFinal_sinonimos), getString(R.string.end_FraseFinal2)),
            Pair(getString(R.string.end_panelFinal_sinonimos), getString(R.string.end_FraseFinal3)),
            Pair(getString(R.string.end_panelFinal_libroAutor), getString(R.string.end_FraseFinal4)),
            Pair(getString(R.string.end_panelFinal_libroAutor), getString(R.string.end_FraseFinal5)),
            Pair(getString(R.string.end_panelFinal_libroAutor), getString(R.string.end_FraseFinal6)),
            Pair(getString(R.string.end_panelFinal_peliculaDirector), getString(R.string.end_FraseFinal7)),
            Pair(getString(R.string.end_panelFinal_peliculaDirector), getString(R.string.end_FraseFinal8)),
            Pair(getString(R.string.end_panelFinal_peliculaDirector), getString(R.string.end_FraseFinal9)),
            Pair(getString(R.string.end_panelFinal_peliculaDirector), getString(R.string.end_FraseFinal10))
        )

        ruleta = findViewById(R.id.imageView)
        textoPista = findViewById(R.id.textoPista)
        panelLetras = findViewById(R.id.panelLetras)
        contenedorRuleta = findViewById(R.id.contenedorRuleta)
        textoInstruccion = findViewById(R.id.textoInstruccion)
        contenedorEntradas = findViewById(R.id.contenedorEntradas)
        botonMostrar = findViewById(R.id.botonMostrar)
        entradaConsonante1 = findViewById(R.id.entradaConsonante1)
        entradaConsonante2 = findViewById(R.id.entradaConsonante2)
        entradaConsonante3 = findViewById(R.id.entradaConsonante3)
        entradaVocal = findViewById(R.id.entradaVocal)
        textoAdivinarFrase = findViewById(R.id.textoAdivinarFrase)
        contador = findViewById(R.id.contador)
        entradaFrase = findViewById(R.id.entradaFrase)
        botonComprobar = findViewById(R.id.botonComprobar)

        inicializarJugadores()

        // Configura el listener para girar la ruleta al tocar la pantalla
        ruleta.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    inicioAngulo = obtenerAngulo(event.x, event.y)
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    val anguloActual = obtenerAngulo(event.x, event.y)
                    val deltaAngulo = anguloActual - inicioAngulo
                    ruleta.rotation += deltaAngulo
                    inicioAngulo = anguloActual
                    true
                }

                MotionEvent.ACTION_UP -> {
                    girarRuleta()
                    true
                }

                else -> false
            }
        }


        // Añade un listener a los EditText para validar las entradas en tiempo real
        val editTexts =
            listOf(entradaConsonante1, entradaConsonante2, entradaConsonante3, entradaVocal)
        editTexts.forEach { editText ->
            editText.addTextChangedListener(object : android.text.TextWatcher {
                override fun afterTextChanged(s: android.text.Editable?) {
                    validarEntradas()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        // Configura el listener del botón "Mostrar" para comprobar las letras introducidas
        botonMostrar.setOnClickListener {
            val consonante1 = entradaConsonante1.text.toString().uppercase()
            val consonante2 = entradaConsonante2.text.toString().uppercase()
            val consonante3 = entradaConsonante3.text.toString().uppercase()
            val vocal = entradaVocal.text.toString().uppercase()

            if (consonante1.isNotEmpty() && consonante2.isNotEmpty() && consonante3.isNotEmpty() && vocal.isNotEmpty()) {
                comprobarLetra(consonante1)
                comprobarLetra(consonante2)
                comprobarLetra(consonante3)
                comprobarLetra(vocal)
                comprobarLetra("R")
                comprobarLetra("S")
                comprobarLetra("F")
                comprobarLetra("Y")
                comprobarLetra("O")
                Handler(Looper.getMainLooper()).postDelayed({
                    mostrarApartadoAdivinarFrase()
                }, 2000)
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.end_introduce_todas_letras),
                    Toast.LENGTH_SHORT
                ).show()
            }
            hideKeyboard()
        }

        // Configura el listener del botón "Comprobar" para validar la frase introducida
        botonComprobar.setOnClickListener {
            val fraseUsuario = entradaFrase.text.toString().uppercase()
            if (fraseUsuario == fraseActual.uppercase()) {
                Toast.makeText(
                    this,
                    getString(R.string.end_felicidades_acertaste),
                    Toast.LENGTH_SHORT
                ).show()
                countDownTimer.cancel()
                contadorActivo = false

                val (jugadorConMasSaldo, saldoMaximo) = listOf(
                    Triple(jugador1Nombre, saldo1Jugador, ::saldo1Jugador),
                    Triple(jugador2Nombre, saldo2Jugador, ::saldo2Jugador),
                    Triple(jugador3Nombre, saldo3Jugador, ::saldo3Jugador)
                ).maxByOrNull { it.second } ?: return@setOnClickListener
                val premio = listOf(200, 500, 1000, 2000).random()

                // Asignar el premio al jugador con más saldo
                when (jugadorConMasSaldo) {
                    jugador1Nombre -> saldo1Jugador += premio
                    jugador2Nombre -> saldo2Jugador += premio
                    jugador3Nombre -> saldo3Jugador += premio
                }
                Toast.makeText(
                    this,
                    "${jugadorConMasSaldo} ha recibido un premio de $premio!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(this, getString(R.string.end_frase_incorrecta), Toast.LENGTH_SHORT).show()
            }
            hideKeyboard()

            val newIntent = Intent(this, EndActivity::class.java)
            newIntent.putExtra("jugador1Nombre", jugador1Nombre)
            newIntent.putExtra("saldo1Jugador", saldo1Jugador)
            newIntent.putExtra("jugador1Color", jugador1Color)
            newIntent.putExtra("jugador1Imagen", jugador1Imagen)

            newIntent.putExtra("jugador2Nombre", jugador2Nombre)
            newIntent.putExtra("saldo2Jugador", saldo2Jugador)
            newIntent.putExtra("jugador2Color", jugador2Color)
            newIntent.putExtra("jugador2Imagen", jugador2Imagen)

            newIntent.putExtra("jugador3Nombre", jugador3Nombre)
            newIntent.putExtra("saldo3Jugador", saldo3Jugador)
            newIntent.putExtra("jugador3Color", jugador3Color)
            newIntent.putExtra("jugador3Imagen", jugador3Imagen)

            startActivity(newIntent)
        }
    }


    private fun inicializarJugadores() {
        jugador1Nombre = intent.getStringExtra("jugador1Nombre") ?: "Jugador 1"
        saldo1Jugador = intent.getIntExtra("saldo1Jugador", 0)
        jugador1Color = intent.getStringExtra("jugador1Color") ?: "#FFFFFF"
        jugador1Imagen = intent.getIntExtra("jugador1Imagen", R.drawable.mujer1)

        jugador2Nombre = intent.getStringExtra("jugador2Nombre") ?: "Jugador 2"
        saldo2Jugador = intent.getIntExtra("saldo2Jugador", 0)
        jugador2Color = intent.getStringExtra("jugador2Color") ?: "#FFFFFF"
        jugador2Imagen = intent.getIntExtra("jugador2Imagen", R.drawable.hombre1)

        jugador3Nombre = intent.getStringExtra("jugador3Nombre") ?: "Jugador 3"
        saldo3Jugador = intent.getIntExtra("saldo3Jugador", 0)
        jugador3Color = intent.getStringExtra("jugador3Color") ?: "#FFFFFF"
        jugador3Imagen = intent.getIntExtra("jugador3Imagen", R.drawable.mujer2)

    }

    // Verifica si una letra es una consonante
    private fun esConsonante(letra: String): Boolean {
        val consonantes = "BCDFGHJKLMNÑPQRSTVWXYZ"
        return letra.uppercase() in consonantes
    }

    // Verifica si una letra es una vocal
    private fun esVocal(letra: String): Boolean {
        val vocales = "AEIOU"
        return letra.uppercase() in vocales
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    // Valida las entradas de los EditText y habilita/deshabilita el botón "Mostrar"
    private fun validarEntradas() {
        val consonante1 = entradaConsonante1.text.toString()
        val consonante2 = entradaConsonante2.text.toString()
        val consonante3 = entradaConsonante3.text.toString()
        val vocal = entradaVocal.text.toString()

        val consonantesValidas =
            esConsonante(consonante1) && esConsonante(consonante2) && esConsonante(consonante3)
        val vocalValida = esVocal(vocal)

        botonMostrar.isEnabled = consonantesValidas && vocalValida
    }

    // Calcula el ángulo de rotación basado en la posición del toque en la pantalla
    private fun obtenerAngulo(x: Float, y: Float): Float {
        val centroX = ruleta.width / 2
        val centroY = ruleta.height / 2
        val deltaX = x - centroX
        val deltaY = y - centroY
        return (atan2(deltaY, deltaX) * (180 / Math.PI)).toFloat()
    }

    // Gira la ruleta y selecciona un sector aleatorio
    private fun girarRuleta() {
        val anguloPorSector = 360f / pistasYFrases.size
        val sectorElegido = Random.nextInt(pistasYFrases.size)
        val anguloCentroSector = sectorElegido * anguloPorSector + (anguloPorSector / 2)
        val girosCompletos = Random.nextInt(5, 10) * 360
        val nuevaRotacion = girosCompletos + anguloCentroSector
        rotacionFinal = nuevaRotacion % 360

        ruleta.animate()
            .rotation(nuevaRotacion)
            .setDuration(3000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                Handler(Looper.getMainLooper()).postDelayed({
                    mostrarResultado(sectorElegido)
                }, 2000)
            }
            .start()
    }

    // Muestra el resultado de la ruleta y configura la interfaz para el siguiente paso
    private fun mostrarResultado(sector: Int) {
        val (pista, frase) = pistasYFrases[sector]
        this.pista = pista
        this.frase = frase
        this.fraseActual = frase

        contenedorRuleta.visibility = View.GONE
        textoPista.visibility = View.VISIBLE
        panelLetras.visibility = View.VISIBLE
        textoInstruccion.visibility = View.VISIBLE
        contenedorEntradas.visibility = View.VISIBLE
        botonMostrar.visibility = View.VISIBLE

        textoPista.text = getString(R.string.end_pista, pista)
        llenarPanelLetras(panelLetras, frase)

        Toast.makeText(
            this,
            getString(R.string.end_pista_del_panel_final, pista),
            Toast.LENGTH_LONG
        ).show()
    }


    // Llena el panel de letras con la frase seleccionada
    private fun llenarPanelLetras(panel: GridLayout, frase: String) {
        panel.removeAllViews()
        val maxCaracteresPorLinea = 12
        val palabras = frase.split(" ")

        val lineas = mutableListOf<String>()
        var lineaActual = StringBuilder()

        for (palabra in palabras) {
            if (lineaActual.length + palabra.length + 1 > maxCaracteresPorLinea) {
                lineas.add(lineaActual.toString().trim())
                lineaActual = StringBuilder()
            }
            lineaActual.append("$palabra ")
        }

        if (lineaActual.isNotEmpty()) {
            lineas.add(lineaActual.toString().trim())
        }

        panel.rowCount = lineas.size
        panel.columnCount = maxCaracteresPorLinea

        for (linea in lineas) {
            val espaciosTotales = maxCaracteresPorLinea - linea.length
            val espaciosIzquierda = espaciosTotales / 2
            val espaciosDerecha = espaciosTotales - espaciosIzquierda

            repeat(espaciosIzquierda) { agregarCuadradoGris(panel) }
            linea.forEach { caracter ->
                val vistaLetra = TextView(this).apply {
                    text = if (caracter == ' ') " " else "_"
                    tag = caracter.toString().uppercase()
                    textSize = 24f
                    gravity = Gravity.CENTER
                    setBackgroundColor(
                        if (caracter == ' ') getColor(android.R.color.darker_gray) else getColor(
                            android.R.color.white
                        )
                    )
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = GridLayout.LayoutParams.WRAP_CONTENT
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        setMargins(4, 4, 4, 4)
                    }
                }
                panel.addView(vistaLetra)
            }
            repeat(espaciosDerecha) { agregarCuadradoGris(panel) }
        }
    }


    // Añade un cuadrado gris al panel de letras
    private fun agregarCuadradoGris(panel: GridLayout) {
        val cuadradoGris = TextView(this).apply {
            text = " "
            textSize = 24f
            gravity = Gravity.CENTER
            setBackgroundColor(getColor(android.R.color.darker_gray))
            layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(4, 4, 4, 4)
            }
        }
        panel.addView(cuadradoGris)
    }

    // Comprueba si una letra está en la frase y la revela en el panel
    private fun comprobarLetra(letra: String) {
        val letraNormalizada = letra.uppercase()
        var letraEncontrada = false

        for (i in 0 until panelLetras.childCount) {
            val vista = panelLetras.getChildAt(i)
            if (vista is TextView) {
                val letraOculta = vista.tag as? String
                if (letraOculta == letraNormalizada && vista.text == "_") {
                    vista.text = letraNormalizada
                    letraEncontrada = true
                }
            }
        }
    }


    // Muestra el apartado para adivinar la frase completa
    private fun mostrarApartadoAdivinarFrase() {
        textoInstruccion.visibility = View.GONE
        contenedorEntradas.visibility = View.GONE
        botonMostrar.visibility = View.GONE

        textoAdivinarFrase.visibility = View.VISIBLE
        contador.visibility = View.VISIBLE
        entradaFrase.visibility = View.VISIBLE
        botonComprobar.visibility = View.VISIBLE

        iniciarContador()
    }

    // Inicia un contador de tiempo para adivinar la frase
    private fun iniciarContador() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (contadorActivo) {
                    contador.text = (millisUntilFinished / 1000).toString()
                }
            }

            override fun onFinish() {
                if (contadorActivo) {
                    contador.text = "0"
                    Toast.makeText(
                        this@FinalGameActivity,
                        getString(R.string.end_tiempo_agotado),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@FinalGameActivity, EndActivity::class.java)
                    startActivity(intent)

                    finish()
                }
            }
        }.start()

    }
}