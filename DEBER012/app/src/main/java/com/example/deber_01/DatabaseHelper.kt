    package com.example.deber_01

    import android.content.Context
    import android.database.sqlite.SQLiteDatabase
    import android.database.sqlite.SQLiteOpenHelper

    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_TABLE_FACULTAD)
            db.execSQL(CREATE_TABLE_CARRERA)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_FACULTAD")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_CARRERA")
            onCreate(db)
        }

        companion object {
            private const val DATABASE_NAME = "universidad.db"
            private const val DATABASE_VERSION = 1

            const val TABLE_FACULTAD = "Facultad"
            const val TABLE_CARRERA = "Carrera"

            private const val CREATE_TABLE_FACULTAD = """
                CREATE TABLE $TABLE_FACULTAD (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    presupuesto REAL NOT NULL,
                    activa INTEGER NOT NULL,
                    latitud REAL,
                    longitud REAL
                )
            """

            private const val CREATE_TABLE_CARRERA = """
                CREATE TABLE $TABLE_CARRERA (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    duracion REAL NOT NULL,
                    materias TEXT NOT NULL,
                    idFacultad INTEGER NOT NULL,
                    FOREIGN KEY(idFacultad) REFERENCES Facultad(id)
                )
            """
        }
    }
