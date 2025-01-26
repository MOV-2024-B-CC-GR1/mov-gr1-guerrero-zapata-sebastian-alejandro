package com.example.deber_01

class ModeloCarrera (
    var id: Int,
    var nombre: String,
    var duracion: Double,
    var materias: List<String>,
    var idFacultad: Int // Este campo es obligatorio
){
}