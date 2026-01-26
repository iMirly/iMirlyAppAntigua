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

        val categoriaJson = root.getJSONObject(categoryId)
        val items = categoriaJson.getJSONArray("items")

        val lista = mutableListOf<Subcategoria>()

        for (i in 0 until items.length()) {
            val item = items.getJSONObject(i)
            lista.add(
                Subcategoria(
                    id = item.getString("nombre")
                        .lowercase()
                        .replace(" ", "_"),
                    nombre = item.getString("nombre"),
                    imagen = item.getString("imagen")
                )
            )
        }

        return lista
    }

    fun obtenerSubcategoriasPorCategoria(categoriaId: String): List<Subcategoria> {
        return obtenerSubcategorias(categoriaId)
    }

    fun categoriaCoincideConBusqueda(
        categoria: Categoria,
        texto: String
    ): Boolean {

        // Coincide con el nombre de la categoría
        if (categoria.nombre.contains(texto, ignoreCase = true)) {
            return true
        }

        // Coincide con alguna subcategoría
        val subcategorias = obtenerSubcategorias(categoria.id)

        return subcategorias.any {
            it.nombre.contains(texto, ignoreCase = true)
        }
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

            // ---------- FILTRO CATEGORIA / SUBCATEGORIA ----------
            if (
                item.getString("categoria") != categoriaId ||
                item.getString("subcategoria") != subcategoriaId
            ) continue

            // ---------- TIPO PRECIO ----------
            val tipoPrecio = try {
                TipoPrecio.valueOf(item.getString("tipoPrecio"))
            } catch (e: Exception) {
                TipoPrecio.HORA // fallback seguro
            }

            // ---------- DETALLES ----------
            val detalles = item.optJSONObject("detalles") ?: JSONObject()

            // ---------- PRECIO NORMALIZADO ----------
            val precio: Double? = when (tipoPrecio) {
                TipoPrecio.HORA ->
                    if (detalles.has("precio_hora")) detalles.optDouble("precio_hora") else null

                TipoPrecio.SERVICIO ->
                    if (detalles.has("precio_servicio")) detalles.optDouble("precio_servicio") else null

                TipoPrecio.DIA ->
                    if (detalles.has("precio_dia")) detalles.optDouble("precio_dia") else null

                TipoPrecio.PROYECTO ->
                    if (detalles.has("precio_proyecto")) detalles.optDouble("precio_proyecto") else null
            }

            // ---------- CREAR ANUNCIO ----------
            lista.add(
                Anuncio(
                    id = item.getString("id"),
                    categoria = item.getString("categoria"),
                    subcategoria = item.getString("subcategoria"),
                    nombre = item.getString("nombre"),
                    provincia = item.getString("provincia"),
                    titulo = item.getString("titulo"),
                    descripcion = item.getString("descripcion"),

                    tipoPrecio = tipoPrecio,
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

            val tipoPrecio = try {
                TipoPrecio.valueOf(item.getString("tipoPrecio"))
            } catch (e: Exception) {
                TipoPrecio.HORA
            }

            val detalles = item.optJSONObject("detalles") ?: JSONObject()

            val precio: Double? = when (tipoPrecio) {
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
                    titulo = item.getString("titulo"),
                    descripcion = item.getString("descripcion"),
                    tipoPrecio = tipoPrecio,
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
