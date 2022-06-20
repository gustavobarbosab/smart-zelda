package data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileNotFoundException

class FileReader(private val filePath: String) {
    fun <T> read(): T {
        val json = this::class.java.getResource("../$filePath")?.readText()
            ?: throw FileNotFoundException()
        val type = object : TypeToken<T>() {}.type
        val gson = Gson()
        return gson.fromJson<T>(json, type)
    }
}