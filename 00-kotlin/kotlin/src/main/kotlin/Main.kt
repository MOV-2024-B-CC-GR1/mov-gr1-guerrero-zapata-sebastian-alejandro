package org.example

fun main() {
    println("Hello World!")



////    calcularSueldo(10.00) //solo parametro requerido
////    calcularSueldo(10.00,15.00,20.00) //parametro requerido y sobreescribir parametros
////
////    calcularSueldo(10.00, bonoEspecial = 20.00)
////    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3,4,5);
    println(arregloEstatico);

    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    );

    println(arregloDinamico);
    arregloDinamico.add(11);
    arregloDinamico.add(12);
    println(arregloDinamico);

    //FOR EACH => Unit
    //Itera un arreglo

    val respuestaForEach: Unit = arregloDinamico
        .forEach {valorActual: Int ->
            println("valor actual: ${valorActual}");
        }

    arregloDinamico.forEach{ println("Valor actual (it): ${it}")}

    //MAP -> MUTA(modifica cambia) el arreglo
    //1) Enviamos el nuevo valor de la interaccion
    //2) Nos devuelve el NUEVO ARREGLO con valores de las iteraciones

    val respuestaMap: List<Double>  =  arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    // Filter - > Filtrar el ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo FILITRADO

//    val respuestaFilter: List<Int> = arregloDinamico
//        .filter { valorActual: Int->
//            return@filter mayoresACinco
//        }
//    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
//    println(respuestaFilter)
//    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY(alguno cumplle?)
    //AND -> ALL(Todos cumplen)

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5) }
    println(respuestaAny) // True
    val respuestaAll: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5) }
    println(respuestaAll) //False

    //REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siemper empieza en 0 emn Kotlin
    // [1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    //ValorInteracion1 = ValorEmpieza + 1 = 0 + 1 = 1 -> Iteracion 1
    //ValorInteracion2 = ValorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Iteracion 2
    //ValorInteracion3 = ValorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Iteracion 2
    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulador: Int, valorActual:Int ->
            return@reduce (acumulador * valorActual)
        }
    println(respuestaReduce);
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


