package repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Carrera
import java.io.File
import java.io.IOException

object RCarrera {
    private val gson = Gson()
    private val file = File("src/main/resources/carreras.json")
    private val carreras: MutableList<Carrera> = loadData()

    private fun loadData(): MutableList<Carrera> {
        return try {
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, object : TypeToken<MutableList<Carrera>>() {}.type)
            } else {
                mutableListOf()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    fun getAll(): List<Carrera> = carreras.toList()

    fun getByFacultadId(facultadId: Int): List<Carrera> {
        return carreras.filter { it.idFacultad == facultadId }
    }

    fun getById(id: Int): Carrera? = carreras.find { it.id == id }

    fun create(carrera: Carrera) {
        carreras.add(carrera)
        saveData()
    }

    fun update(updatedCarrera: Carrera) {
        val index = carreras.indexOfFirst { it.id == updatedCarrera.id }
        if (index != -1) {
            carreras[index] = updatedCarrera
            saveData()
        }
    }

    fun delete(id: Int) {
        carreras.removeAll { it.id == id }
        saveData()
    }

    private fun saveData() {
        val jsonString = gson.toJson(carreras)
        file.writeText(jsonString)
    }

}
