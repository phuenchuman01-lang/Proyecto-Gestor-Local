<div align = "center">
  <img width="500" height="170" alt="Ufrodark" src="https://github.com/user-attachments/assets/19dee791-9a5a-473b-a55f-6a091a1b6298" />
</div>

# Proyecto Local Gestor

Este repositorio contiene el proyecto en equipo realizado en la asignatura de **Programacion Orientada a Objetos (POO)** en la **Universidad de la Frontera (UFRO)**. una app para la **gestion de inventario** para casilleros. El funcionamiento principal de nuestra app Local Gestor es que los estudiantes puedan registrarse a una cuenta con nombre y contraseña, asignar su casillero correspondiente, y organizar su inventario personal. con el apoyo de los creadores y funcionarios. El proyecto aun sigue en desarrollo...

> **Version:** Beta  
> **Status:** Done

### Integrantes

| Nombre | Rol en el equipo |
|---|---|
| [Katherine Llanquinao Gallardo](https://github.com/Katherine23015) | GUI+estructura |
| [Luis Arias Quezada](https://github.com/Lgarias11) | Logica+tester |
| [Patricio Huenchuman Calful](https://github.com/phuenchuman01-lang) | Vistas+navegabilidad |

> [!NOTE]
> El programa esta habilitado para usar, activando el Launcher dentro de la carpeta src/main/java/launcher

---

### How To Use

Al iniciar el programa, se te pedira un usuario y una contraseña la cual debes crear para ingresar a la ventana principal. Hay una distincion entre un usuario Alumno y Admin.

> [!CAUTION]
> Todas las cuentas creadas en el Login son consideradas Usuario **ALUMNO**, solo **existe una unica cuenta ADMIN** con una contraseña fija.

### Roles de usuario
 
| Rol | Permisos |
|---|---|
| `Admin` | Gestionar todos los casilleros disponibles, añadir o borrar objetos, enlazar con otros usuarios Alumno y tener privilegio de edicion libre de cada casillero. |
| `Alumno` | Asignar y gestionar un unico casillero bajo su nombre y responsabilidad. |

---

## Desarrollo Avance 1
**Resumen:** Primera version basada en ventanas y navegabilidad de Interfaz Grafica de Usuario [GUI].
* Implementacion de ventanas y botones utiles para el manejo de la interfaz de usuario dependiendo de su rol dentro del proyecto [>main.controlador.controlador.vista + Launcher.java], tambien la creacion del archivo main.controlador.controlador.launcher. Desarrollo realizado por Patricio.   
* Aplicacion de logica para distincion de usuarios [Usuario.java], diferenciar entre un usuario alumno o un usuario docente para dirigir a su respectiva ventana de menu, y creacion de cajas de texto para insertar nombre y contraseña a una nueva cuenta. Desarrollo realizado por Katherine.   
* Agregacion de clausulas para el control de sesiones [SessionController.java], cuya funcion prohibe ciertas caracteristics a la hora de crear una cuenta. como un nombre y contraseña vacios, y crear un nuevo alumno con cada registro de cuenta. Desarrollo realizado por Luis.   

## Desarrollo avance 2  
**Resumen:** Aplicacion de logica y navegabilidad para que el programa cumpla con su proposito de gestionar casilleros para los usuarios Alumno.
* Aplicacion de logica, conexion y mejora de navegacion dentro del programa. Donde ahora se cumple con el proposito de cada rol Estudiante o Admin, asignar y gestionar 1 entre 10 casilleros disponibles, ingresar objetos y mantener persistencia en los datos del casillero asignado, su usuario relacionado, y los objetos que contiene en un Json.
* Aplicacion de conceptos de Herencia, Diagramas UML aprendidos en clase.
* Cambio a entorno Maven.

## Desarrollo Avance 3

coming soon...
