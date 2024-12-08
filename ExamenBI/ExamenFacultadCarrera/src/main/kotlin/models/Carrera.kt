package models

data class Carrera(
    val id: Int,
    val nombre: String,
    val duracion: Double,
    val acreditada: Boolean,
    val materias: List<String>,
    val idFacultad: Int // Este campo es obligatorio
)
