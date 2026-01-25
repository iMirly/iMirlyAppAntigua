package com.example.imirly.data.local


import android.content.Context
import com.example.imirly.data.model.Anuncio
import org.json.JSONArray
import org.json.JSONObject
import androidx.core.content.edit
import com.example.imirly.data.model.TipoPrecio

class FavoritesStore(context: Context) {

    private val prefs =
        context.getSharedPreferences("favorites_store", Context.MODE_PRIVATE)

    private val KEY = "favorites"

    fun getAll(): List<Anuncio> {
        val json = prefs.getString(KEY, "[]") ?: "[]"
        val array = JSONArray(json)
        val list = mutableListOf<Anuncio>()

        for (i in 0 until array.length()) {
            val o = array.getJSONObject(i)

            // ---------- TIPO PRECIO SEGURO ----------
            val tipoPrecio = try {
                TipoPrecio.valueOf(o.getString("tipoPrecio"))
            } catch (e: Exception) {
                TipoPrecio.HORA
            }

            // ---------- PRECIO NORMALIZADO ----------
            val precio = if (o.has("precio")) {
                o.optDouble("precio")
            } else {
                null
            }

            list.add(
                Anuncio(
                    id = o.getString("id"),
                    categoria = o.getString("categoria"),
                    subcategoria = o.getString("subcategoria"),
                    nombre = o.getString("nombre"),
                    provincia = o.getString("provincia"),
                    titulo = o.getString("titulo"),
                    descripcion = o.getString("descripcion"),

                    tipoPrecio = tipoPrecio,
                    precio = precio,

                    detalles = JSONObject()
                )
            )
        }

        return list
    }





    fun clear() {
        prefs.edit { remove(KEY) }
    }

    fun isFavorite(id: String): Boolean =
        getAll().any { it.id == id }

    fun toggle(anuncio: Anuncio) {
        val list = getAll().toMutableList()

        if (list.any { it.id == anuncio.id }) {
            list.removeAll { it.id == anuncio.id }
        } else {
            list.add(anuncio)
        }

        save(list)
    }

    private fun save(list: List<Anuncio>) {
        val array = JSONArray()

        list.forEach {
            val obj = JSONObject().apply {
                put("id", it.id)
                put("categoria", it.categoria)
                put("subcategoria", it.subcategoria)
                put("nombre", it.nombre)
                put("provincia", it.provincia)
                put("titulo", it.titulo)
                put("descripcion", it.descripcion)
                put("tipoPrecio", it.tipoPrecio.name)

                if (it.precio != null) {
                    put("precio", it.precio)
                }
            }
            array.put(obj)
        }

        prefs.edit { putString(KEY, array.toString()) }
    }

}
