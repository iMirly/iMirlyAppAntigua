package com.example.imirly.data.repository

import android.content.Context
import com.example.imirly.data.model.CampoFormulario
import com.example.imirly.data.model.Formulario
import org.json.JSONObject

class FormulariosRepository(
    private val context: Context
) {

    fun obtenerFormulario(
        categoriaId: String,
        subcategoriaId: String
    ): Formulario? {

        val json = context.assets.open("formularios.json")
            .bufferedReader()
            .use { it.readText() }

        val root = JSONObject(json)

        if (!root.has(categoriaId)) return null
        val categoria = root.getJSONObject(categoriaId)

        if (!categoria.has(subcategoriaId)) return null
        val subcategoria = categoria.getJSONObject(subcategoriaId)

        val titulo = subcategoria.getString("titulo")
        val camposJson = subcategoria.getJSONArray("campos")

        val campos = mutableListOf<CampoFormulario>()

        for (i in 0 until camposJson.length()) {
            val campo = camposJson.getJSONObject(i)

            campos.add(
                CampoFormulario(
                    id = campo.getString("id"),
                    label = campo.getString("label"),
                    tipo = campo.getString("tipo"),
                    required = campo.optBoolean("required", false),
                    opciones = if (campo.has("opciones"))
                        campo.getJSONArray("opciones").let { array ->
                            List(array.length()) { array.getString(it) }
                        }
                    else emptyList()
                )
            )
        }

        return Formulario(
            titulo = titulo,
            campos = campos.toList()
        )
    }
}
