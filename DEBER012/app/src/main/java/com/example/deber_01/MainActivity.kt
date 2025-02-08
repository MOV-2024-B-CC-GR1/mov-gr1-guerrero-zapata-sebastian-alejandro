package com.example.deber_01

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dao: FacultadDAO
    private var selectedItemIndex: Int? = null
    private var facultades: MutableList<Facultad> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = FacultadDAO(this)

        val listViewFacultades = findViewById<ListView>(R.id.Ls_Facultades)
        val btnEliminar = findViewById<Button>(R.id.Btn_EliminarFacultad)
        val btnEditar = findViewById<Button>(R.id.Btn_EditarFacultad)
        val btnAgregar = findViewById<Button>(R.id.Btn_AgregarFacultad)
        val btnListadoCarreras = findViewById<Button>(R.id.Btn_ListadoCarreras)

        // Cargar facultades desde SQLite
        cargarFacultades()

        // Configurar clic en los elementos del ListView
        listViewFacultades.setOnItemClickListener { _, _, position, _ ->
            selectedItemIndex = position
            val facultadSeleccionada = facultades[position]
            Toast.makeText(this, "Seleccionado: ${facultadSeleccionada.nombre}", Toast.LENGTH_SHORT).show()
        }

        // Configurar acción del botón Eliminar
        btnEliminar.setOnClickListener {
            if (selectedItemIndex != null) {
                val facultadEliminada = facultades[selectedItemIndex!!]
                dao.eliminarFacultad(facultadEliminada.id)
                cargarFacultades() // Recargar lista después de eliminar
                selectedItemIndex = null
                Toast.makeText(this, "Facultad eliminada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Selecciona una facultad para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar acción del botón Editar
        btnEditar.setOnClickListener {
            if (selectedItemIndex != null) {
                val facultadSeleccionada = facultades[selectedItemIndex!!]
                val intent = Intent(this, EditarFacultad::class.java).apply {
                    putExtra("id", facultadSeleccionada.id)
                    putExtra("nombre", facultadSeleccionada.nombre)
                    putExtra("presupuesto", facultadSeleccionada.presupuesto)
                    putExtra("activa", facultadSeleccionada.activa)
                }
                editFacultadLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Selecciona una facultad para editar", Toast.LENGTH_SHORT).show()
            }
        }

        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarFacultad::class.java)
            agregarFacultadLauncher.launch(intent)
        }

        // Navegar a la actividad Carreras
        btnListadoCarreras.setOnClickListener {
            val intent = Intent(this, Carreras::class.java)
            startActivity(intent)
        }
    }

    private val editFacultadLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                cargarFacultades() // Recargar datos después de editar
                Toast.makeText(this, "Facultad actualizada", Toast.LENGTH_SHORT).show()
            }
        }

    private val agregarFacultadLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                cargarFacultades() // Recargar datos después de agregar
                Toast.makeText(this, "Facultad agregada", Toast.LENGTH_SHORT).show()
            }
        }

    private fun cargarFacultades() {
        facultades = dao.obtenerFacultades().toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, facultades.map {
            "ID: ${it.id}\nNombre: ${it.nombre}\nPresupuesto: ${it.presupuesto}\nActiva: ${if (it.activa) "Sí" else "No"}"
        })
        findViewById<ListView>(R.id.Ls_Facultades).adapter = adapter
    }
}
