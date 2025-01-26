package com.example.deber_01

class Facultad (
    var id: Int,
    var nombre: String,
    var presupuesto: Double,
    var activa: Boolean
){
    override fun toString(): String {
        return "facultad: ${nombre}, Presupuesto: ${presupuesto}, Activa: ${activa}"
    }
}