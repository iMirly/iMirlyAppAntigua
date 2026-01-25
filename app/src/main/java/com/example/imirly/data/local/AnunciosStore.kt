package com.example.imirly.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.imirly.data.model.MiAnuncio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import java.util.UUID

private val Context.dataStore by preferencesDataStore(
    name = "imirly_anuncios"
)

class AnunciosStore(
    private val context: Context
) {

    companion object {
        private val ANUNCIOS_KEY = stringPreferencesKey("mis_anuncios")
    }

    private val gson = Gson()

    /* ---------- GET ALL ---------- */

    suspend fun getAnuncios(): List<MiAnuncio> {
        val prefs = context.dataStore.data.first()
        val json = prefs[ANUNCIOS_KEY] ?: return emptyList()

        val type = object : TypeToken<List<MiAnuncio>>() {}.type
        return gson.fromJson(json, type)
    }

    /* ---------- GET BY ID ---------- */

    suspend fun getAnuncioById(id: String): MiAnuncio? {
        return getAnuncios().firstOrNull { it.id == id }
    }

    /* ---------- CREATE ---------- */

    suspend fun guardarAnuncio(anuncio: MiAnuncio) {
        val actuales = getAnuncios().toMutableList()

        actuales.add(
            anuncio.copy(
                id = UUID.randomUUID().toString()
            )
        )

        guardarLista(actuales)
    }

    /* ---------- UPDATE ---------- */

    suspend fun actualizarAnuncio(anuncioEditado: MiAnuncio) {
        val actualizados = getAnuncios().map { anuncio ->
            if (anuncio.id == anuncioEditado.id) {
                anuncioEditado
            } else anuncio
        }

        guardarLista(actualizados)
    }

    /* ---------- STATUS ---------- */

    suspend fun cambiarEstadoAnuncio(anuncioId: String, activo: Boolean) {
        val actualizados = getAnuncios().map { anuncio ->
            if (anuncio.id == anuncioId) {
                anuncio.copy(activo = activo)
            } else anuncio
        }

        guardarLista(actualizados)
    }

    /* ---------- DELETE ---------- */

    suspend fun borrarAnuncio(anuncioId: String) {
        val filtrados = getAnuncios()
            .filterNot { it.id == anuncioId }

        guardarLista(filtrados)
    }

    /* ---------- INTERNAL ---------- */

    private suspend fun guardarLista(lista: List<MiAnuncio>) {
        context.dataStore.edit { prefs ->
            prefs[ANUNCIOS_KEY] = gson.toJson(lista)
        }
    }
}
