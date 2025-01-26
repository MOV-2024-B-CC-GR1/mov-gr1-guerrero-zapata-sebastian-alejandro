package com.example.deber_01

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class Carreras : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedItemIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carreras)

        val listViewCarreras = findViewById<ListView>(R.id.Ls_Carreras)
        val btnEditarCarrera = findViewById<Button>(R.id.Btn_EditarCarrera)
        val btnEliminarCarrera = findViewById<Button>(R.id.Btn_EliminarCarrera)
        val btnListadoCarreras = findViewById<Button>(R.id.Btn_RegresarFacultades)
        val btnAgregarCarrera = findViewById<Button>(R.id.Btn_AgregarCarrera)
        val carreras = BaseDatosCarrera.arregloCarrera
        val facultades = BaseDatosFacultad.arregloFacultad

        // Función para formatear los datos del ListView
        fun getFormattedCarreras(): List<String> {
            return carreras.map { carrera ->
                val facultad = facultades.find { it.id == carrera.idFacultad }
                """
        Carrera: ${carrera.nombre}
        Duración: ${carrera.duracion} años
        Materias: ${carrera.materias.joinToString(", ")}
        Facultad: ${facultad?.nombre ?: "Desconocida"}
        """.trimIndent()
            }
        }


        // Crear adaptador para mostrar las carreras
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            getFormattedCarreras()
        )
        listViewCarreras.adapter = adapter

        // Configurar selección en el ListView
        listViewCarreras.setOnItemClickListener { _, _, position, _ ->
            selectedItemIndex = position
            val carreraSeleccionada = carreras[position]
            Toast.makeText(
                this,
                "Seleccionado: ${carreraSeleccionada.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Navegar a EditarCarreras
        val editCarrerasLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Actualizar el adaptador con los datos modificados
                adapter.clear()
                adapter.addAll(getFormattedCarreras())
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Carrera actualizada", Toast.LENGTH_SHORT).show()
            }
        }

        btnEditarCarrera.setOnClickListener {
            if (selectedItemIndex != null) {
                val carreraSeleccionada = carreras[selectedItemIndex!!]
                val intent = Intent(this, EditarCarreras::class.java)
                intent.putExtra("index", selectedItemIndex!!)
                intent.putExtra("nombre", carreraSeleccionada.nombre)
                intent.putExtra("duracion", carreraSeleccionada.duracion)
                intent.putExtra("materias", carreraSeleccionada.materias.toTypedArray())
                intent.putExtra("idFacultad", carreraSeleccionada.idFacultad)
                editCarrerasLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Selecciona una carrera para editar", Toast.LENGTH_SHORT).show()
            }
        }
        btnEliminarCarrera.setOnClickListener {
            if (selectedItemIndex != null) {
                val carreraEliminada = carreras.removeAt(selectedItemIndex!!)
                adapter.clear()
                adapter.addAll(getFormattedCarreras())
                adapter.notifyDataSetChanged()
                selectedItemIndex = null
                Toast.makeText(
                    this,
                    "Carrera eliminada: ${carreraEliminada.nombre}",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "Selecciona una carrera para eliminar", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val agregarCarreraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                adapter.clear()
                adapter.addAll(getFormattedCarreras())
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Carrera agregada", Toast.LENGTH_SHORT).show()
            }
        }

        btnAgregarCarrera.setOnClickListener {
            val intent = Intent(this, AgregarCarrera::class.java)
            agregarCarreraLauncher.launch(intent)
        }

        btnListadoCarreras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            agregarCarreraLauncher.launch(intent)
        }
    }
}
