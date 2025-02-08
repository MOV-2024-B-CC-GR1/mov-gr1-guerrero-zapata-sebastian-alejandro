package com.example.deber_01

data class ModeloCarrera(
    val id: Int = 0,
    val nombre: String,
    val duracion: Double,
    val materias: List<String>,
    val idFacultad: Int
)
