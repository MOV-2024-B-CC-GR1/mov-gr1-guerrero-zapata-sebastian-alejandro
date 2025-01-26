package com.example.deber_01

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class AgregarCarrera : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_carrera)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos de la vista
        val inputNombre = findViewById<TextInputEditText>(R.id.Input_AgregarNuevaNombreCarrera)
        val inputDuracion = findViewById<EditText>(R.id.Input_AgregarNuevaDuracionCarrera)
        val inputMaterias = findViewById<TextInputEditText>(R.id.Input_AgregarNuevaMateriasCarrera)
        val listViewFacultades = findViewById<ListView>(R.id.Ls_ListadoFacultadesAgregarCarrera)
        val btnConfirmar = findViewById<Button>(R.id.Btn_ConfirmarAgregarCarrera)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarAgregarCarrera)

        // Obtener las facultades para mostrarlas en el ListView
        val facultades = BaseDatosFacultad.arregloFacultad
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            facultades.map { "${it.id}: ${it.nombre}" }
        )
        listViewFacultades.adapter = adapter
        listViewFacultades.choiceMode = ListView.CHOICE_MODE_SINGLE

        // Configurar el botón Confirmar
        btnConfirmar.setOnClickListener {
            val nombre = inputNombre?.text.toString()
            val duracion = inputDuracion.text.toString().toDoubleOrNull() ?: 0.0
            val materias = inputMaterias?.text.toString().split(",").map { it.trim() }
            val facultadSeleccionada = facultades.getOrNull(listViewFacultades.checkedItemPosition)

            // Validar que los campos no estén vacíos
            if (nombre.isNotEmpty() && duracion > 0 && materias.isNotEmpty() && facultadSeleccionada != null) {
                // Crear la nueva carrera
                val nuevaCarrera = ModeloCarrera(
                    id = BaseDatosCarrera.arregloCarrera.size + 1,
                    nombre = nombre,
                    duracion = duracion,
                    materias = materias,
                    idFacultad = facultadSeleccionada.id
                )

                // Agregar la nueva carrera a la base de datos
                BaseDatosCarrera.arregloCarrera.add(nuevaCarrera)

                // Mostrar mensaje de éxito y regresar a la actividad anterior
                Toast.makeText(this, "Carrera agregada exitosamente", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Por favor, completa todos los campos correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Configurar el botón Cancelar
        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}