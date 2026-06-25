<div align = "center">
  <img width="500" height="170" alt="Ufrodark" src="https://github.com/user-attachments/assets/19dee791-9a5a-473b-a55f-6a091a1b6298" />
</div>

# Proyecto Local Gestor

Este repositorio contiene el proyecto en equipo realizado en la asignatura de Programacion Orientada a Objetos (POO) en la Universidad de la Frontera (UFRO). una App para el manejo y distribucion de inventario para casilleros. El funcionamiento principal de nuestra app Local Gestor es que los estudiantes puedan registrarse a una cuenta con nombre y contraseña, asignar su casillero correspondiente, y organizar su inventario personal. con el apoyo de los creadores y funcionarios. El proyecto aun sigue en desarrollo...

> **Version:** Beta  
> **Status:** Done

### Integrantes
- [Katherine Llanquinao Gallardo](https://github.com/Katherine23015)
- [Luis Arias Quezada](https://github.com/Lgarias11)
- [Patricio Huenchuman Calful](https://github.com/phuenchuman01-lang)

> [!NOTE]
> El programa esta habilitado para usar, activando el Launcher dentro de src/


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
