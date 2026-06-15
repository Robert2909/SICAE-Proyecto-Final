# Conclusión de la Fase 4 - Integrante 4

Ya quedó funcional y completamente integrado el microservicio de estacionamiento.

## Detalles del Servicio
- **Servicio:** ParkingService
- **Puerto:** 8084
- **Base de datos:** MySQL - sicaeparking
- **Ruta base:** `http://localhost:8084/parking`

---

## Autenticación e Integración
Todas las peticiones a este microservicio están protegidas y **requieren el token de AuthService**.
Formato en los Headers:
`Authorization: Bearer <token>`

Este microservicio actúa como el orquestador final. Al recibir una petición, extrae el token y utiliza `RestTemplate` (nativo de Spring, sin librerías externas) para consultar los puertos `8082` (UserService) y `8083` (VehicleService). Si el token expiró, si el usuario no existe o si el vehículo no le pertenece, la petición es rechazada de inmediato.

---

## Endpoints Disponibles

**1. Registrar Entrada:**
`POST http://localhost:8084/parking/entrada`
**Body:**
```json
{
  "idUsuario": 2,
  "placa": "ABC1234"
}
```

**2. Registrar Salida y Calcular Costo:**
`POST http://localhost:8084/parking/salida/{placa}`
*(No requiere body, la placa viaja en la URL).*

---

## Reglas de Negocio Implementadas y Probadas

1. **Disponibilidad:** Se valida contra la tabla de `configuracion` si hay cajones disponibles.
2. **Límite por usuario:** Un mismo usuario NO puede tener más de 2 vehículos adentro al mismo tiempo.
3. **Doble entrada:** Un vehículo que ya tiene el estatus "Adentro" no puede volver a registrar entrada.
4. **Cálculo de costos:** Se utiliza matemática pura y la clase `Duration` de Java para calcular las horas y los minutos exactos transcurridos entre la fecha de entrada y de salida, cobrando en base a las tarifas establecidas en la base de datos.
5. **Sanitización:** Todo el código está comentado paso a paso, explicando la lógica. No se utilizaron librerías "mágicas" ni atajos, respetando la estructura vista en clase.