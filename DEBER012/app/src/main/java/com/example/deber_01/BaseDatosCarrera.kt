package com.example.deber_01

class BaseDatosCarrera {
    companion object {
        val arregloCarrera = arrayListOf<ModeloCarrera>()

        init {
            arregloCarrera.add(ModeloCarrera(1, "Computación", 4.0, listOf("Redes", "Sistemas Operativos", "Inteligencia Artificial"), 1))
            arregloCarrera.add(ModeloCarrera(2, "Ingeniería Civil", 5.0,  listOf("Estructuras", "Construcción", "Geotecnia"), 1))
            arregloCarrera.add(ModeloCarrera(3, "Química", 4.5,  listOf("Química General", "Orgánica", "Inorgánica"), 2))
            arregloCarrera.add(ModeloCarrera(4, "Matemáticas", 3.5,  listOf("Álgebra", "Cálculo", "Estadística"), 3))
            arregloCarrera.add(ModeloCarrera(5, "Física", 4.0,  listOf("Mecánica", "Termodinámica", "Electromagnetismo"), 3))
        }
    }
}
