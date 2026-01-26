# 📱 Imirly App: Arquitectura y Flujo de Navegación

Imirly es una aplicación Android desarrollada con **Jetpack Compose** siguiendo una arquitectura **MVVM**, con navegación basada en `NavHost` y datos dinámicos obtenidos desde ficheros JSON locales (mock backend).

La app permite gestionar anuncios de servicios, adaptando su contenido y detalle dinámicamente según la categoría y subcategoría seleccionada.

---

## 🧠 Arquitectura General

La app sigue un flujo de capas unidireccional para garantizar la escalabilidad y facilidad de testeo:

```text
UI (Screens / Composables)
       ↓
    ViewModel
       ↓
    Repository
       ↓
Assets (JSON) / DataStore
```

### 📏 Reglas Clave de Desarrollo
* 🚫 **La UI nunca lee JSON directamente**: Siempre pasa por el ViewModel.
* 🧠 **Lógica de Presentación**: Exclusiva de los `ViewModel`.
* 📦 **Fuente de Datos**: Los `Repository` son la única vía de acceso a los datos.
* 🚀 **Navegación Limpia**: Solo se pasan IDs (strings/ints), nunca objetos completos para evitar errores de memoria.

---

## 🚦 Navegación Principal

La navegación se gestiona íntegramente en: `ImirlyNavHost.kt`.

Este archivo actúa como el **cerebro de rutas** de la app:
* Define el `NavHost`.
* Conecta todas las pantallas.
* Decide qué pantalla mostrar según la ruta activa.
* Gestiona argumentos (ej. `anuncioId`).

### 🗺️ Definición de Rutas
Centralizadas en `Routes.kt` para evitar errores de escritura:

```kotlin
object Routes {
    object Home : Route("home")
    object Anuncios : Route("anuncios/{categoriaId}/{subcategoriaId}")
    object DetalleAnuncio : Route("detalle/{anuncioId}")
    object Favoritos : Route("favoritos")
    object Chat : Route("chat/{anuncioId}")
}

```
> [!IMPORTANT]
> **Regla de Oro:** Las rutas solo transportan identificadores (IDs), nunca modelos de datos completos. Esto asegura que la app sea eficiente y evita errores de consistencia.

---

## 🔄 Flujo de Navegación por Pantallas

### 🏠 Home (`HomeScreen`)
* **Ruta:** `home`
* **Descripción:** Pantalla de entrada con categorías principales y accesos rápidos.
* **Acción:** Al pulsar una categoría, navega a la selección de subcategorías.

### 📂 Subcategorías (`SubcategoriesScreen`)
* **Ruta:** `subcategorias/{categoriaId}`
* **Descripción:** Filtra y muestra las subcategorías correspondientes a la categoría padre seleccionada.

### 📜 Listado de Anuncios (`AnunciosScreen`)
* **Ruta:** `anuncios/{categoriaId}/{subcategoriaId}`
* **Descripción:** Muestra los anuncios filtrados por el nicho seleccionado.
* **Funcionalidades:**
    * Gestión de favoritos en tiempo real.
    * Acceso a filtros dinámicos.
* **Componentes:** `AnunciosViewModel` ← `CategoriasRepository` + `FavoritesStore`.

### 🔍 Detalle del Anuncio (`DetalleAnuncioScreen`)
* **Ruta:** `detalle/{anuncioId}`
* **Estructura Interna:**
    1. **`DetalleAnuncioScreen`**: El contenedor inteligente. Recibe el `anuncioId`, gestiona el `DetalleAnuncioViewModel` y maneja los estados de carga o error.
    2. **`DetalleAnuncioContent`**: El componente visual (UI pura). Recibe los datos ya procesados y renderiza los campos según la configuración dinámica.

---

## 📡 Flujo de Datos en el Detalle

Para renderizar un anuncio, la app sigue este camino lógico para asegurar que los datos coincidan con su formulario específico:

```text
  anuncioId (de la Ruta)
          ↓
  CategoriasRepository  ──→ Obtiene objeto "Anuncio"
          ↓
  FormulariosRepository ──→ Busca "Formulario" (por Categoria + Subcategoria)
          ↓
  UI Dinámica           ──→ Renderiza campos (ej. "Tipo de limpieza", "Materiales")
```

### 💖 Favoritos (`FavoritosScreen`)
- **Ruta:** `favoritos`
- **Descripción:** Pantalla donde se listan todos los anuncios que el usuario ha marcado como favoritos.
- **Persistencia:** Utiliza datos persistidos localmente para que la información no se pierda al cerrar la aplicación.
- **Origen de datos:** `FavoritesStore`

### 💬 Chat (`ChatMirlyScreen`)
- **Ruta:** `chat/{anuncioId}`
- **Descripción:** Interfaz de mensajería para establecer contacto entre usuarios.
- **Acceso:** Se activa desde el botón **"Contactar"** ubicado en la pantalla de detalle del anuncio.

---

## 📋 Formularios Dinámicos (Clave del Proyecto)
El núcleo de la flexibilidad de Imirly reside en sus formularios dinámicos. Estos se definen en el archivo:  
`src/main/assets/formularios.json`

Cada subcategoría tiene un formulario asociado que define:
* **Estructura:** Qué campos específicos existen para ese servicio.
* **Tipos de datos:** Soporte para `boolean`, `number`, `select` y `text`.
* **Versatilidad:** La misma definición de JSON se utiliza para renderizar los **Filtros**, el **Detalle del anuncio** y las pantallas de **Publicación/Edición**.

**Impacto en el desarrollo:**
* 🧹 **Limpieza:** Muestra automáticamente campos como "materiales" o "tipo de limpieza".
* 📚 **Clases:** Muestra campos como "asignaturas" o "modalidad online".
* ✅ **Mantenimiento:** Permite añadir nuevas categorías o campos sin modificar el código de la interfaz (cero *hardcoding* de UI).

---

## 💾 Repositories y Stores

| Componente | Responsabilidad |
| :--- | :--- |
| **CategoriasRepository** | Lee el archivo `anuncios_mock.json`, gestiona el filtrado de anuncios y la búsqueda por ID. |
| **FormulariosRepository** | Procesa `formularios.json` para entregar la configuración dinámica según la categoría elegida. |
| **FavoritesStore** | Almacena los favoritos de forma persistente y sincroniza el estado entre la lista y el detalle. |

google-docs iMyrly compose - archivo

```text
app/src/main/
├── java/com/example/imirly/
│   ├── data/                               # Capa de DATOS: Modelos y acceso a datos puros [cite: 5, 6]
│   │   ├── local/                          # Datos que persisten en el dispositivo [cite: 8, 10]
│   │   │   ├── AnunciosStore.kt            # Gestiona anuncios del usuario y fuente local mock [cite: 13, 15, 17]
│   │   │   ├── FavoritesStore.kt           # Gestiona la persistencia de anuncios favoritos [cite: 21, 23, 284]
│   │   │   ├── SessionStore.kt             # Guarda el estado de sesión (isLoggedIn, userId) [cite: 30, 32, 33]
│   │   │   └── UserStore.kt                # Almacena el perfil del usuario (nombre, email) [cite: 40, 42, 46]
│   │   ├── model/                          # Modelos de datos (POJOs) sin lógica de UI [cite: 47, 48]
│   │   │   ├── Anuncio.kt                  # Modelo central de la aplicación [cite: 50, 51]
│   │   │   ├── Formulario.kt               # Define campos dinámicos por subcategoría [cite: 56, 59, 265]
│   │   │   ├── Categoria.kt                # Estructura de las categorías principales
│   │   │   └── TipoPrecio.kt               # Enum para tipos de cobro (hora, servicio, etc.) [cite: 66, 67]
│   │   ├── remote/                         # Configuración de red (Retrofit/API)
│   │   └── repository/                     # Puente entre datos (JSON/Local) y ViewModels [cite: 76, 77]
│   │       ├── CategoriasRepository.kt     # Lee anuncios_mock.json y filtra por categoría/ID [cite: 78, 80, 277]
│   │       └── FormulariosRepository.kt    # Lee formularios.json para la UI dinámica [cite: 87, 89, 281]
│   │
│   ├── ui/                                 # Capa de INTERFAZ: Componentes Jetpack Compose [cite: 96, 165]
│   │   ├── anuncios/                       # Listado y detalle de servicios [cite: 98, 99]
│   │   │   ├── AnunciosScreen.kt           # Pantalla de listado con cards y filtros [cite: 100, 101]
│   │   │   ├── AnunciosViewModel.kt        # Cerebro del listado; gestiona filtros y favoritos [cite: 106, 108, 110]
│   │   │   ├── DetalleAnuncioScreen.kt     # Pantalla contenedora que gestiona el estado de carga [cite: 115, 116, 233]
│   │   │   └── DetalleAnuncioViewModel.kt  # Busca el anuncio y su formulario dinámico [cite: 125, 128, 243]
│   │   ├── chat/                           # Pantalla de mensajería y su lógica [cite: 258, 260]
│   │   ├── components/                     # UI reutilizable (AnuncioCard, BottomBar) [cite: 134, 135]
│   │   ├── home/                           # Pantalla principal con banners y categorías [cite: 140, 141]
│   │   ├── perfil/                         # Gestión de datos personales y configuración del usuario [cite: 46]
│   │   ├── publicar/                       # Flujo de creación de anuncios y formularios dinámicos [cite: 64, 75]
│   │   ├── subcategories/                  # Selección de subcategoría según la categoría elegida [cite: 211, 213]
│   │   └── theme/                          # Configuración visual (Colores, Tipografía, Tema)
│   │
│   ├── navigation/                         # Sistema de Navegación [cite: 146, 147, 181]
│   │   ├── Routes.kt                       # Definición centralizada de rutas e IDs [cite: 148, 149, 192]
│   │   └── ImirlyNavHost.kt                # Conecta pantallas y gestiona el flujo real [cite: 152, 153, 183]
│   │
│   └── MainActivity.kt                     # Punto de entrada de la aplicación [cite: 37]
│
├── assets/                                 # Datos estáticos en formato JSON (Mock Backend) [cite: 157, 158]
│   ├── anuncios_mock.json                  # Base de datos de anuncios [cite: 159, 278]
│   ├── formularios.json                    # Configuración de campos dinámicos [cite: 160, 264]
│   ├── subcategorias.json                  # Listado de subcategorías [cite: 161]
│   └── provincias.json                     # Datos geográficos [cite: 162]

```


### Archivos JSON de Ejemplo

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

#  Configuración y Conexión a Base de Datos

Instrucciones para levantar el entorno de desarrollo (Backend + Android App).

## 1️⃣ Preparación

1. Descomprimir el proyecto en tu ordenador.

## 2️⃣ Backend (Spring Boot)

### Abrir el proyecto
Puedes usar **IntelliJ IDEA** (recomendado) o NetBeans.

1. Abrir IntelliJ IDEA.
2. `Open` → Seleccionar la carpeta `ImirlyAppbackend`.
3. Esperar a que cargue las dependencias de Maven/Gradle.

### ⚙️ Configuración de la Base de Datos (H2)
El backend utiliza **H2 (Base de datos en memoria)**.
* ✅ **NO** hace falta instalar nada.
* ✅ **NO** hace falta crear la base de datos manualmente.

La configuración se encuentra en:
`src/main/resources/application.properties`

**Configuración típica:**
```properties
spring.datasource.url=jdbc:h2:mem:imirlydb
spring.datasource.username=sa
spring.datasource.password=
```
📌 Usuario: sa
📌 Contraseña: (vacía)

### ▶️ Ejecutar el Backend
Tienes dos opciones para lanzarlo:

* **Opción A:** Buscar la clase `ImirlyAppBackendApplication`, clic derecho → **Run**.
* **Opción B:** Pulsar el botón **▶️ (verde)** en la barra superior de IntelliJ/NetBeans.

Si todo va bien, verás en la consola:
```text
Tomcat started on port 8080
```

👉 El backend ya está funcionando en: `http://localhost:8080`

### 🧪 Verificación (Opcional)
Para confirmar que el backend responde, entra en tu navegador a:  
```
[http://localhost:8080/anuncios](http://localhost:8080/anuncios)
```
Si devuelve un **JSON** → ✅ **Backend OK**

---

## 3️⃣ Frontend (Android App)
1. **Abrir Android Studio.**
2. **Abrir el proyecto** `ImirlyApp`.
3. **Esperar** a que sincronice **Gradle**.

### 📱 Ejecutar la App
1. Iniciar un emulador o conectar un móvil físico.
2. Pulsar **▶️ Run**.

> [!IMPORTANT]
> **Orden de ejecución:** El backend debe estar encendido antes de lanzar la app Android.

### 🔌 Conexión desde el Emulador
La app se conecta al backend utilizando la dirección IP especial para emuladores:  
```
`http://10.0.2.2:8080`
```
---

## ✅ Resumen Rápido del Flujo
1. ▶️ **Run `ImirlyAppbackend`** (Esperar a ver el mensaje `"Tomcat started..."`).
2. ▶️ **Run `ImirlyApp`** en Android Studio.
3. 🎉 La app ya consume datos reales del backend.
