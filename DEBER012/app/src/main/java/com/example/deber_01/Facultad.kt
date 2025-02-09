package com.example.deber_01

data class Facultad(
    val id: Int = 0,
    val nombre: String,
    val presupuesto: Double,
    val activa: Boolean,
    val latitud: Double = 0.0,    // valor por defecto (modifica según convenga)
    val longitud: Double = 0.0
)
