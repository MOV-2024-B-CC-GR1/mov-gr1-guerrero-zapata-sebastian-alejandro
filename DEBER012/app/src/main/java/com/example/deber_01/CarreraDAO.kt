package com.example.deber_01

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import android.database.sqlite.SQLiteDatabase


class CarreraDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)
    fun obtenerUltimoIdCarrera(): Int {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT MAX(id) FROM Carrera", null)
        var ultimoId = 0

        if (cursor.moveToFirst()) {
            ultimoId = cursor.getInt(0)  // Obtener el valor de MAX(id)
        }

        cursor.close()
        return ultimoId
    }

    fun insertarCarrera(carrera: ModeloCarrera): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", carrera.nombre)
            put("duracion", carrera.duracion)
            put("materias", carrera.materias.joinToString(","))
            put("idFacultad", carrera.idFacultad)
        }
        return db.insert(DatabaseHelper.TABLE_CARRERA, null, values)
    }

    fun obtenerCarreras(): List<ModeloCarrera> {
        val listaCarreras = mutableListOf<ModeloCarrera>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Carrera", null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val duracion = cursor.getDouble(cursor.getColumnIndexOrThrow("duracion"))
            val materias = cursor.getString(cursor.getColumnIndexOrThrow("materias")).split(",")
            val idFacultad = cursor.getInt(cursor.getColumnIndexOrThrow("idFacultad"))
            listaCarreras.add(ModeloCarrera(id, nombre, duracion, materias, idFacultad))
        }

        cursor.close()
        return listaCarreras
    }

    fun eliminarCarrera(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete("Carrera", "id = ?", arrayOf(id.toString()))
    }

    fun actualizarCarrera(carrera: ModeloCarrera) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", carrera.nombre)
            put("duracion", carrera.duracion)
            put("materias", carrera.materias.joinToString(","))
            put("idFacultad", carrera.idFacultad)
        }

        val filasActualizadas = db.update(
            "Carrera",
            valores,
            "id = ?",
            arrayOf(carrera.id.toString())
        )

        db.close()

        if (filasActualizadas == 0) {
            Log.e("CarreraDAO", "No se pudo actualizar la carrera con ID ${carrera.id}")
        }
    }

}
