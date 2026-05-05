# API Endpoints (Resumen Backend)
Esta es la documentación oficial rápida de los endpoints disponibles hasta ahora en el servicio `ms-mascotas`.

> **Nota importante:** Cualquier endpoint protegido por autenticación espera que el ID del token JWT se pase en la cabecera de la solicitud bajo el parámetro `X-Auth0-Id`. 

## 1. Módulo Usuarios (`/api/v1/usuarios`)

### `POST /api/v1/usuarios/registro`
Registra un nuevo usuario tras el proceso de autenticación.
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
Devuelve el perfil del usuario autenticado que solicita la información.
- **Headers:** `X-Auth0-Id: auth0|local_dummy_001`
- **Response (`200 OK`):** `UsuarioResponseDTO`

---

## 2. Módulo Mascotas (`/api/v1/mascotas`)

### `POST /api/v1/mascotas`
Crear una nueva publicación de mascota (para adopción).
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
Recupera todas las mascotas publicadas, detallando características y tamaños en cadenas de texto para el Frontend.
- **Response (`200 OK`):** `List<MascotaResponseDTO>`

### `GET /api/v1/mascotas/{id}`
Carga el detalle específico de una mascota utilizando su `id_mascota` numérico.
- **Response (`200 OK`):** `MascotaResponseDTO`

---

## 3. Módulo Reportes (`/api/v1/reportes`)

### `POST /api/v1/reportes`
Crea un nuevo reporte (Perdido o Encontrado) relacionando una mascota y un tipo, con validación explícita de quién es el usuario creador en base a su autenticación.
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
Listado aplanado y general de los diferentes reportes de la plataforma (Útil para un feed de inicio/mapa general).
- **Response (`200 OK`):** `List<ReporteResponseDTO>`

### `GET /api/v1/reportes/{id}`
Devuelve el detalle del reporte específico.
- **Response (`200 OK`):** `ReporteResponseDTO`

### `PUT /api/v1/reportes/{id}/cerrar`
Marcador de caso "Cerrado" para cuando la mascota vuelve a su hogar. Verifica en DB interna que el reporte sea efectivamente de la persona conectada, de lo contrario lanza excepción de permisos.
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