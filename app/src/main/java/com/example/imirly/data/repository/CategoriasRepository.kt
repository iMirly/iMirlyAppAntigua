package com.example.imirly.data.repository

import android.content.Context
import com.example.imirly.data.model.Anuncio
import com.example.imirly.data.model.Categoria
import com.example.imirly.data.model.Subcategoria
import com.example.imirly.data.model.TipoPrecio
import org.json.JSONArray
import org.json.JSONObject

class CategoriasRepository(
    private val context: Context
) {

    fun obtenerCategorias(): List<Categoria> {
        val json = context.assets.open("subcategorias.json")
            .bufferedReader()
            .use { it.readText() }

        val root = JSONObject(json)
        val categorias = mutableListOf<Categoria>()

        root.keys().forEach { key ->
            val categoriaJson = root.getJSONObject(key)
            val items = categoriaJson.getJSONArray("items")

            categorias.add(
                Categoria(
                    id = key,
                    nombre = key.replaceFirstChar { it.uppercase() },
                    totalProfesionales = items.length(),
                    icono = key
                )
            )
        }

        return categorias
    }

    fun obtenerSubcategorias(categoryId: String): List<Subcategoria> {
        val json = context.assets.open("subcategorias.json")
            .bufferedReader()
            .use { it.readText() }

        val root = JSONObject(json)
        if (!root.has(categoryId)) return emptyList()

        val items = root.getJSONObject(categoryId).getJSONArray("items")
        val lista = mutableListOf<Subcategoria>()

        for (i in 0 until items.length()) {
            val item = items.getJSONObject(i)
            lista.add(
                Subcategoria(
                    id = item.getString("nombre").lowercase().replace(" ", "_"),
                    nombre = item.getString("nombre"),
                    imagen = item.getString("imagen")
                )
            )
        }

        return lista
    }

    fun obtenerAnuncios(
        categoriaId: String,
        subcategoriaId: String
    ): List<Anuncio> {

        val json = context.assets.open("anuncios_mock.json")
            .bufferedReader()
            .use { it.readText() }

        val array = JSONArray(json)
        val lista = mutableListOf<Anuncio>()

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            if (
                item.getString("categoria") != categoriaId ||
                item.getString("subcategoria") != subcategoriaId
            ) continue

            val tipoPrecioEnum = try {
                TipoPrecio.valueOf(item.getString("tipoPrecio"))
            } catch (e: Exception) {
                TipoPrecio.HORA
            }

            val detalles = item.optJSONObject("detalles") ?: JSONObject()

            val precio: Double? = when (tipoPrecioEnum) {
                TipoPrecio.HORA -> detalles.optDouble("precio_hora", Double.NaN).takeIf { !it.isNaN() }
                TipoPrecio.SERVICIO -> detalles.optDouble("precio_servicio", Double.NaN).takeIf { !it.isNaN() }
                TipoPrecio.DIA -> detalles.optDouble("precio_dia", Double.NaN).takeIf { !it.isNaN() }
                TipoPrecio.PROYECTO -> detalles.optDouble("precio_proyecto", Double.NaN).takeIf { !it.isNaN() }
            }

            lista.add(
                Anuncio(
                    id = item.getString("id"),
                    categoria = item.getString("categoria"),
                    subcategoria = item.getString("subcategoria"),
                    nombre = item.getString("nombre"),
                    provincia = item.getString("provincia"),
                    localidad = item.optString("localidad", ""),
                    titulo = item.getString("titulo"),
                    descripcion = item.getString("descripcion"),
                    tipoPrecio = tipoPrecioEnum.name,
                    precio = precio,
                    detalles = detalles
                )

            )
        }

        return lista
    }

    fun obtenerTodosLosAnuncios(): List<Anuncio> {
        val json = context.assets.open("anuncios_mock.json")
            .bufferedReader()
            .use { it.readText() }

        val array = JSONArray(json)
        val lista = mutableListOf<Anuncio>()

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            val tipoPrecioEnum = try {
                TipoPrecio.valueOf(item.getString("tipoPrecio"))
            } catch (e: Exception) {
                TipoPrecio.HORA
            }

            val detalles = item.optJSONObject("detalles") ?: JSONObject()

            val precio: Double? = when (tipoPrecioEnum) {
                TipoPrecio.HORA -> detalles.optDouble("precio_hora", Double.NaN).takeIf { !it.isNaN() }
                TipoPrecio.SERVICIO -> detalles.optDouble("precio_servicio", Double.NaN).takeIf { !it.isNaN() }
                TipoPrecio.DIA -> detalles.optDouble("precio_dia", Double.NaN).takeIf { !it.isNaN() }
                TipoPrecio.PROYECTO -> detalles.optDouble("precio_proyecto", Double.NaN).takeIf { !it.isNaN() }
            }

            lista.add(
                Anuncio(
                    id = item.getString("id"),
                    categoria = item.getString("categoria"),
                    subcategoria = item.getString("subcategoria"),
                    nombre = item.getString("nombre"),
                    provincia = item.getString("provincia"),
                    localidad = item.optString("localidad", ""),
                    titulo = item.getString("titulo"),
                    descripcion = item.getString("descripcion"),
                    tipoPrecio = tipoPrecioEnum.name,
                    precio = precio,
                    detalles = detalles
                )

            )
        }

        return lista
    }

    fun obtenerAnuncioPorId(id: String): Anuncio? {
        return obtenerTodosLosAnuncios().find { it.id == id }
    }
}
