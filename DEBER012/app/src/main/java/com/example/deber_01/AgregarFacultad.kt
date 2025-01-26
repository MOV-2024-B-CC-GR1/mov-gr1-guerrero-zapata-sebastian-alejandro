package com.example.deber_01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class AgregarFacultad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_facultad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_AgrefarFacultad)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputNombre = findViewById<EditText>(R.id.Input_NombreFacultad_EditText)
        val inputPresupuesto = findViewById<EditText>(R.id.Input_PresupuestoFacultad)
        val switchActiva = findViewById<Switch>(R.id.switch1)
        val btnAgregar = findViewById<Button>(R.id.Btn_AgregarFacultad)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarAgregar)
        btnAgregar.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val presupuesto = inputPresupuesto.text.toString().toDoubleOrNull() ?: 0.0
            val activa = switchActiva.isChecked

            if (nombre.isNotEmpty()) {
                // Crear nueva facultad
                val nuevaFacultad = Facultad(
                    id = BaseDatosFacultad.arregloFacultad.size + 1,
                    nombre = nombre,
                    presupuesto = presupuesto,
                    activa = activa
                )

                // Agregar facultad a la base de datos simulada
                BaseDatosFacultad.arregloFacultad.add(nuevaFacultad)

                // Enviar resultado a MainActivity
                setResult(RESULT_OK)
                finish()
            }
        }

        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}