package com.example.deber_01

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class AgregarCarrera : AppCompatActivity() {
    private lateinit var daoFacultad: FacultadDAO
    private lateinit var facultades: List<Facultad>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar_carrera)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar DAO
        daoFacultad = FacultadDAO(this)

        // Obtener referencias de UI
        val inputNombre = findViewById<TextInputEditText>(R.id.Input_AgregarNuevaNombreCarrera)
        val inputDuracion = findViewById<EditText>(R.id.Input_AgregarNuevaDuracionCarrera)
        val inputMaterias = findViewById<TextInputEditText>(R.id.Input_AgregarNuevaMateriasCarrera)
        val listViewFacultades = findViewById<ListView>(R.id.Ls_ListadoFacultadesAgregarCarrera)
        val btnConfirmar = findViewById<Button>(R.id.Btn_ConfirmarAgregarCarrera)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarAgregarCarrera)

        // **Obtener facultades desde SQLite**
        facultades = daoFacultad.obtenerFacultades()

        // Configurar el adaptador del ListView con datos reales
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            facultades.map { "ID: ${it.id} - ${it.nombre}" }
        )

        listViewFacultades.adapter = adapter
        listViewFacultades.choiceMode = ListView.CHOICE_MODE_SINGLE

        // **Configurar botón Confirmar**
        btnConfirmar.setOnClickListener {
            val nombre = inputNombre.text?.toString()?.trim() ?: ""
            val duracion = inputDuracion.text.toString().toDoubleOrNull() ?: -1.0
            val materias = inputMaterias.text?.toString()?.split(",")?.map { it.trim() } ?: emptyList()
            val facultadSeleccionada = facultades.getOrNull(listViewFacultades.checkedItemPosition)

            // **Validación**
            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (duracion <= 0) {
                Toast.makeText(this, "La duración debe ser un número positivo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (materias.isEmpty() || materias.any { it.isEmpty() }) {
                Toast.makeText(this, "Debe ingresar al menos una materia", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (facultadSeleccionada == null) {
                Toast.makeText(this, "Seleccione una facultad válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // **Crear y guardar la nueva carrera**
            val daoCarrera = CarreraDAO(this)
            val nuevaCarrera = ModeloCarrera(
                id = daoCarrera.obtenerUltimoIdCarrera() + 1,  // Generar nuevo ID
                nombre = nombre,
                duracion = duracion,
                materias = materias,
                idFacultad = facultadSeleccionada.id
            )

            daoCarrera.insertarCarrera(nuevaCarrera)

            // **Mensaje de éxito y cierre**
            Toast.makeText(this, "Carrera agregada exitosamente", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }

        // **Configurar botón Cancelar**
        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
