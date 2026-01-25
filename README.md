# 📱 Imirly App
## Arquitectura y Flujo de Navegación

Imirly es una aplicación Android desarrollada con Jetpack Compose siguiendo una arquitectura MVVM, con navegación basada en NavHost y datos dinámicos obtenidos desde ficheros JSON locales (mock backend).

El objetivo de la app es mostrar y gestionar anuncios de servicios, cuyo contenido y detalle se adaptan dinámicamente según la categoría y subcategoría del servicio.

🧠 Arquitectura General
La app sigue el siguiente flujo de capas:

text
UI (Screens / Composables)
   ↓
ViewModel
   ↓
Repository
   ↓
Assets (JSON) / DataStore
Reglas clave:
 La UI nunca lee JSON directamente

 Los ViewModel contienen la lógica de presentación

 Los Repository son la única fuente de datos

 La navegación solo pasa IDs, nunca objetos completos

🚦 Navegación Principal
La navegación de la app se gestiona desde:

ImirlyNavHost.kt
Este archivo:

Define el NavHost

Conecta todas las pantallas

Decide qué pantalla se muestra según la ruta

Gestiona los argumentos de navegación (por ejemplo anuncioId)

Es el mapa central de la app

🗺️ Definición de Rutas
Las rutas se definen en:

Routes.kt
Aquí se centralizan todas las rutas para evitar errores y hardcodeo.

kotlin
object Routes {
    object Home : Route("home")
    object Anuncios : Route("anuncios/{categoriaId}/{subcategoriaId}")
    object DetalleAnuncio : Route("detalle/{anuncioId}")
    object Favoritos : Route("favoritos")
    object Chat : Route("chat/{anuncioId}")
}
 Importante: Las rutas solo transportan identificadores, nunca modelos completos.

Flujo de Navegación por Pantallas
 Home (HomeScreen)
Ruta: home

Descripción: Pantalla principal que muestra categorías y accesos rápidos

Navega a: SubcategoriesScreen
Subcategorías (SubcategoriesScreen)
Ruta: subcategorias/{categoriaId}

Descripción: Muestra las subcategorías de una categoría seleccionada

Navega a: AnunciosScreen

Listado de Anuncios (AnunciosScreen)
Ruta: anuncios/{categoriaId}/{subcategoriaId}

Descripción:

Muestra el listado de anuncios filtrados por categoría y subcategoría

Permite marcar favoritos

Permite abrir filtros dinámicos

ViewModel: AnunciosViewModel

Datos obtenidos desde:

CategoriasRepository

FavoritesStore

Navega a: DetalleAnuncioScreen

Detalle del Anuncio (DetalleAnuncioScreen)
Ruta: detalle/{anuncioId}

Estructura de la pantalla:

DetalleAnuncioScreen

Recibe anuncioId desde la navegación

Crea el DetalleAnuncioViewModel

Gestiona el estado de carga

Decide cuándo mostrar la UI

DetalleAnuncioContent

UI pura

Muestra los datos del anuncio

Renderiza los detalles dinámicamente según el formulario

Flujo de datos:

text
anuncioId
   ↓
CategoriasRepository → Anuncio
   ↓
FormulariosRepository → Formulario
   ↓
Detalle dinámico según campos

Favoritos (FavoritosScreen)
Ruta: favoritos

Descripción: Muestra los anuncios marcados como favoritos

Usa datos persistidos localmente

Datos obtenidos desde: FavoritesStore

Chat (ChatMirlyScreen)
Ruta: chat/{anuncioId}

Descripción: Pantalla de mensajería

Se abre desde el botón "Contactar" del detalle del anuncio

Formularios Dinámicos (Clave del Proyecto)
Los formularios se definen en:

assets/formularios.json
Cada subcategoría tiene asociado un formulario que define:

Qué campos existen

Su tipo (boolean, number, select, text)

Cómo se muestran en:

Filtros

Detalle del anuncio

Publicación / Edición

Esto permite que:

Un anuncio de limpieza muestre "materiales", "tipo de limpieza"

Un anuncio de clases muestre "asignaturas", "online", etc.

Sin hardcodear UI por categoría

Repositories y Stores

CategoriasRepository

Lee anuncios_mock.json

Devuelve anuncios filtrados

Permite buscar un anuncio por ID

FormulariosRepository
Lee formularios.json

Devuelve el formulario correcto según categoría y subcategoría

FavoritesStore
Guarda favoritos de forma persistente

Comunica con listados y detalle del anuncio

Estructura de Archivos Recomendada

```text
app/
├── src/main/
│   ├── java/com/imirly/
│   │   ├── navigation/
│   │   │   ├── ImirlyNavHost.kt
│   │   │   └── Routes.kt
│   │   ├── ui/
│   │   │   ├── home/
│   │   │   ├── subcategories/
│   │   │   ├── anuncios/
│   │   │   ├── detalle/
│   │   │   ├── favoritos/
│   │   │   └── chat/
│   │   ├── viewmodel/
│   │   ├── repository/
│   │   └── data/
│   │       ├── local/
│   │       └── store/
│   └── assets/
│       ├── anuncios_mock.json
│       └── formularios.json

```
Archivos JSON de Ejemplo

anuncios_mock.json
```json
[
  {
    "id": 1,
    "categoriaId": "limpieza",
    "subcategoriaId": "hogar",
    "titulo": "Limpieza completa de casa",
    "precio": 50,
    "camposDinamicos": {
      "materiales": "Profesional",
      "tipoLimpieza": "Profunda"
    }
  }
]
```
formularios.json
```json
{
  "limpieza": {
    "hogar": {
      "campos": [
        {
          "id": "materiales",
          "tipo": "select",
          "opciones": ["Básico", "Profesional", "Ecológico"]
        },
        {
          "id": "tipoLimpieza",
          "tipo": "select",
          "opciones": ["Superficial", "Profunda", "Post-obra"]
        }
      ]
    }
  }
}
```
