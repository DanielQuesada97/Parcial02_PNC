# 📚 API de Gestión de Biblioteca

Este proyecto es una API desarrollada en Java que permite gestionar una biblioteca con control de usuarios y autenticación.

## 👥 Autores

- **Jaime Samuel Portan Rivas** – 00046119  
- **Daniel Alfredo Quesada Cortez** – 00147619

---

## 🚀 Instrucciones para Ejecutar el Proyecto

1. **Credenciales de Acceso:**
   - Usuario: `admin`
   - Contraseña: `admin`

2. **Configuración Inicial:**
   Ejecuta la siguiente sentencia SQL para registrar el rol de usuario por defecto (ya que no logramos solucionar dicho error):

   ```sql
   INSERT INTO roles (id, name, description, created_at) 
   VALUES (uuid_generate_v4(), 'ROLE_USER', 'Default user role', CURRENT_TIMESTAMP);
   ```

3. **Base de Datos:**
   - Utiliza **PostgreSQL**
   - Se recomienda usar [DBngin](https://dbngin.com/) para una mejor experiencia en la gestión de bases de datos locales.

---

## ⚙️ Tecnologías y Dependencias Utilizadas

- **Java 23**
- **Spring Boot** con los siguientes módulos:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-security`
- **Lombok** – para simplificar código con anotaciones
- **Base de datos:** PostgreSQL
- **Gestión de dependencias:** Maven o Gradle

---

## 🔐 Seguridad y Roles

La API utiliza **autenticación basada en roles**.  
Hay dos tipos de usuarios:

- 👤 **Usuario (ROLE_USER):**  
  - Solo puede visualizar datos.
- 🛠️ **Administrador (ROLE_ADMIN):**  
  - Puede **agregar**, **editar** y **eliminar** información.

La autenticación es obligatoria para consumir los endpoints protegidos (todos por el momento).

---
