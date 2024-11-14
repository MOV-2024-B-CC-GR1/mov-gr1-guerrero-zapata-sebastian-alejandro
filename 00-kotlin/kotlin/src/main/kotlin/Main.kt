package org.example

fun main() {
    println("Hello World!")

////    calcularSueldo(10.00) //solo parametro requerido
////    calcularSueldo(10.00,15.00,20.00) //parametro requerido y sobreescribir parametros
////
////    calcularSueldo(10.00, bonoEspecial = 20.00)
////    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)
}
//
//fun calcularSueldo(
//    sueldo: Double,
//    tasa: Double = 12.00,
//    bonoEspecial: Double?=null
//): Double {
//    if(bonoEspecial == null) {
//        return sueldo * (100/tasa)
//    }else{
//        return sueldo * (100/tasa) * bonoEspecial
//    }
//}

//abstract class Numeros(
//    protected val numeroUno: Int,
//    protected val numeroDos: Int,
//    parametroNoUsadoPropiedadDeLaClase: Int? = null){
//    init {
//        this.numeroUno
//        this.numeroDos
//        println("Inicializado")
//    }
//}

//abstract class NumerosJava{
//    protected val numeroUno: Int
//    private var numeroDos: Int
//
//    constructor(
//        uno: Int,
//        dos: Int
//    ){
//        this.numeroUno = uno
//        this.numeroDos = dos
//        println("Inicializado")
//    }
//}

//class Suma (
//    unoParametro: Int,
//    dosParametro: Int
//): Numeros(
//    unoParametro,
//    dosParametro
//){
//
//}