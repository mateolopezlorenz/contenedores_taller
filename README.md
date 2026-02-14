# Práctica Docker - Fase 3: Pipeline CI/CD

## Descripción
Se ha realizado la práctica del módulo **Despliegue y administración de contenedores**, cuyo objetivo es implementar una pipeline CI/CD para una aplicación Java haciendo uso de **Docker** y **GitHub Actions**. La aplicación que ha sido desarrollada consta de una API REST basada en un **taller de coches**, donde se gestionan las siguientes entidades:
- **Coche** (`coche`): matrícula, marca, modelo.
- **Mecánico** (`mecanico`): nombre, especialidad.
- **Reparación** (`reparacion`): coche, mecánico, fecha, descripción, horas, precio.

### Estructura del proyecto
```
taller_contenedoores/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── checkstyle.xml
├── src/
│   ├── main/
│   │   ├── java/daw2026/taller_contenedores/
│   │   │   ├── model/              # Entidades (Coche, Mecanico, Reparacion)
│   │   │   ├── repository/         # Repositorios 
│   │   │   ├── service/            # Servicios
│   │   │   ├── controller/         # Controladores REST
│   │   │   ├── fixtures/           # Datos de prueba (DataFixtures)
│   │   │   └── TallerContenedooresApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/daw2026/taller_contenedores/
│       │   └── Tests.java
│       └── resources/
│           └── application-test.properties
└── .github/workflows/
    └── ci.yml                      # Pipeline CI/CD con GitHub Actions
```

## Puesta en marcha
### Iniciar la aplicación con Docker Compose
```
cd taller_contenedoores/
mvn clean package -DskipTests
docker compose up -d --build
```

Esto levantara:
- **MariaDB** en el puerto 3306 con la base de datos `tallerdb`.
- **Aplicación Tomcat** en el puerto 8080.
Los datos de prueba (fixtures) se cargan automáticamente al arrancar la aplicación a través de la clase `DataFixtures`, que inserta:
- 5 mecánicos
- 10 coches
- 10 reparaciones

### Parar la aplicación
```
docker compose down -v
```

## Ejecución de tests
Los tests se ejecutan con H2 en memoria (perfil `test`), sin necesidad de base de datos externa:

```
cd taller_contenedoores/
mvn clean test
```

### Ejecución del linting (Checkstyle)
```
cd taller_contenedoores/
mvn checkstyle:check
```

## Endpoints de la API
### Coches
| Método | Endpoint                      | Descripción                    |
|--------|-------------------------------|--------------------------------|
| GET    | `/coches`                     | Listar todos los coches        |
| GET    | `/coches/{id}`                | Obtener coche por ID           |
| GET    | `/coches/matricula/{matricula}` | Obtener coche por matrícula  |
| POST   | `/coches`                     | Crear un nuevo coche           |

### Mecánicos
| Método | Endpoint            | Descripción                       |
|--------|---------------------|-----------------------------------|
| GET    | `/mecanicos`        | Listar todos los mecánicos        |
| GET    | `/mecanicos/{id}`   | Obtener mecánico por ID           |
| POST   | `/mecanicos`        | Crear un nuevo mecánico           |

### Reparaciones
| Método | Endpoint                       | Descripción                             |
|--------|--------------------------------|-----------------------------------------|
| GET    | `/reparaciones`                | Listar todas las reparaciones           |
| GET    | `/reparaciones/{id}`           | Obtener reparación por ID               |
| GET    | `/reparaciones/coche/{id}`     | Reparaciones por coche                  |
| GET    | `/reparaciones/mecanico/{id}`  | Reparaciones por mecánico               |
| POST   | `/reparaciones`                | Crear una nueva reparación              |

### Ejemplos con curl
```bash
# Listar todos los coches
curl http://localhost:8080/coches

# Obtener un coche por ID
curl http://localhost:8080/coches/1

# Obtener un coche por matrícula
curl http://localhost:8080/coches/matricula/1111AAA

# Crear un nuevo coche
curl -X POST http://localhost:8080/coches \
  -H "Content-Type: application/json" \
  -d '{"matricula":"NEW1234","marca":"Tesla","modelo":"Model 3"}'

# Listar todos los mecánicos
curl http://localhost:8080/mecanicos

# Listar todas las reparaciones
curl http://localhost:8080/reparaciones

# Reparaciones de un coche concreto
curl http://localhost:8080/reparaciones/coche/1

# Reparaciones de un mecánico concreto
curl http://localhost:8080/reparaciones/mecanico/1
```

## Pruebas de la API con curl
### Requisitos
- La aplicación debe estar ejecutándose en `http://localhost:8080`
- `curl` instalado

### Flujo de pruebas completo
#### 1. Consultar datos existentes
```bash
# Ver todos los coches
curl -X GET http://localhost:8080/coches

# Ver todos los mecánicos
curl -X GET http://localhost:8080/mecanicos

# Ver todas las reparaciones
curl -X GET http://localhost:8080/reparaciones
```

#### 2. Crear nuevos registros
```bash
# Crear un nuevo coche
curl -X POST http://localhost:8080/coches \
  -H "Content-Type: application/json" \
  -d '{
    "matricula": "9999ZZZ",
    "marca": "Renault",
    "modelo": "Clio"
  }'

# Crear un nuevo mecánico
curl -X POST http://localhost:8080/mecanicos \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Roberto",
    "especialidad": "Electricista"
  }'

# Crear una nueva reparación (requiere IDs existentes de coche y mecánico)
curl -X POST http://localhost:8080/reparaciones \
  -H "Content-Type: application/json" \
  -d '{
    "coche": {"id": 1},
    "mecanico": {"id": 1},
    "fecha": "2024-02-11",
    "descripcion": "Revisión completa",
    "horas": 3,
    "precio": 150.00
  }'
```

#### 3. Consultar por ID
```bash
# Obtener coche específico
curl -X GET http://localhost:8080/coches/1

# Obtener mecánico específico
curl -X GET http://localhost:8080/mecanicos/1

# Obtener reparación específica
curl -X GET http://localhost:8080/reparaciones/1
```

#### 4. Consultas específicas
```bash
# Obtener coche por matrícula
curl -X GET "http://localhost:8080/coches/matricula/1111AAA"

# Obtener reparaciones de un coche
curl -X GET http://localhost:8080/reparaciones/coche/1

# Obtener reparaciones de un mecánico
curl -X GET http://localhost:8080/reparaciones/mecanico/1
```

#### 5. Validación de respuestas
```bash
# Ver código de estado HTTP
curl -w "\nStatus: %{http_code}\n" -X GET http://localhost:8080/coches

# Ver solo los headers
curl -I -X GET http://localhost:8080/coches

# Ver la respuesta completa con códigos de estado
curl -v -X GET http://localhost:8080/coches
```

## Tests
El proyecto incluye **34 tests** organizados en tres categorías:

### Tests unitarios (14 tests)
Verifican el funcionamiento básico de las entidades:
- `testCrearCoche` - Crear una instancia de Coche
- `testSetMatricula` - Establecer matrícula
- `testSetMarcaCoche` - Establecer marca
- `testSetModeloCoche` - Establecer modelo
- `testCocheNoNulo` - Verificar que Coche no es nulo
- `testCrearMecanico` - Crear una instancia de Mecanico
- `testSetNombreMecanico` - Establecer nombre
- `testSetEspecialidad` - Establecer especialidad
- `testMecanicoNoNulo` - Verificar que Mecanico no es nulo
- `testCrearReparacion` - Crear una instancia de Reparacion
- `testSetDescripcionReparacion` - Establecer descripción
- `testSetHorasReparacion` - Establecer horas
- `testSetPrecioReparacion` - Establecer precio
- `testReparacionNoNula` - Verificar que Reparacion no es nula

### Tests de integración (14 tests)
Comprueban el funcionamiento de servicios y repositorios:
**Tests de Coche:**
- `testSaveCocheService` - Guardar coche en servicio
- `testFindAllCochesService` - Buscar todos los coches
- `testUpdateCocheService` - Actualizar coche
- `testSaveCocheRepository` - Guardar coche en repositorio
- `testFindCocheByIdRepository` - Buscar coche por ID

**Tests de Mecanico:**
- `testSaveMecanicoService` - Guardar mecánico en servicio
- `testFindAllMecanicosService` - Buscar todos los mecánicos
- `testSaveMecanicoRepository` - Guardar mecánico en repositorio
- `testFindMecanicoByIdRepository` - Buscar mecánico por ID

**Tests de Reparacion:**
- `testSaveReparacionService` - Guardar reparación en servicio
- `testFindReparacionByIdService` - Buscar reparación por ID
- `testUpdateReparacionService` - Actualizar reparación
- `testSaveReparacionRepository` - Guardar reparación en repositorio
- `testFindReparacionByIdRepository` - Buscar reparación por ID

### Tests de aceptación (6 tests)
Validan los endpoints REST de la aplicación:
- `testGetAllCochesEndpoint` - GET /coches
- `testCreateCocheEndpoint` - POST /coches
- `testGetAllMecanicosEndpoint` - GET /mecanicos
- `testCreateMecanicoEndpoint` - POST /mecanicos
- `testGetAllReparacionesEndpoint` - GET /reparaciones
- `testCreateReparacionEndpoint` - POST /reparaciones

## Fixtures (Datos de prueba)
La clase `DataFixtures` carga automáticamente datos al arrancar:

- **5 mecánicos**: Juan Pérez, Ana Gómez, Luis Martínez, María López, Carlos Fernández.
- **10 coches**: Toyota Corolla, Ford Fiesta, Honda Civic, BMW Serie 3, Audi A4, Mercedes C200, Kia Ceed, Nissan Leaf, Seat Ibiza, Volkswagen Golf.
- **10 reparaciones**: Una por cada coche, asignada a mecánicos de forma rotatoria.

## Dockerfile
El Dockerfile despliega la aplicación WAR sobre **Tomcat 10.1** con JDK 21:
1. Usa `tomcat:10.1.13-jdk21` como imagen base.
2. Elimina las aplicaciones por defecto de Tomcat.
3. Copia el WAR generado por Maven como `ROOT.war`.
4. Expone el puerto 8080.

```bash
# Construir la imagen manualmente
mvn clean package -DskipTests
docker build -t taller-app .
```

## Docker Compose
El `docker-compose.yaml` orquesta dos servicios:
- **db**: MariaDB 11.1 con la base de datos `tallerdb`.
- **app**: La aplicación Spring Boot sobre Tomcat, conectada a la base de datos.

Las variables de entorno se configuran automáticamente la conexión.

## Pipeline CI/CD (GitHub Actions)

El fichero `.github/workflows/ci.yml` define un pipeline automático que se ejecuta en cada **push** o **pull request** a `master`:

### Pasos que sigue el Pipeline:
1. **Checkout code** - Obtiene el código del repositorio
2. **Set up JDK 21** - Configura Java 21
3. **Give execute permission to mvnw** - Permisos de ejecución en Linux
4. **Wait for MariaDB** - Espera a que la base de datos esté lista
5. **Validate code format with Checkstyle** - Valida el formato y la correctez del código
   - Ejecuta `mvn validate` que incluye la verificación de Checkstyle
   - Falla si no se cumple el estilo
6. **Run Tests** - Ejecuta los 34 tests JUnit
   - Tests unitarios
   - Tests de integración
   - Tests de aceptación
   - Usa H2 en memoria (perfil `test`)
7. **Package application** - Empaqueta la aplicación como un WAR
8. **Build Docker image** - Construye la imagen Docker

### Configuración de Checkstyle
El fichero `checkstyle.xml` define las siguientes reglas de validación:
- Longitud máxima de línea: 120 caracteres
- Nombre correcto de clases, métodos y variables
- Espacios en blanco congruentes
- Ausencia de imports innecesarios
- Longitud máxima de métodos: 150 líneas

## Conclusión
Con esta práctica he aprendido:
- Como implementar una aplicación java con API REST, haciendo uso de Spring Boot con 3 entidades.
- Configuración de MariaDB y Tomcat (haciendo uso de la aplicación).
- Creación de varios tipos de tests.
- Integración de checkstile para poder validar el código.
- Cargar datos de prueba en la base de datos al arrancar la aplicación.
