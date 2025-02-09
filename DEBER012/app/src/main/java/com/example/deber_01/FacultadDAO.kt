package com.example.deber_01

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class FacultadDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertarFacultad(
        nombre: String,
        presupuesto: Double,
        activa: Boolean,
        latitud: Double,
        longitud: Double
    ): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("presupuesto", presupuesto)
            put("activa", if (activa) 1 else 0)
            put("latitud", latitud)
            put("longitud", longitud)
        }
        return db.insert("Facultad", null, valores)
    }


    fun obtenerFacultades(): List<Facultad> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val listaFacultades = mutableListOf<Facultad>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM Facultad", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val presupuesto = cursor.getDouble(cursor.getColumnIndexOrThrow("presupuesto"))
                val activa = cursor.getInt(cursor.getColumnIndexOrThrow("activa")) == 1
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))  // Agregado
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud")) // Agregado

                listaFacultades.add(Facultad(id, nombre, presupuesto, activa, latitud, longitud))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return listaFacultades
    }


    fun eliminarFacultad(id: Int): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete("Facultad", "id = ?", arrayOf(id.toString()))
    }

    fun actualizarFacultad(
        id: Int,
        nombre: String,
        presupuesto: Double,
        activa: Boolean,
        latitud: Double,
        longitud: Double
    ) {
        val db = dbHelper.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("presupuesto", presupuesto)
            put("activa", if (activa) 1 else 0)
            put("latitud", latitud)
            put("longitud", longitud)
        }
        db.update("Facultad", valores, "id = ?", arrayOf(id.toString()))
    }


}
