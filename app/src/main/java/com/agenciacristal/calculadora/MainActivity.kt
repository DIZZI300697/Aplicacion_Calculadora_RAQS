package com.agenciacristal.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private val SUMA = "+"
    private val RESTA = "-"
    private val MULTIPLICACION = "*"
    private val DIVISION = "/"
    private val PORCENTAJE = "%"

    private var operacionActual = ""

    private var primerNumero: Double = Double.NaN
    private var segundoNumero: Double = Double.NaN

    private lateinit var tvTemp: TextView
    private lateinit var tvResult: TextView

    private val formatoDecimal = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTemp = findViewById(R.id.tvTemp)
        tvResult = findViewById(R.id.tvResult)
    }

    fun cambiarOperador(b: View) {
        if (tvTemp.text.isNotEmpty() || !primerNumero.isNaN()) {
            calcular()
            val boton: Button = b as Button
            operacionActual = when (boton.text.toString().trim()) {
                "÷" -> DIVISION
                "X" -> MULTIPLICACION
                else -> boton.text.toString().trim()
            }
            if (tvTemp.text.toString().isEmpty()) {
                tvTemp.text = formatoDecimal.format(primerNumero)
            }
            tvTemp.text = "${tvTemp.text}$operacionActual"
            tvResult.text = ""
        }
    }

    private fun calcular() {
        try {
            if (!primerNumero.isNaN()) {
                if (tvTemp.text.toString().isEmpty()) {
                    tvTemp.text = formatoDecimal.format(primerNumero)
                }
                segundoNumero = tvTemp.text.toString().substringAfterLast(operacionActual).toDouble()

                when (operacionActual) {
                    SUMA -> primerNumero += segundoNumero
                    RESTA -> primerNumero -= segundoNumero
                    MULTIPLICACION -> primerNumero *= segundoNumero
                    DIVISION -> primerNumero /= segundoNumero
                    PORCENTAJE -> primerNumero %= segundoNumero
                }
            } else {
                primerNumero = tvTemp.text.toString().toDouble()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun seleccionarNumero(b: View) {
        val boton: Button = b as Button
        tvTemp.text = "${tvTemp.text}${boton.text}"
    }

    fun igual(b: View) {
        calcular()
        tvResult.text = formatoDecimal.format(primerNumero)
        operacionActual = ""
    }

    fun borrar(b: View) {
        val boton: Button = b as Button
        if (boton.text.toString().trim() == "C") {
            if (tvTemp.text.toString().isNotEmpty()) {
                var datosActuales: CharSequence = tvTemp.text
                tvTemp.text = datosActuales.subSequence(0, datosActuales.length - 1)
            } else {
                primerNumero = Double.NaN
                segundoNumero = Double.NaN
                tvTemp.text = ""
                tvResult.text = ""
            }
        } else if (boton.text.toString().trim() == "CA") {
            primerNumero = Double.NaN
            segundoNumero = Double.NaN
            tvTemp.text = ""
            tvResult.text = ""
        }
    }
}
