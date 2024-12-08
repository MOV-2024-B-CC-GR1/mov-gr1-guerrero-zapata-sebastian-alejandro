package models

data class Facultad(
    val id: Int,                     // Identificador único de la facultad
    val nombre: String,              // Nombre de la facultad
    val presupuesto: Double,         // Presupuesto asignado a la facultad
    val activa: Boolean,             // Indica si la facultad está activa o no
    val departamentos: List<String>  // Lista de nombres de departamentos en la facultad
)
