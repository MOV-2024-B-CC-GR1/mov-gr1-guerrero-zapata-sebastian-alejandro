package com.example.deber_01

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class Carreras : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dao: CarreraDAO
    private var selectedItemIndex: Int? = null
    private var listaCarreras = mutableListOf<ModeloCarrera>() // Cambio aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carreras)

        dao = CarreraDAO(this)

        val listViewCarreras = findViewById<ListView>(R.id.Ls_Carreras)
        val btnEditarCarrera = findViewById<Button>(R.id.Btn_EditarCarrera)
        val btnEliminarCarrera = findViewById<Button>(R.id.Btn_EliminarCarrera)
        val btnListadoCarreras = findViewById<Button>(R.id.Btn_RegresarFacultades)
        val btnAgregarCarrera = findViewById<Button>(R.id.Btn_AgregarCarrera)

        // Cargar las carreras desde la base de datos
        cargarCarreras()

        // Configurar ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getFormattedCarreras())
        listViewCarreras.adapter = adapter

        listViewCarreras.setOnItemClickListener { _, _, position, _ ->
            selectedItemIndex = position
            Toast.makeText(this, "Seleccionado: ${listaCarreras[position].nombre}", Toast.LENGTH_SHORT).show()
        }

        // Editar Carrera
        val editCarrerasLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                cargarCarreras()
                Toast.makeText(this, "Carrera actualizada", Toast.LENGTH_SHORT).show()
            }
        }

        btnEditarCarrera.setOnClickListener {
            selectedItemIndex?.let {
                val carreraSeleccionada = listaCarreras[it]
                val intent = Intent(this, EditarCarreras::class.java).apply {
                    putExtra("id", carreraSeleccionada.id)
                    putExtra("nombre", carreraSeleccionada.nombre)
                    putExtra("duracion", carreraSeleccionada.duracion)
                    putExtra("materias", carreraSeleccionada.materias.toTypedArray())
                    putExtra("idFacultad", carreraSeleccionada.idFacultad)
                }
                editCarrerasLauncher.launch(intent)
            } ?: Toast.makeText(this, "Selecciona una carrera para editar", Toast.LENGTH_SHORT).show()
        }

        // Eliminar Carrera
        btnEliminarCarrera.setOnClickListener {
            selectedItemIndex?.let {
                val carreraEliminada = listaCarreras[it]
                dao.eliminarCarrera(carreraEliminada.id)
                cargarCarreras()
                Toast.makeText(this, "Carrera eliminada: ${carreraEliminada.nombre}", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(this, "Selecciona una carrera para eliminar", Toast.LENGTH_SHORT).show()
        }

        // Agregar Carrera
        val agregarCarreraLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                cargarCarreras()
                Toast.makeText(this, "Carrera agregada", Toast.LENGTH_SHORT).show()
            }
        }

        btnAgregarCarrera.setOnClickListener {
            val intent = Intent(this, AgregarCarrera::class.java)
            agregarCarreraLauncher.launch(intent)
        }

        btnListadoCarreras.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Obtener carreras de SQLite
    private fun cargarCarreras() {
        listaCarreras = dao.obtenerCarreras().toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getFormattedCarreras())
        findViewById<ListView>(R.id.Ls_Carreras).adapter = adapter
    }

    // Formato de texto para mostrar en el ListView
    private fun getFormattedCarreras(): List<String> {
        return listaCarreras.map { carrera ->
            """
            Carrera: ${carrera.nombre}
            Duración: ${carrera.duracion} años
            Materias: ${carrera.materias.joinToString(", ")}
            """.trimIndent()
        }
    }
}
