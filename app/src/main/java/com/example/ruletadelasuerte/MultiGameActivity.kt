package com.example.ruletadelasuerte

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
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

@SuppressLint("SetTextI18n")
class MultiGameActivity : AppCompatActivity() {
    private lateinit var textoPista: TextView
    private lateinit var panelLetras: GridLayout
    private lateinit var jugador1: TextView
    private lateinit var jugador2: TextView
    private lateinit var jugador3: TextView

    private lateinit var ruleta: ImageView
    private lateinit var contendorRuleta: LinearLayout
    private lateinit var puntero: ImageView
    private lateinit var contenedorAdivinarLetra: LinearLayout
    private lateinit var contendorBotones: LinearLayout
    private lateinit var contenedorResolver: LinearLayout
    private lateinit var contenedorComprarLetra: LinearLayout
    private lateinit var letraEscogida: EditText
    private lateinit var botonLetraEscogida: Button
    private lateinit var valorSaldoJugador: TextView
    private lateinit var valorCeldaTirada: TextView
    private lateinit var btnOpcion1: Button
    private lateinit var btnOpcion2: Button
    private lateinit var btnOpcion3: Button

    private lateinit var btnComprobarLetraComprar: Button
    private lateinit var entradaLetraComprar: EditText
    private lateinit var valorSaldoComprar: TextView

    private lateinit var btnComprobarPanel: Button
    private lateinit var entradaFrase: EditText

    private lateinit var textoValorAcumulado: TextView

    private var jugadorActivo = 0
    private lateinit var frase: String

    private var inicioAngulo = 0f
    private var rotacionFinal = 0f
    private lateinit var frasesYPistas: Array<Pair<String, String>>
    private lateinit var sectores2: Array<String>
    private var saldoAcumulado = 0

    private var frasesResueltas = 0
    private val costoCompraLetra = 50


    //Datos de los jugadores
    private var saldoJugador1 = 0
    private lateinit var avatarJugador1: ImageView
    private lateinit var nombreJugador1: TextView
    private lateinit var linearJugador1: LinearLayout

    private var saldoJugador2 = 0
    private lateinit var avatarJugador2: ImageView
    private lateinit var nombreJugador2: TextView
    private lateinit var linearJugador2: LinearLayout

    private var saldoJugador3 = 0
    private lateinit var avatarJugador3: ImageView
    private lateinit var nombreJugador3: TextView
    private lateinit var linearJugador3: LinearLayout

    private lateinit var jugador1Color: String
    private var jugador1Imagen: Int = 0
    private lateinit var jugador2Color: String
    private var jugador2Imagen: Int = 0
    private lateinit var jugador3Color: String
    private var jugador3Imagen: Int = 0

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_multi_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.principal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicializacionVaribles()
        inicializarJugadores()
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

        frasesYPistas = arrayOf(
            Pair(getString(R.string.titular_loco), getString(R.string.frase_titular_loco1)),
            Pair(getString(R.string.titular_loco), getString(R.string.frase_titular_loco2)),
            Pair(getString(R.string.titular_loco), getString(R.string.frase_titular_loco3)),
            Pair(getString(R.string.letra_cancion), getString(R.string.frase_letra_cancion1)),
            Pair(getString(R.string.letra_cancion), getString(R.string.frase_letra_cancion2)),
            Pair(getString(R.string.letra_cancion), getString(R.string.frase_letra_cancion3)),
            Pair(
                getString(R.string.descubrimiento_cientifico),
                getString(R.string.frase_descubrimiento_cientifico1)
            ),
            Pair(
                getString(R.string.descubrimiento_cientifico),
                getString(R.string.frase_descubrimiento_cientifico2)
            ),
            Pair(
                getString(R.string.descubrimiento_cientifico),
                getString(R.string.frase_descubrimiento_cientifico3)
            ),
            Pair(
                getString(R.string.definicion_concepto),
                getString(R.string.frase_definicion_concepto1)
            ),
            Pair(
                getString(R.string.definicion_concepto),
                getString(R.string.frase_definicion_concepto2)
            ),
            Pair(
                getString(R.string.definicion_concepto),
                getString(R.string.frase_definicion_concepto3)
            ),
            Pair(
                getString(R.string.panel_amor_desamor),
                getString(R.string.frase_panel_amor_desamor1)
            ),
            Pair(
                getString(R.string.panel_amor_desamor),
                getString(R.string.frase_panel_amor_desamor2)
            ),
            Pair(
                getString(R.string.panel_amor_desamor),
                getString(R.string.frase_panel_amor_desamor3)
            )
        )


        sectores2 = arrayOf(
            "200",
            getString(R.string.bote),
            "100",
            getString(R.string.pierde_turno),
            "75",
            "50",
            "25",
            getString(R.string.quiebra),
            "200",
            "250",
            "100",
            getString(R.string.pierde_turno),
            "75",
            "50",
            "25",
            getString(R.string.quiebra)
        )

        inicializacionPanel()
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

        btnOnClicks()
        actualizarJugadorActivo()
    }

    private fun inicializacionVaribles() {
        textoPista = findViewById(R.id.textoPista)
        panelLetras = findViewById(R.id.panelLetras)
        jugador1 = findViewById(R.id.saldoJugador1)
        jugador2 = findViewById(R.id.saldoJugador2)
        jugador3 = findViewById(R.id.saldoJugador3)
        ruleta = findViewById(R.id.imageView)
        contendorRuleta = findViewById(R.id.contenedorRuleta)
        contenedorAdivinarLetra = findViewById(R.id.contenedorAdivinarLetra)
        contendorBotones = findViewById(R.id.contendorBotones)
        contenedorResolver = findViewById(R.id.contenedorResolver)
        contenedorComprarLetra = findViewById(R.id.contenedorComprarLetra)
        letraEscogida = findViewById(R.id.letraEscogida)
        botonLetraEscogida = findViewById(R.id.botonLetraEscogida)
        valorSaldoJugador = findViewById(R.id.valorSaldoJugador)
        valorCeldaTirada = findViewById(R.id.valorCeldaTirada)
        btnOpcion1 = findViewById(R.id.btnOpcion1)
        btnOpcion2 = findViewById(R.id.btnOpcion2)
        btnOpcion3 = findViewById(R.id.btnOpcion3)
        btnComprobarPanel = findViewById(R.id.btnComprobarPanel)
        entradaFrase = findViewById(R.id.entradaFrase)
        btnComprobarLetraComprar = findViewById(R.id.botonComprobarLetraComprar)
        entradaLetraComprar = findViewById(R.id.entradaLetraComprar)
        valorSaldoComprar = findViewById(R.id.valorSaldoComprar)
        textoValorAcumulado = findViewById(R.id.textoValorAcumulado)
        nombreJugador1 = findViewById(R.id.nombreJugador1)
        avatarJugador1 = findViewById(R.id.avatarJugador1)
        nombreJugador2 = findViewById(R.id.nombreJugador2)
        avatarJugador2 = findViewById(R.id.avatarJugador2)
        nombreJugador3 = findViewById(R.id.nombreJugador3)
        avatarJugador3 = findViewById(R.id.avatarJugador3)
        linearJugador1 = findViewById(R.id.linearJugador1)
        linearJugador2 = findViewById(R.id.linearJugador2)
        linearJugador3 = findViewById(R.id.linearJugador3)
    }

    private fun inicializarJugadores() {
        val jugador1Nombre = intent.getStringExtra("jugador1Nombre") ?: "Jugador 1"
        jugador1Color = intent.getStringExtra("jugador1Color") ?: "#FFFFFF"
        jugador1Imagen = intent.getIntExtra("jugador1Imagen", R.drawable.mujer1)

        val jugador2Nombre = intent.getStringExtra("jugador2Nombre") ?: "Jugador 2"
        jugador2Color = intent.getStringExtra("jugador2Color") ?: "#FFFFFF"
        jugador2Imagen = intent.getIntExtra("jugador2Imagen", R.drawable.hombre1)

        val jugador3Nombre = intent.getStringExtra("jugador3Nombre") ?: "Jugador 3"
        jugador3Color = intent.getStringExtra("jugador3Color") ?: "#FFFFFF"
        jugador3Imagen = intent.getIntExtra("jugador3Imagen", R.drawable.mujer2)

        nombreJugador1.text = jugador1Nombre
        nombreJugador1.setBackgroundColor(Color.parseColor(jugador1Color))
        avatarJugador1.setImageResource(jugador1Imagen)

        nombreJugador2.text = jugador2Nombre
        nombreJugador2.setBackgroundColor(Color.parseColor(jugador2Color))
        avatarJugador2.setImageResource(jugador2Imagen)

        nombreJugador3.text = jugador3Nombre
        nombreJugador3.setBackgroundColor(Color.parseColor(jugador3Color))
        avatarJugador3.setImageResource(jugador3Imagen)
    }

    //Funcion para ocultar el teclado cuando se da a un boton
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun btnOnClicks() {
        botonLetraEscogida.setOnClickListener {
            val letra = letraEscogida.text.toString().trim()
            if (letra.length == 1 && letra.matches(Regex("[a-zA-Z]"))) {
                if (esConsonante(letra)) {
                    comprobarLetra(letra)
                    letraEscogida.text.clear()
                } else {
                    letraEscogida.error = getString(R.string.error_consonante)
                }
            } else {
                letraEscogida.error = getString(R.string.error_letra_valida)
            }
            hideKeyboard()
        }

        btnOpcion1.setOnClickListener {
            contendorBotones.visibility = View.GONE
            contenedorResolver.visibility = View.VISIBLE
        }

        btnOpcion2.setOnClickListener {
            val saldoJugadorActivo = when (jugadorActivo) {
                0 -> saldoJugador1
                1 -> saldoJugador2
                2 -> saldoJugador3
                else -> 0
            }

            if (saldoJugadorActivo <= 50) {
                AlertDialog.Builder(this)
                    .setTitle("Saldo insuficiente")
                    .setMessage("No puedes comprar porque tu saldo tiene que ser más de 50.")
                    .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else {
                contendorBotones.visibility = View.GONE
                contenedorComprarLetra.visibility = View.VISIBLE
                valorSaldoComprar.text = "${saldoJugadorActivo}€"
            }
        }

        btnOpcion3.setOnClickListener {
            contendorBotones.visibility = View.GONE
            contendorRuleta.visibility = View.VISIBLE
        }

        btnComprobarLetraComprar.setOnClickListener {
            val letra = entradaLetraComprar.text.toString().trim().uppercase()
            if (letra.length == 1 && letra.matches(Regex("[A-Z]"))) {
                if (esVocal(letra)) {
                    comprarLetra(letra)
                    entradaLetraComprar.text.clear()
                } else {
                    entradaLetraComprar.error = getString(R.string.error_vocal)
                }
            } else {
                entradaLetraComprar.error = getString(R.string.error_letra_valida)
            }
            entradaLetraComprar.setText("")
            hideKeyboard()
        }

        btnComprobarPanel.setOnClickListener {
            val fraseIntroducida = entradaFrase.text.toString().trim().uppercase()
            hideKeyboard()
            if (fraseIntroducida.isNotEmpty()) {
                comprobarFrase(fraseIntroducida)
            } else {
                entradaFrase.error = getString(R.string.error_frase_valida)
            }
            hideKeyboard()
        }

        if (todasLasVocalesDescubiertas()) {
            btnOpcion2.visibility = View.GONE
        } else {
            btnOpcion2.visibility = View.VISIBLE
        }

        if (todasLasLetrasDescubiertas()) {
            contendorBotones.visibility = View.GONE
            contenedorResolver.visibility = View.VISIBLE
        }
    }

    private fun inicializacionPanel() {
        val seleccion = frasesYPistas.random()
        frase = seleccion.second.uppercase()
        textoPista.text = getString(R.string.pista, seleccion.first)
        llenarPanelLetras(panelLetras, frase)
    }

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
            linea.forEachIndexed { index, caracter ->
                val vistaLetra = TextView(this).apply {
                    text = if (caracter == ' ') " " else "_"
                    tag = if (caracter != ' ') frase.indexOf(caracter, index) else null
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

    private fun comprobarLetra(letra: String) {
        val letraNormalizada = letra.uppercase()
        var contadorLetras = 0

        for (i in 0 until panelLetras.childCount) {
            val vista = panelLetras.getChildAt(i)
            if (vista is TextView) {
                val posicionEnFrase = vista.tag as? Int
                if (posicionEnFrase != null) {
                    val letraFrase = frase[posicionEnFrase].toString()
                    if (letraFrase == letraNormalizada && vista.text == "_") {
                        vista.text = letraFrase
                        contadorLetras++
                        Toast.makeText(
                            this,
                            getString(R.string.letra_encontrada, letraFrase),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        if (contadorLetras > 0) {
            Toast.makeText(
                this,
                getString(R.string.letra_aparece, letraNormalizada, contadorLetras),
                Toast.LENGTH_SHORT
            ).show()
            val valorCelda = valorCeldaTirada.text.toString().replace("€", "").toIntOrNull() ?: 0
            val incrementoSaldo = contadorLetras * valorCelda
            when (jugadorActivo) {
                0 -> saldoJugador1 += incrementoSaldo
                1 -> saldoJugador2 += incrementoSaldo
                2 -> saldoJugador3 += incrementoSaldo
            }
            saldoAcumulado += incrementoSaldo
            textoValorAcumulado.text = getString(R.string.valor_acumulado, saldoAcumulado)
            jugador1.text = "${saldoJugador1}€"
            jugador2.text = "${saldoJugador2}€"
            jugador3.text = "${saldoJugador3}€"

            contenedorAdivinarLetra.visibility = View.GONE
            contendorBotones.visibility = View.VISIBLE
            if (todasLasVocalesDescubiertas()) {
                btnOpcion2.visibility = View.GONE
            }
            if (todasLasLetrasDescubiertas()) {
                contendorBotones.visibility = View.GONE
                contenedorResolver.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(
                this,
                getString(R.string.letra_no_encontrada, letraNormalizada),
                Toast.LENGTH_SHORT
            ).show()
            siguienteJugador()
            contenedorAdivinarLetra.visibility = View.GONE
            contendorRuleta.visibility = View.VISIBLE
        }

        hideKeyboard()
    }

    private fun obtenerAngulo(x: Float, y: Float): Float {
        val centroX = ruleta.width / 2
        val centroY = ruleta.height / 2
        val deltaX = x - centroX
        val deltaY = y - centroY
        return (atan2(deltaY, deltaX) * (180 / Math.PI)).toFloat()
    }

    private fun girarRuleta() {
        val sectores = if (frasesResueltas < 2) {
            arrayOf(
                "200",
                "100",
                "250",
                getString(R.string.pierde_turno),
                "75",
                "50",
                "25",
                getString(R.string.quiebra),
                "200",
                "100",
                "250",
                getString(R.string.pierde_turno),
                "75",
                "50",
                "25",
                getString(R.string.quiebra)
            )
        } else {
            sectores2
        }
        val anguloPorSector = 360f / sectores.size
        val sectorElegido = Random.nextInt(sectores.size)
        val anguloCentroSector = sectorElegido * anguloPorSector + (anguloPorSector / 2)
        val girosCompletos = Random.nextInt(5, 10) * 360
        val nuevaRotacion = girosCompletos + anguloCentroSector

        rotacionFinal = nuevaRotacion % 360
        ruleta.animate()
            .rotation(nuevaRotacion)
            .setDuration(3000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                mostrarResultado(sectorElegido, sectores)
            }
            .start()
    }

    private fun mostrarResultado(sector: Int, sectores: Array<String>) {
        when (val resultado = sectores[sector]) {
            getString(R.string.pierde_turno) -> {
                Toast.makeText(
                    this,
                    getString(R.string.pierde_turno_mensaje, resultado),
                    Toast.LENGTH_SHORT
                ).show()
                siguienteJugador()
            }

            getString(R.string.quiebra) -> {
                saldoAcumulado = 0
                textoValorAcumulado.text = getString(R.string.valor_acumulado, saldoAcumulado)
                when (jugadorActivo) {
                    0 -> {
                        saldoJugador1 = 0
                        jugador1.text = "${saldoJugador1}€"
                    }

                    1 -> {
                        saldoJugador2 = 0
                        jugador2.text = "${saldoJugador2}€"
                    }

                    2 -> {
                        saldoJugador3 = 0
                        jugador3.text = "${saldoJugador3}€"
                    }
                }
                Toast.makeText(
                    this,
                    getString(R.string.quiebra_mensaje, resultado),
                    Toast.LENGTH_SHORT
                ).show()
                siguienteJugador()
            }

            getString(R.string.bote) -> {
                Toast.makeText(this, getString(R.string.bote_mensaje), Toast.LENGTH_SHORT).show()
                mostrarOpcionesBote()
            }

            else -> {
                val valor = resultado.replace("€", "").toIntOrNull() ?: 0
                Toast.makeText(
                    this,
                    getString(R.string.resultado_ruleta, resultado),
                    Toast.LENGTH_SHORT
                ).show()
                valorCeldaTirada.text = "$resultado€"
                when (jugadorActivo) {
                    0 -> valorSaldoJugador.text = jugador1.text
                    1 -> valorSaldoJugador.text = jugador2.text
                    2 -> valorSaldoJugador.text = jugador3.text
                }
                Handler(Looper.getMainLooper()).postDelayed(
                    { cambiarRuleta_AdivinarLetra(sectores[sector]) },
                    2000
                )
            }
        }
    }

    private fun mostrarOpcionesBote() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.bote))
        builder.setMessage(getString(R.string.bote_opciones))
        builder.setPositiveButton(getString(R.string.resolver)) { dialog, which ->
            contendorRuleta.visibility = View.GONE
            contenedorResolver.visibility = View.VISIBLE
        }
        builder.setNegativeButton(getString(R.string.volver_a_tirar)) { dialog, which ->
            saldoAcumulado = 0
            textoValorAcumulado.text = getString(R.string.valor_acumulado, saldoAcumulado)
            girarRuleta()
        }
        builder.show()
    }

    private fun siguienteJugador() {
        jugadorActivo = (jugadorActivo + 1) % 3
        actualizarJugadorActivo()
    }

    private fun actualizarJugadorActivo() {

        linearJugador1.setBackgroundResource(0)
        linearJugador2.setBackgroundResource(0)
        linearJugador3.setBackgroundResource(0)
        when (jugadorActivo) {
            0 -> linearJugador1.setBackgroundResource(R.drawable.borde)
            1 -> linearJugador2.setBackgroundResource(R.drawable.borde)
            2 -> linearJugador3.setBackgroundResource(R.drawable.borde)
        }
    }

    private fun cambiarRuleta_AdivinarLetra(valor: String) {
        contendorRuleta.visibility = View.GONE
        contenedorAdivinarLetra.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun comprarLetra(letra: String) {
        val saldoActual = when (jugadorActivo) {
            0 -> saldoJugador1
            1 -> saldoJugador2
            2 -> saldoJugador3
            else -> 0
        }

        if (saldoActual >= costoCompraLetra) {
            when (jugadorActivo) {
                0 -> saldoJugador1 -= costoCompraLetra
                1 -> saldoJugador2 -= costoCompraLetra
                2 -> saldoJugador3 -= costoCompraLetra
            }
            jugador1.text = "${saldoJugador1}€"
            jugador2.text = "${saldoJugador2}€"
            jugador3.text = "${saldoJugador3}€"
            valorSaldoComprar.text = "${
                when (jugadorActivo) {
                    0 -> saldoJugador1
                    1 -> saldoJugador2
                    2 -> saldoJugador3
                    else -> 0
                }
            }€"

            if (frase.contains(letra)) {
                for (i in 0 until panelLetras.childCount) {
                    val vista = panelLetras.getChildAt(i)
                    if (vista is TextView) {
                        val posicionEnFrase = vista.tag as? Int
                        if (posicionEnFrase != null && frase[posicionEnFrase].toString() == letra) {
                            vista.text = letra
                            entradaLetraComprar.setText("")
                        }
                    }
                }
                Toast.makeText(this, getString(R.string.letra_comprada, letra), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.letra_no_encontrada, letra),
                    Toast.LENGTH_SHORT
                ).show()
                siguienteJugador()
                contendorBotones.visibility = View.GONE
                contenedorComprarLetra.visibility = View.GONE
                contendorRuleta.visibility = View.VISIBLE
            }

            contenedorComprarLetra.visibility = View.GONE
            contendorBotones.visibility = View.VISIBLE

            if (todasLasVocalesDescubiertas()) {
                btnOpcion2.visibility = View.GONE
            }
            if (todasLasLetrasDescubiertas()) {
                contendorBotones.visibility = View.GONE
                contenedorResolver.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(this, getString(R.string.saldo_insuficiente), Toast.LENGTH_SHORT).show()
        }
    }

    private fun comprobarFrase(fraseIntroducida: String) {
        if (fraseIntroducida == frase) {
            frasesResueltas++

            if (frasesResueltas < 2) {
                Toast.makeText(this, getString(R.string.frase_correcta), Toast.LENGTH_SHORT).show()
                generarNuevaFrase()
                siguienteJugador()
                llenarPanelLetras(panelLetras, frase)
                contenedorResolver.visibility = View.GONE
                contendorRuleta.visibility = View.VISIBLE
                if (ruleta.drawable.constantState == resources.getDrawable(R.drawable.ruletabote).constantState) {
                    when (jugadorActivo) {
                        0 -> saldoJugador1 += saldoAcumulado
                        1 -> saldoJugador2 += saldoAcumulado
                        2 -> saldoJugador3 += saldoAcumulado
                    }
                    saldoAcumulado = 0
                    textoValorAcumulado.text = getString(R.string.valor_acumulado, saldoAcumulado)
                    actualizarSaldosEnPantalla()
                }
            } else if (frasesResueltas == 2) {
                ruleta.setImageResource(R.drawable.ruletabote)
                Toast.makeText(this, getString(R.string.frase_correcta_bote), Toast.LENGTH_SHORT)
                    .show()
                generarNuevaFrase()
                llenarPanelLetras(panelLetras, frase)
                contenedorResolver.visibility = View.GONE
                contendorRuleta.visibility = View.VISIBLE
                textoValorAcumulado.visibility = View.VISIBLE
                textoValorAcumulado.text = getString(R.string.valor_acumulado, 0)

            } else {
                mostrarPanelFinal()
            }
        } else {
            Toast.makeText(this, getString(R.string.frase_incorrecta), Toast.LENGTH_SHORT).show()
            siguienteJugador()
            contenedorResolver.visibility = View.GONE
            contendorRuleta.visibility = View.VISIBLE
        }
        entradaFrase.text.clear()

        if (todasLasLetrasDescubiertas()) {
            contendorBotones.visibility = View.GONE
            contenedorResolver.visibility = View.VISIBLE
        }
    }

    private fun mostrarPanelFinal() {
        val mensaje = """
        ${getString(R.string.juego_terminado)}
        ${nombreJugador1.text}: $saldoJugador1
        ${nombreJugador2.text}: $saldoJugador2
        ${nombreJugador3.text}: $saldoJugador3
    """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.fin_del_juego))
            .setMessage(mensaje)
            .setPositiveButton(getString(R.string.aceptar)) { dialog, which ->
                finish()
            }
            .setCancelable(false)
            .show()

        Handler(Looper.getMainLooper()).postDelayed({
            val newIntent = Intent(this, FinalGameActivity::class.java)
            newIntent.putExtra("jugador1Nombre", nombreJugador1.text)
            newIntent.putExtra("saldo1Jugador", saldoJugador1)
            newIntent.putExtra("jugador1Color", jugador1Color)
            newIntent.putExtra("jugador1Imagen", jugador1Imagen)

            newIntent.putExtra("jugador2Nombre", nombreJugador2.text)
            newIntent.putExtra("saldo2Jugador", saldoJugador2)
            newIntent.putExtra("jugador2Color", jugador2Color)
            newIntent.putExtra("jugador2Imagen", jugador2Imagen)

            newIntent.putExtra("jugador3Nombre", nombreJugador3.text)
            newIntent.putExtra("saldo3Jugador", saldoJugador3)
            newIntent.putExtra("jugador3Color", jugador3Color)
            newIntent.putExtra("jugador3Imagen", jugador3Imagen)

            startActivity(newIntent)
        }, 5000)
    }


    private fun actualizarSaldosEnPantalla() {
        jugador1.text = "${saldoJugador1}€"
        jugador2.text = "${saldoJugador2}€"
        jugador3.text = "${saldoJugador3}€"
    }

    private fun generarNuevaFrase() {
        val seleccion = frasesYPistas.random()
        frase = seleccion.second.uppercase()
        textoPista.text = getString(R.string.pista, seleccion.first)
    }

    private fun esConsonante(letra: String): Boolean {
        val consonantes = "BCDFGHJKLMNÑPQRSTVWXYZ"
        return letra.uppercase() in consonantes
    }

    private fun esVocal(letra: String): Boolean {
        val vocales = "AEIOU"
        return letra.uppercase() in vocales
    }

    private fun todasLasVocalesDescubiertas(): Boolean {
        val vocales = "AEIOU"
        for (i in frase.indices) {
            if (frase[i].toString() in vocales) {
                val vista = panelLetras.getChildAt(i) as? TextView
                if (vista?.text == "_") {
                    return false
                }
            }
        }
        return true
    }

    private fun todasLasLetrasDescubiertas(): Boolean {
        for (i in 0 until panelLetras.childCount) {
            val vista = panelLetras.getChildAt(i) as? TextView
            if (vista?.text == "_") {
                return false
            }
        }
        return true
    }
}