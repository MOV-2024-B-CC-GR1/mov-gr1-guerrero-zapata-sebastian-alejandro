package com.example.deber_01

class BaseDatosFacultad {
    companion object {
        val arregloFacultad = arrayListOf<Facultad>()

        init {
            arregloFacultad.add(Facultad(1, "Computacion", 5000.00, true))
            arregloFacultad.add(Facultad(2, "Quimica", 6000.00, true))
            arregloFacultad.add(Facultad(3, "Ciencias", 3000.00, true))
        }
    }
}