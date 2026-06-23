<img width="389" height="141" alt="image" src="https://github.com/user-attachments/assets/ead7f30b-e5f9-4dac-8fe2-64e4bafa98a1" />


# Proyecto Local Gestor - Rama: dev-kathy/casillas-seguras

Este repositorio contiene el avance y desarrollo de la rama enfocada en la gestión segura de casilleros, realizada para la asignatura de **Programación Orientada a Objetos (POO)** en la **Universidad de la Frontera (UFRO)**. El propósito de este módulo es robustecer la lógica de negocio, el registro de usuarios y garantizar la integridad de los datos de los estudiantes y sus inventarios.

## Integrantes
* Katherine Llanquinao Gallardo
* Luis Arias Quezada
* Patricio Huenchuman Calful

## 🛠️ Desarrollo de la Rama (Avance Actual)

* **Modelado y Lógica de Usuario (`Usuario.java`):** Implementación y optimización de la clase base `Usuario`. Se refinaron los atributos de seguridad (nombre y contraseña) y se estructuró el comportamiento para diferenciar correctamente los roles de *Estudiante* y *Administrador*.
* **Control de Negocio e Inventario:** Desarrollo de métodos específicos en el `Main` y controladores para asociar dinámicamente un estudiante a un casillero único, validando que las credenciales no sean nulas o vacías mediante cláusulas de guarda.
* **Persistencia de Datos (JSON):** Integración de lógica para guardar el estado actual de los casilleros y sus respectivos objetos asignados, permitiendo mantener la información guardada de manera local entre ejecuciones del programa.

### Resumen Técnico del Avance
Aplicación estricta de principios **POO** (Herencia, Encapsulación y Asociación), migración y orden del entorno mediante **Maven**, y gestión limpia del repositorio a través de commits atómicos en esta rama.

---

## Próximos Pasos 
- [ ] Finalizar las pruebas unitarias para el inicio de sesión.
- [ ] Validar la persistencia cuando se limpian los casilleros por completo.
- [ ] Realizar el Merge limpio hacia la rama `main` tras la aprobación del equipo.
