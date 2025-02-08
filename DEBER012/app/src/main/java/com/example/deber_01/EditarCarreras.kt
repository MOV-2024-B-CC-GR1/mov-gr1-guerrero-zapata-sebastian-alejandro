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

class EditarCarreras : AppCompatActivity() {
    private lateinit var dao: CarreraDAO
    private lateinit var listaCarreras: MutableList<ModeloCarrera>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_carreras)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Cl_ListadoCarreras)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dao = CarreraDAO(this)
        listaCarreras = dao.obtenerCarreras().toMutableList()

        // Referencias a los elementos de la vista
        val inputNombre = findViewById<EditText>(R.id.TxtInput_EdicionNombreCarrera)
        val inputDuracion = findViewById<EditText>(R.id.Inp_EditarAniosCarrera)
        val inputMaterias = findViewById<EditText>(R.id.Inp_EditarMateriasCarrera)
        val listViewFacultades = findViewById<ListView>(R.id.Ls_Carreras)
        val btnConfirmar = findViewById<Button>(R.id.Btn_CfmEdicionCarrera)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarEdicionCarrera)

        // Obtener los datos enviados desde Carreras.kt
        val idCarrera = intent.getIntExtra("id", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val duracion = intent.getDoubleExtra("duracion", 0.0)
        val materias = intent.getStringArrayExtra("materias")?.toList() ?: emptyList()
        val idFacultad = intent.getIntExtra("idFacultad", -1)

        // Mostrar datos en los campos
        inputNombre.setText(nombre)
        inputDuracion.setText(duracion.toString())
        inputMaterias.setText(materias.joinToString(", "))

        // Mostrar facultades en el ListView
        val facultades = BaseDatosFacultad.arregloFacultad
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice, // Selección única
            facultades.map { "${it.id}: ${it.nombre}" }
        )
        listViewFacultades.adapter = adapter
        listViewFacultades.choiceMode = ListView.CHOICE_MODE_SINGLE

        // Seleccionar la facultad actual en el ListView
        val facultadActualIndex = facultades.indexOfFirst { it.id == idFacultad }
        if (facultadActualIndex != -1) {
            listViewFacultades.setItemChecked(facultadActualIndex, true)
        }

        // Configurar el botón Confirmar
        btnConfirmar.setOnClickListener {
            val nuevoNombre = inputNombre.text.toString()
            val nuevaDuracion = inputDuracion.text.toString().toDoubleOrNull() ?: 0.0
            val nuevasMaterias = inputMaterias.text.toString().split(",").map { it.trim() }

            // Obtener la nueva facultad seleccionada
            val checkedPosition = listViewFacultades.checkedItemPosition
            val nuevaFacultadId = if (checkedPosition != -1) {
                facultades[checkedPosition].id
            } else {
                idFacultad // Mantener la facultad actual si no hay selección
            }

            if (idCarrera != -1) {
                // Obtener la carrera existente
                val carreraExistente = listaCarreras.find { it.id == idCarrera }
                if (carreraExistente != null) {
                    val carreraEditada = ModeloCarrera(
                        id = idCarrera,
                        nombre = nuevoNombre,
                        duracion = nuevaDuracion,
                        materias = nuevasMaterias,
                        idFacultad = nuevaFacultadId
                    )

                    // Actualizar en la base de datos
                    dao.actualizarCarrera(carreraEditada)

                    setResult(RESULT_OK) // Indicar que se actualizó correctamente
                    Toast.makeText(this, "Carrera actualizada correctamente", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error: No se encontró la carrera", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error al actualizar la carrera", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar botón Cancelar
        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED) // Cancelar la edición
            finish()
        }
    }
}
