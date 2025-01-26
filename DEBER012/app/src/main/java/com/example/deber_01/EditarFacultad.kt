package com.example.deber_01

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class EditarFacultad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_facultad)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_EditarFacultad)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener vistas
        val inputNombre = findViewById<TextInputEditText>(R.id.Input_NombreFacultad)
        val inputPresupuesto = findViewById<TextInputEditText>(R.id.Input_PresupuestoFacultad)
        val switchActiva = findViewById<Switch>(R.id.Swt_ActivaFacultad)
        val btnConfirmar = findViewById<Button>(R.id.Btn_ConfirmarEdicion)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarEdicion)

        // Obtener datos del intent
        val index = intent.getIntExtra("index", -1)
        val nombre = intent.getStringExtra("nombre")
        val presupuesto = intent.getDoubleExtra("presupuesto", 0.0)
        val activa = intent.getBooleanExtra("activa", false)

        // Mostrar datos en los campos
        inputNombre.setText(nombre)
        inputPresupuesto.setText(presupuesto.toString())
        switchActiva.isChecked = activa

        // Confirmar edición
        btnConfirmar.setOnClickListener {
            // Actualizar datos en la lista
            val facultadEditada = BaseDatosFacultad.arregloFacultad[index]
            facultadEditada.nombre = inputNombre.text.toString()
            facultadEditada.presupuesto = inputPresupuesto.text.toString().toDouble()
            facultadEditada.activa = switchActiva.isChecked
            setResult(RESULT_OK)
            // Finalizar actividad
            finish()
        }

        // Cancelar edición
        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish() // Volver a la actividad principal sin cambios
        }
    }
}