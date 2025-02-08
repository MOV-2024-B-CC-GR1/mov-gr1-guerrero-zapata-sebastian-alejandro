package com.example.deber_01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class AgregarFacultad : AppCompatActivity() {
    private lateinit var dao: FacultadDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_facultad)

        dao = FacultadDAO(this)

        val inputNombre = findViewById<EditText>(R.id.Input_NombreFacultad_EditText)
        val inputPresupuesto = findViewById<EditText>(R.id.Input_PresupuestoFacultad)
        val switchActiva = findViewById<Switch>(R.id.switch1)
        val btnAgregar = findViewById<Button>(R.id.Btn_AgregarFacultad)
        val btnCancelar = findViewById<Button>(R.id.Btn_CancelarAgregar)

        btnAgregar.setOnClickListener {
            val nombre = inputNombre.text.toString().trim()
            val presupuesto = inputPresupuesto.text.toString().toDoubleOrNull() ?: 0.0
            val activa = switchActiva.isChecked

            if (nombre.isNotEmpty()) {
                // Insertar en la base de datos
                val id = dao.insertarFacultad(nombre, presupuesto, activa)

                if (id != -1L) {
                    Toast.makeText(this, "Facultad agregada exitosamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error al agregar facultad", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
