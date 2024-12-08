package repositories

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import models.Facultad
import java.io.File
import java.io.IOException

object RFacultad {
    private val gson = Gson()
    private val file = File("src/main/resources/facultades.json")
    private val facultades: MutableList<Facultad> = loadData()

    private fun loadData(): MutableList<Facultad> {
        return try {
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, object : TypeToken<MutableList<Facultad>>() {}.type)
            } else {
                mutableListOf()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    fun getAll(): List<Facultad> = facultades.toList()

    fun getById(id: Int): Facultad? = facultades.find { it.id == id }

    fun create(facultad: Facultad) {
        facultades.add(facultad)
        saveData()
    }

    fun update(updatedFacultad: Facultad) {
        val index = facultades.indexOfFirst { it.id == updatedFacultad.id }
        if (index != -1) {
            facultades[index] = updatedFacultad
            saveData()
        }
    }

    fun delete(id: Int) {
        facultades.removeAll { it.id == id }
        saveData()
    }

    private fun saveData() {
        val jsonString = gson.toJson(facultades)
        file.writeText(jsonString)
    }
}
