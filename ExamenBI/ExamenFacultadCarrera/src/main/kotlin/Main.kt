import models.Carrera
import models.Facultad
import repositories.RCarrera
import repositories.RFacultad

fun main() {
    while (true) {
        println("Seleccione una opción:")
        println("1. Ver facultades")
        println("2. Agregar facultad")
        println("3. Actualizar facultad")
        println("4. Eliminar facultad")
        println("5. Ver carreras por facultad")
        println("6. Agregar carrera")
        println("7. Actualizar carrera")
        println("8. Eliminar carrera")
        println("9. Salir")

        when (readlnOrNull()?.toIntOrNull() ?: 0) {
            1 -> verFacultades()
            2 -> agregarFacultad()
            3 -> actualizarFacultad()
            4 -> eliminarFacultad()
            5 -> verCarrerasPorFacultad()
            6 -> agregarCarrera()
            7 -> actualizarCarrera()
            8 -> eliminarCarrera()
            9 -> return
            else -> println("Opción no válida. Por favor, elija una opción válida.")
        }
    }
}

fun verFacultades() {
    val facultades = RFacultad.getAll()
    if (facultades.isEmpty()) {
        println("No hay facultades registradas.")
    } else {
        println("Facultades:")
        facultades.forEach { println(it) }
    }
}

fun agregarFacultad() {
    println("Ingrese el nombre de la facultad:")
    val nombre = readlnOrNull().toString()

    println("Ingrese el presupuesto asignado:")
    val presupuesto = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    println("¿Está activa la facultad? (true/false):")
    val activa = readlnOrNull()?.toBooleanStrictOrNull() ?: false

    println("Ingrese los departamentos separados por comas:")
    val departamentos = readlnOrNull()?.split(",")?.map { it.trim() } ?: listOf()

    val nuevaFacultad = Facultad(
        id = RFacultad.getAll().size + 1,
        nombre = nombre,
        presupuesto = presupuesto,
        activa = activa,
        departamentos = departamentos
    )
    RFacultad.create(nuevaFacultad)

    println("Facultad agregada correctamente:")
    println(nuevaFacultad)
}

fun actualizarFacultad() {
    println("Ingrese el ID de la facultad a actualizar:")
    val idFacultad = readlnOrNull()?.toIntOrNull()
    if (idFacultad != null) {
        val facultadExistente = RFacultad.getById(idFacultad)
        if (facultadExistente != null) {
            println("Ingrese el nuevo nombre de la facultad:")
            val nombre = readlnOrNull().toString()

            println("Ingrese el nuevo presupuesto asignado:")
            val presupuesto = readlnOrNull()?.toDoubleOrNull() ?: facultadExistente.presupuesto

            println("¿Está activa la facultad? (true/false):")
            val activa = readlnOrNull()?.toBooleanStrictOrNull() ?: facultadExistente.activa

            println("Ingrese los nuevos departamentos separados por comas:")
            val departamentos = readlnOrNull()?.split(",")?.map { it.trim() } ?: facultadExistente.departamentos

            val facultadActualizada = Facultad(idFacultad, nombre, presupuesto, activa, departamentos)
            RFacultad.update(facultadActualizada)

            println("Facultad actualizada correctamente:")
            println(facultadActualizada)
        } else {
            println("No se encontró ninguna facultad con el ID proporcionado.")
        }
    } else {
        println("ID de facultad no válido.")
    }
}

fun eliminarFacultad() {
    println("Ingrese el ID de la facultad a eliminar:")
    val idFacultad = readlnOrNull()?.toIntOrNull()
    if (idFacultad != null) {
        val facultadExistente = RFacultad.getById(idFacultad)
        if (facultadExistente != null) {
            RFacultad.delete(idFacultad)
            println("Facultad eliminada correctamente.")
        } else {
            println("No se encontró ninguna facultad con el ID proporcionado.")
        }
    } else {
        println("ID de facultad no válido.")
    }
}

fun verCarrerasPorFacultad() {
    println("Ingrese el ID de la facultad:")
    val idFacultad = readlnOrNull()?.toIntOrNull()
    if (idFacultad != null) {
        val carreras = RCarrera.getByFacultadId(idFacultad)
        if (carreras.isEmpty()) {
            println("No hay carreras registradas para esta facultad.")
        } else {
            println("Carreras para la Facultad $idFacultad:")
            carreras.forEach { println(it) }
        }
    } else {
        println("ID de facultad no válido.")
    }
}

fun agregarCarrera() {
    println("Ingrese el nombre de la carrera:")
    val nombre = readlnOrNull().toString()

    println("Ingrese la duración de la carrera (en años):")
    val duracion = readlnOrNull()?.toDoubleOrNull() ?: 0.0

    println("¿Está acreditada la carrera? (true/false):")
    val acreditada = readlnOrNull()?.toBooleanStrictOrNull() ?: false

    println("Ingrese las materias separadas por comas:")
    val materias = readlnOrNull()?.split(",")?.map { it.trim() } ?: listOf()

    println("Ingrese el ID de la facultad para esta carrera:")
    val idFacultad = readlnOrNull()?.toIntOrNull()

    // Validar que idFacultad no sea nulo
    if (idFacultad != null) {
        val nuevaCarrera = Carrera(
            id = RCarrera.getAll().size + 1,
            nombre = nombre,
            duracion = duracion,
            acreditada = acreditada,
            materias = materias,
            idFacultad = idFacultad // Se pasa el idFacultad correctamente
        )

        RCarrera.create(nuevaCarrera)

        println("Carrera agregada correctamente:")
        println(nuevaCarrera)
    } else {
        println("ID de facultad no válido. No se puede crear la carrera.")
    }
}

fun actualizarCarrera() {
    println("Ingrese el ID de la carrera a actualizar:")
    val idCarrera = readlnOrNull()?.toIntOrNull()
    if (idCarrera != null) {
        val carreraExistente = RCarrera.getById(idCarrera)
        if (carreraExistente != null) {
            println("Ingrese el nuevo nombre de la carrera:")
            val nombre = readlnOrNull().toString()

            println("Ingrese la nueva duración de la carrera (en años):")
            val duracion = readlnOrNull()?.toDoubleOrNull() ?: carreraExistente.duracion

            println("¿Está acreditada la carrera? (true/false):")
            val acreditada = readlnOrNull()?.toBooleanStrictOrNull() ?: carreraExistente.acreditada

            println("Ingrese las nuevas materias separadas por comas:")
            val materias = readlnOrNull()?.split(",")?.map { it.trim() } ?: carreraExistente.materias

            println("Ingrese el nuevo ID de la facultad para esta carrera:")
            val idFacultad = readlnOrNull()?.toIntOrNull()

            // Validar que idFacultad no sea nulo
            if (idFacultad != null) {
                val carreraActualizada = Carrera(
                    id = idCarrera,
                    nombre = nombre,
                    duracion = duracion,
                    acreditada = acreditada,
                    materias = materias,
                    idFacultad = idFacultad // Se pasa el idFacultad correctamente
                )

                RCarrera.update(carreraActualizada)

                println("Carrera actualizada correctamente:")
                println(carreraActualizada)
            } else {
                println("ID de facultad no válido. No se puede actualizar la carrera.")
            }
        } else {
            println("No se encontró ninguna carrera con el ID proporcionado.")
        }
    } else {
        println("ID de carrera no válido.")
    }
}


fun eliminarCarrera() {
    println("Ingrese el ID de la carrera a eliminar:")
    val idCarrera = readlnOrNull()?.toIntOrNull()
    if (idCarrera != null) {
        val carreraExistente = RCarrera.getById(idCarrera)
        if (carreraExistente != null) {
            RCarrera.delete(idCarrera)
            println("Carrera eliminada correctamente.")
        } else {
            println("No se encontró ninguna carrera con el ID proporcionado.")
        }
    } else {
        println("ID de carrera no válido.")
    }
}
