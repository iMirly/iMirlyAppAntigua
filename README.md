<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=rect&color=0:6c5ce7,100:3f51b5&height=180&section=header&text=iMirly%20App%20Antigua&fontSize=60&fontColor=fff&fontAlignY=40&desc=Primera%20versi%C3%B3n%20Android%20del%20marketplace%20iMirly&descAlignY=70&descSize=18" width="100%"/>
</div>

<br>

<div align="center">
  <a href="https://github.com/iMirly/iMirlyAppAntigua" target="_blank">
    <img src="https://img.shields.io/badge/Ver_Código-3f51b5?style=for-the-badge&logo=github&logoColor=white"/>
  </a>
  <a href="https://github.com/iMirly/iMirly" target="_blank">
    <img src="https://img.shields.io/badge/Repositorio_Principal-6c5ce7?style=for-the-badge&logo=github&logoColor=white"/>
  </a>
  <img src="https://img.shields.io/badge/Status-Legacy-9E9E9E?style=for-the-badge" alt="Status" />
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Platform" />
  <img src="https://img.shields.io/badge/Kotlin-Jetpack%20Compose-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Architecture-MVVM-8A2BE2?style=for-the-badge" alt="Architecture" />
</div>

<br>

<div align="center">
  <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=22&duration=3000&pause=1000&color=6c5ce7&center=true&vCenter=true&width=600&lines=Kotlin+%2B+Jetpack+Compose;Arquitectura+MVVM+por+capas;Navegaci%C3%B3n+con+NavHost+%2B+Routes;Formularios+din%C3%A1micos+desde+JSON;Mock+backend+con+assets+locales;DataStore+para+favoritos+y+sesi%C3%B3n"/>
</div>

# 🚀 Sobre el Proyecto

**iMirly App Antigua** es la **primera versión funcional** de la aplicación Android de iMirly, construida con **Kotlin + Jetpack Compose** y arquitectura **MVVM**. Esta versión funciona con un **mock backend** basado en ficheros JSON locales (`assets/`) y sirvió como base para validar el flujo de navegación, los formularios dinámicos y la experiencia de usuario antes de conectar la app al backend real con Spring Boot.

✨ Características principales:

- 📱 **UI 100% Jetpack Compose** con Material 3
- 🧠 **Arquitectura MVVM** con separación estricta UI ← ViewModel ← Repository ← Data
- 🗺️ **Navegación centralizada** con `NavHost` y `Routes.kt` (solo IDs, nunca objetos)
- 📋 **Formularios dinámicos** definidos en `formularios.json` para filtros, detalle y publicación
- 💖 **Favoritos persistentes** con DataStore Preferences
- 🔐 **Sesión y perfil de usuario** locales (`SessionStore`, `UserStore`)
- 💬 **Chat básico** entre cliente y profesional desde el detalle del anuncio
- 🧪 **Mock backend** en `assets/` (anuncios, categorías, subcategorías, provincias)

# 🧠 Contexto del Proyecto

Este repositorio forma parte del ecosistema **iMirly**, desarrollado como **Trabajo Fin de Ciclo (TFC)** del ciclo de **DAM (Desarrollo de Aplicaciones Multiplataforma)** en **NDT NewDigitalTalent · Granada**.

Es la **versión legacy** de la app Android: el punto de partida del proyecto, anterior a la migración a arquitectura hexagonal y a la conexión con el backend Spring Boot. Se mantiene como referencia histórica y para mostrar la evolución técnica del proyecto.

> [NOTA]
> Para la versión actual y mantenida de la app, visita el repositorio principal [**iMirly**](https://github.com/iMirly/iMirly).

# 🏗️ Arquitectura

Flujo de capas unidireccional para garantizar escalabilidad y testeo:

```text
UI (Screens / Composables)
       ↓
    ViewModel
       ↓
    Repository
       ↓
Assets (JSON) / DataStore
```

### 📏 Reglas clave

- 🚫 La UI **nunca** lee JSON directamente: siempre pasa por el ViewModel
- 🧠 La lógica de presentación vive **exclusivamente** en los `ViewModel`
- 📦 Los `Repository` son la **única vía** de acceso a los datos
- 🚀 Las rutas solo transportan **IDs** (String / Int), nunca modelos completos

# 🗺️ Navegación

Toda la navegación se gestiona en `ImirlyNavHost.kt` con rutas centralizadas en `Routes.kt`:

```kotlin
object Routes {
    object Home : Route("home")
    object Anuncios : Route("anuncios/{categoriaId}/{subcategoriaId}")
    object DetalleAnuncio : Route("detalle/{anuncioId}")
    object Favoritos : Route("favoritos")
    object Chat : Route("chat/{anuncioId}")
}
```

| Pantalla | Ruta | Descripción |
|:---|:---|:---|
| 🏠 **Home** | `home` | Categorías principales y accesos rápidos |
| 📂 **Subcategorías** | `subcategorias/{categoriaId}` | Filtrado de subcategorías por categoría padre |
| 📜 **Listado de Anuncios** | `anuncios/{categoriaId}/{subcategoriaId}` | Anuncios del nicho seleccionado + favoritos |
| 🔍 **Detalle del Anuncio** | `detalle/{anuncioId}` | Ficha completa con UI dinámica según formulario |
| 💖 **Favoritos** | `favoritos` | Anuncios marcados, persistidos en DataStore |
| 💬 **Chat** | `chat/{anuncioId}` | Mensajería con el publicador del anuncio |

# 📋 Formularios Dinámicos

El núcleo de la flexibilidad de iMirly reside en `assets/formularios.json`. Cada subcategoría define su propio formulario, que se reutiliza para:

- 🔍 **Filtros** del listado
- 📄 **Detalle** del anuncio
- ➕ **Publicación** y edición

Soporta tipos `boolean`, `number`, `select` y `text`. Añadir una nueva categoría no requiere tocar la UI — **cero hardcoding**.

# 🗂️ Estructura del Proyecto

```bash
app/src/main/
├── java/com/example/imirly/
│   ├── data/
│   │   ├── local/              # AnunciosStore, FavoritesStore, SessionStore, UserStore
│   │   ├── model/              # Anuncio, Formulario, Categoria, TipoPrecio
│   │   ├── remote/             # Configuración de red (Retrofit)
│   │   └── repository/         # CategoriasRepository, FormulariosRepository
│   │
│   ├── ui/
│   │   ├── anuncios/           # Listado y detalle de servicios
│   │   ├── chat/               # Mensajería
│   │   ├── components/         # AnuncioCard, BottomBar y UI reutilizable
│   │   ├── home/               # Pantalla principal
│   │   ├── perfil/             # Datos del usuario
│   │   ├── publicar/           # Creación de anuncios
│   │   ├── subcategories/      # Selección de subcategoría
│   │   └── theme/              # Colores, tipografía y tema
│   │
│   ├── navigation/             # Routes.kt + ImirlyNavHost.kt
│   └── MainActivity.kt
│
└── assets/                     # Mock backend JSON
    ├── anuncios_mock.json
    ├── formularios.json
    ├── subcategorias.json
    └── provincias.json
```

# 🛠️ Stack Técnico

| Categoría | Tecnología |
|:---|:---|
| **Lenguaje** | Kotlin |
| **UI** | Jetpack Compose · Material 3 |
| **Arquitectura** | MVVM por capas |
| **Navegación** | Navigation Compose (`NavHost`) |
| **Persistencia** | DataStore Preferences |
| **Red (preparado)** | Retrofit 2 + Gson |
| **Async** | Coroutines + StateFlow |
| **Background** | WorkManager |
| **SDK** | minSdk 24 · targetSdk 36 |

# ⚙️ Instalación

```bash
# 1. Clonar el repositorio
git clone https://github.com/iMirly/iMirlyAppAntigua.git

# 2. Abrir en Android Studio (Hedgehog o superior)
# 3. Sincronizar Gradle y ejecutar en un emulador o dispositivo (API 24+)
```

No requiere backend: todos los datos provienen de `app/src/main/assets/`.

# 🔗 Ecosistema iMirly

| Repositorio | Descripción |
|:---|:---|
| [**iMirly**](https://github.com/iMirly/iMirly) | Repositorio principal del ecosistema |
| [**iMirlyAppBackend**](https://github.com/iMirly/ImirlyAppbackend) | Backend Spring Boot + Panel de administración |
| [**iMirlyDocumentacion**](https://github.com/iMirly/iMirlyDocumentacion) | Documentación técnica oficial |
| [**iMirlyWeb-MVP**](https://github.com/iMirly/iMirlyWeb-MVP) | Demo web del marketplace |
| [**iMirlyPresentacion**](https://github.com/iMirly/iMirlyPresentacion) | Web de presentación del TFC |

# 👥 Equipo

Proyecto desarrollado como TFC del ciclo **DAM** en **NDT NewDigitalTalent · Granada**.

<div align="center">
  <a href="https://github.com/iMirly" target="_blank">
    <img src="https://img.shields.io/badge/Organización_GitHub-3f51b5?style=for-the-badge&logo=github&logoColor=white"/>
  </a>
</div>

<div align="center">
  <img src="https://capsule-render.vercel.app/api?type=rect&color=0:3f51b5,100:6c5ce7&height=80&section=footer" width="100%"/>
</div>
