# API Endpoints (Resumen Backend)
Esta es la documentaci贸n oficial r谩pida de los endpoints disponibles hasta ahora en el servicio `ms-mascotas`.

> **Nota importante:** Cualquier endpoint protegido por autenticaci贸n espera que el ID del token JWT se pase en la cabecera de la solicitud bajo el par谩metro `X-Auth0-Id`. 

## 1. M贸dulo Usuarios (`/api/v1/usuarios`)

### `POST /api/v1/usuarios/registro`
Registra un nuevo usuario tras el proceso de autenticaci贸n.
- **Headers:** N/A (por ahora)
- **Body (`UsuarioRequestDTO`):**
  ```json
  {
    "auth0Id": "auth0|32h41k",
    "nombre": "Maria Perez",
    "email": "maria@mail.com",
    "telefono": "555-5555",
    "idTipoCuenta": 1,
    "idOrganizacion": null
  }
  ```
- **Response (`201 Created`):** `UsuarioResponseDTO`

### `GET /api/v1/usuarios/me`
Devuelve el perfil del usuario autenticado que solicita la informaci贸n.
- **Headers:** `X-Auth0-Id: auth0|local_dummy_001`
- **Response (`200 OK`):** `UsuarioResponseDTO`

---

## 2. M贸dulo Mascotas (`/api/v1/mascotas`)

### `POST /api/v1/mascotas`
Crear una nueva publicaci贸n de mascota (para adopci贸n).
- **Body (`MascotaRequestDTO`):**
  ```json
  {
    "nombreMascota": "Bobby",
    "descripcion": "Cachorro muy tranquilo",
    "idRaza": 3,
    "idTamanio": 1,
    "idsCaracteristicas": [1, 2, 5],
    "urlsFotografias": ["https://s3.aws.com/fotobobby1.jpg"]
  }
  ```
- **Response (`201 Created`):** `MascotaResponseDTO`

### `GET /api/v1/mascotas`
Recupera todas las mascotas publicadas, detallando caracter铆sticas y tama帽os en cadenas de texto para el Frontend.
- **Response (`200 OK`):** `List<MascotaResponseDTO>`

### `GET /api/v1/mascotas/{id}`
Carga el detalle espec铆fico de una mascota utilizando su `id_mascota` num茅rico.
- **Response (`200 OK`):** `MascotaResponseDTO`

---

## 3. M贸dulo Reportes (`/api/v1/reportes`)

### `POST /api/v1/reportes`
Crea un nuevo reporte (Perdido o Encontrado) relacionando una mascota y un tipo, con validaci贸n expl铆cita de qui茅n es el usuario creador en base a su autenticaci贸n.
- **Headers:** `X-Auth0-Id: auth0|32h41k`
- **Body (`ReporteRequestDTO`):**
  ```json
  {
    "idTipoReporte": 1, 
    "idMascota": 5,
    "idUbicacionReporte": null
  }
  ```
- **Response (`201 Created`):** 
  ```json
  {
    "idReporte": 4,
    "fechaReporte": "2024-05-19T21:15:00",
    "idUbicacionReporte": null,
    "tipoReporte": "Perdido",
    "estadoReporte": "Activo",
    "idUsuario": 2,
    "nombreUsuario": "Maria Perez",
    "idMascota": 5,
    "nombreMascota": "Bobby"
  }
  ```

### `GET /api/v1/reportes`
Listado aplanado y general de los diferentes reportes de la plataforma (脷til para un feed de inicio/mapa general).
- **Response (`200 OK`):** `List<ReporteResponseDTO>`

### `GET /api/v1/reportes/{id}`
Devuelve el detalle del reporte espec铆fico.
- **Response (`200 OK`):** `ReporteResponseDTO`

### `PUT /api/v1/reportes/{id}/cerrar`
Marcador de caso "Cerrado" para cuando la mascota vuelve a su hogar. Verifica en DB interna que el reporte sea efectivamente de la persona conectada, de lo contrario lanza excepci贸n de permisos.
- **Headers:** `X-Auth0-Id: auth0|32h41k`
- **Response (`200 OK`):** 
  ```json
  {
    "idReporte": 4,
    "fechaReporte": "2024-05-19T21:15:00",
    "idUbicacionReporte": null,
    "tipoReporte": "Perdido",
    "estadoReporte": "Cerrado",
    "idUsuario": 2,
    "nombreUsuario": "Maria Perez",
    "idMascota": 5,
    "nombreMascota": "Bobby"
  }
  ```

---

## 4. M骴ulo Cat醠ogos (/api/v1/catalogos)

Endpoints de solo lectura (GET) para poblar listas desplegables (selects) en el Frontend. No requieren autenticaci髇.

### GET /api/v1/catalogos/razas`nDevuelve la lista de razas disponibles.
- **Response (200 OK):** `List<CatalogoResponseDTO>` (id, descripcion)

### GET /api/v1/catalogos/tamanios`nDevuelve la lista de tama駉s disponibles (Peque駉, Mediano, etc.).
- **Response (200 OK):** `List<CatalogoResponseDTO>` (id, descripcion)

### GET /api/v1/catalogos/caracteristicas`nDevuelve la lista de caracter韘ticas posibles para una mascota.
- **Response (200 OK):** `List<CatalogoResponseDTO>` (id, descripcion)

### GET /api/v1/catalogos/tipos-reporte`nDevuelve los tipos de reportes (ej. Perdido, Encontrado).
- **Response (200 OK):** `List<CatalogoResponseDTO>` (id, descripcion)

### GET /api/v1/catalogos/tipos-cuenta`nDevuelve los roles o tipos de cuenta de usuario (ej. Usuario, Refugio).
- **Response (200 OK):** `List<CatalogoResponseDTO>` (id, descripcion)



---

### Resumen de nuevos Endpoints (CRUD Completados)
- **Usuarios**: `PUT /api/v1/usuarios/me` y `DELETE /api/v1/usuarios/me`
- **Mascotas**: `PUT /api/v1/mascotas/{id}` y `DELETE /api/v1/mascotas/{id}`
- **Reportes**: `PUT /api/v1/reportes/{id}` y `DELETE /api/v1/reportes/{id}`

