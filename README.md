# 🔒 Casillas-Seguras: Sistema de Gestión de Casilleros Universitarios

Proyecto de desarrollo de software enfocado en resolver la problemática de asignación y control de inventario de los casilleros estudiantiles dentro del campus de la **Universidad de la Frontera (UFRO)**. 

---

## 📋 Requisitos del Avance 02

Este repositorio contiene el prototipo funcional correspondiente al **Avance 02**, cumpliendo con los siguientes lineamientos de diseño:
- **Modelo de Dominio Robusto:** Implementación lógica encapsulada de entidades del mundo real.
- **Estructura Estándar de la Industria:** Organización limpia bajo el árbol de fuentes `src/main/java/modelo`.
- **Preparación Arquitectónica:** Código desacoplado listo para la integración de Interfaces Gráficas de Usuario (GUI) y persistencia de datos (Archivos locales).

---
## Descripción del Modelo de Clases
1. Usuario
Modela a los estudiantes registrados en la institución facultados para solicitar el uso de casillas seguros.
- Atributos: rut (String), nombre (String), correo (String), carrera (String).
- Encapsulamiento: Todos los atributos son privados (private) con acceso controlado mediante métodos Getters y Setters.

2. Casillero
Representa los casilleros físicos distribuidos en la universidad.
- Atributos: idCasillero (String), estado (String: Disponible, Ocupado, Mantenimiento), inventario (List).
- Relación: Mantiene una relación de Asociación/Agregación con la clase ObjetoInventario para monitorear qué elementos contiene en tiempo real de forma dinámica.
  
3. ObjetoInventario
Clase que permite declarar y categorizar los artículos individuales que introduce un alumno para resguardo.
- Atributos: idObjeto (String), descripcion (String), categoria (String).
  
4. AsignacionCasillero
Clase de relación que actúa como registro o contrato entre un estudiante y un espacio físico específico.
- Atributos: estudiante (Usuario), casillero (Casillero), fechaAsignacion (LocalDate).
- Lógica de Negocio: Al instanciarse, modifica de forma automática el estado del casillero vinculado a "Ocupado".

## 🏗️ Estructura del Proyecto

La arquitectura del código fuente sigue la jerarquía recomendada para proyectos Java estructurados:

```text
src/
└── main/
    └── java/
        ├── modelo/                 # Paquete principal del dominio
        │   ├── Usuario.java             # Entidad del estudiante universitario
        │   ├── Casillero.java           # Control de estado y capacidad física
        │   ├── ObjetoInventario.java    # Gestión de pertenencias guardadas
        │   └── AsignacionCasillero.java # Registro transaccional de uso
        └── Main.java               # Punto de entrada y simulación del sistema

