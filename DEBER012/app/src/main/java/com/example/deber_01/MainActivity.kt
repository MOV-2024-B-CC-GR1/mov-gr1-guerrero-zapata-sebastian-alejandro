package com.example.deber_01

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast // Importación de Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ArrayAdapter<Facultad>
    private var selectedItemIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewFacultades = findViewById<ListView>(R.id.Ls_Facultades)
        //Botones
        val btnEliminar = findViewById<Button>(R.id.Btn_EliminarFacultad)
        val btnEditar = findViewById<Button>(R.id.Btn_EditarFacultad)
        val btnAgregar = findViewById<Button>(R.id.Btn_AgregarFacultad)
        val btnListadoCarreras = findViewById<Button>(R.id.Btn_ListadoCarreras)
        //

        val facultades = BaseDatosFacultad.arregloFacultad

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            facultades
        )
        listViewFacultades.adapter = adapter

        // Configurar clic en los elementos del ListView
        listViewFacultades.setOnItemClickListener { _, _, position, _ ->
            selectedItemIndex = position
            val facultadSeleccionada = facultades[position]
            Toast.makeText(
                this,
                "Seleccionado: ${facultadSeleccionada.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Configurar acción del botón Eliminar
        btnEliminar.setOnClickListener {
            if (selectedItemIndex != null) {
                val index = selectedItemIndex!!
                facultades.removeAt(index)
                adapter.notifyDataSetChanged()
                selectedItemIndex = null
                Toast.makeText(this, "Facultad eliminada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Selecciona una facultad para eliminar", Toast.LENGTH_SHORT).show()
            }
        }
        // Configurar acción del botón Editar
        btnEditar.setOnClickListener {
            if (selectedItemIndex != null) {
                val index = selectedItemIndex!!
                val facultadSeleccionada = facultades[index]

                // Crear intent para cambiar a EditarFacultad
                val intent = Intent(this, EditarFacultad::class.java)
                intent.putExtra("index", index) // Pasar índice
                intent.putExtra("nombre", facultadSeleccionada.nombre)
                intent.putExtra("presupuesto", facultadSeleccionada.presupuesto)
                intent.putExtra("activa", facultadSeleccionada.activa)
                editFacultadLauncher.launch(intent)
            } else {
                Toast.makeText(this, "Selecciona una facultad para editar", Toast.LENGTH_SHORT)
                    .show()
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
    // actualizar datos al volver de Editar
    private val editFacultadLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Actualizar la lista con los cambios
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Facultad actualizada", Toast.LENGTH_SHORT).show()
            }
        }

    // Registrar el ActivityResultLauncher para agregar facultades
    private val agregarFacultadLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Actualizar la lista con los cambios
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Facultad agregada", Toast.LENGTH_SHORT).show()
            }
        }

}
