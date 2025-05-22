# 🏫 API de Gestión Escolar

Este es un proyecto evaluativo de la disciplina **Programación Orientada a Objetos en Java**, con el objetivo de desarrollar una API REST utilizando Spring Boot para la gestión de un sistema escolar.

## 🎯 Objetivo

Permitir que alumnos puedan programar entrevistas con profesores, que los profesores puedan visualizar sus horarios, y que la institución educativa gestione la disponibilidad de horarios y asignaturas.

---

## ⚙️ Tecnologías Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Banco de Datos (PostgreSQL)
- Maven

---

## 🗃️ Entidades Principales

- **Alumno**: nombre, numeroEstudiante, email, teléfono.
- **Profesor**: nombre, identificacionProfesor, materiaId.
- **Materia**: nombre.
- **Entrevista**: alumno, profesor, fechaHora, status, observaciones.
- **DisponibilidadProfesor**: profesor, diaDeSemana, horarioInicio, horarioFin.

---

## 🔐 Reglas de Negocio

- La programación de entrevistas solo se permite:
  - Si el profesor está disponible en ese horario.
  - Con fecha/hora futura.
- Las cancelaciones de entrevistas solo se aceptan con **un mínimo de 24 horas de anticipación**.
- Un profesor no puede ser eliminado si tiene entrevistas futuras programadas.
- Un alumno no puede ser eliminado si tiene entrevistas programadas.

---

## 🔁 Endpoints Principales

### 📌 Alumnos

- `POST /api/alumnos` – Registrar un nuevo alumno.
- `GET /api/alumnos` – Listar todos los alumnos.
- `GET /api/alumnos/{id}` – Buscar un alumno por su ID.
- `PUT /api/alumnos/{id}` – Actualizar la información de un alumno (si se implementa).
- `DELETE /api/alumnos/{id}` – Eliminar un alumno.

### 🧑‍🏫 Profesores

- `POST /api/profesores` – Registrar un nuevo profesor.
- `GET /api/profesores` – Listar todos los profesores (filtros por materia/disponibilidad).
- `GET /api/profesores/{id}` – Buscar un profesor por su ID.
- `PUT /api/profesores/{id}` – Actualizar la información de un profesor (si se implementa).
- `DELETE /api/profesores/{id}` – Eliminar un profesor.

### 📅 Entrevistas

- `POST /api/entrevistas` – Programar una nueva entrevista.
- `GET /api/entrevistas` – Listar entrevistas (filtros por profesor, alumno, estado).
- `GET /api/entrevistas/{id}` – Buscar una entrevista por su ID.
- `PATCH /api/entrevistas/{id}/status` – Actualizar el estado de una entrevista.
- `DELETE /api/entrevistas/{id}` – Cancelar una entrevista.

### 📚 Materias

- `POST /api/materias` – Registrar una nueva materia.
- `GET /api/materias` – Listar todas las materias.
- `GET /api/materias/{id}` – Buscar una materia por su ID.
- `PUT /api/materias/{id}` – Actualizar una materia (si se implementa).
- `DELETE /api/materias/{id}` – Eliminar una materia.

### ⏰ Disponibilidad de Profesores

- `POST /api/profesores/{profesorId}/disponibilidades` – Registrar la disponibilidad de un profesor.
- `GET /api/profesores/{profesorId}/disponibilidades` – Listar las disponibilidades de un profesor.
- `DELETE /api/disponibilidades/{id}` – Eliminar un registro de disponibilidad.

---

## 🧪 Cómo Ejecutar el Proyecto

1. Clona el repositorio:

```bash
  git clone https://github.com/gbrogio/api-agendamento-consultas-medicas 
```
(Nota: El enlace del repositorio original se mantiene, se asume que el código fuente está en este mismo repositorio.)

2. Accede a la carpeta del proyecto:

```bash
  cd nombre-del-directorio-del-proyecto 
```
(Reemplaza `nombre-del-directorio-del-proyecto` con el nombre real del directorio si es diferente)

3. (OPCIONAL) Si tienes Docker, ejecuta la base de datos:
```bash
  docker-compose up
```

o crea una base de datos PostgreSQL con las siguientes propiedades:
USUARIO: postgres
CONTRASEÑA: password
NOMBRE DE LA BASE DE DATOS: gestionEscolar

4. Compila y ejecuta el proyecto con Maven:

```bash
  mvn spring-boot:run
```

5. Accede a la documentación de Swagger en:

```bash
  http://localhost:8080/docs
```

## 👨‍🏫 Información Académica

**Disciplina:** Programación Orientada a Objetos en Java\
**Proyecto Avaliativo:** API para Gestión Escolar\
**Alunos:** Antônio Neto, Guilherme Brogio, Lucas Gabriel, Matheus Guilherme, Leonardo Ribeiro\
**Professor:** Fabrício
