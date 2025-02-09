package com.example.deber_01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast

class EditarFacultad : AppCompatActivity() {
    private lateinit var dao: FacultadDAO
    private var facultadId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_facultad)

        dao = FacultadDAO(this)

        // Obtener vistas
        val inputNombre = findViewById<TextInputEditText>(R.id.Input_NombreFacultad)
        val inputPresupuesto = findViewById<TextInputEditText>(R.id.Input_PresupuestoFacultad)
        val inputLatitud = findViewById<EditText>(R.id.Input_LatitudFacultad)  // Agregado
        val inputLongitud = findViewById<EditText>(R.id.Input_LongitudFacultad)
        val switchActiva = findViewById<Switch>(R.id.Swt_ActivaFacultad)
        val btnConfirmar = findViewById<Button>(R.id.Btn_ConfirmarEdicion)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarEdicion)

        // Obtener datos del intent
        facultadId = intent.getIntExtra("id", -1)
        val nombre = intent.getStringExtra("nombre") ?: ""
        val presupuesto = intent.getDoubleExtra("presupuesto", 0.0)
        val activa = intent.getBooleanExtra("activa", false)
        val latitud = intent.getDoubleExtra("latitud", 0.0)  // Agregado
        val longitud = intent.getDoubleExtra("longitud", 0.0)

        // Mostrar datos en los campos
        inputNombre.setText(nombre)
        inputPresupuesto.setText(presupuesto.toString())
        inputLatitud.setText(latitud.toString())  // Agregado
        inputLongitud.setText(longitud.toString())
        switchActiva.isChecked = activa

        // Confirmar edición
        btnConfirmar.setOnClickListener {
            val nuevoNombre = inputNombre.text.toString()
            val nuevoPresupuesto = inputPresupuesto.text.toString().toDoubleOrNull() ?: 0.0
            val nuevaLatitud = inputLatitud.text.toString().toDoubleOrNull() ?: 0.0 // Agregado
            val nuevaLongitud = inputLongitud.text.toString().toDoubleOrNull() ?: 0.0
            val nuevaActiva = switchActiva.isChecked

            if (facultadId != -1) {
                dao.actualizarFacultad(facultadId, nuevoNombre, nuevoPresupuesto, nuevaActiva, nuevaLatitud, nuevaLongitud)
                Toast.makeText(this, "Facultad actualizada", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        }

        // Cancelar edición
        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
