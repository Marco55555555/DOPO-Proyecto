# Proyecto: The Silk Road ... with Robots!

Este proyecto está basado en el **Problema J del ICPC 2024**, *The Silk Road … with Robots!*  
El objetivo es construir paso a paso un simulador y luego una solución completa del problema, siguiendo el esquema de **ciclos de desarrollo** propuestos en la asignatura de Desarrollo Orientado por Objetos (DOPO-POOB).

---

##  Descripción del problema original
En la Ruta de la Seda moderna, tenemos **robots** y **tiendas** con cantidades de *tenges* (moneda de Kazajistán).  

- Un robot puede moverse a una tienda y recolectar todo su dinero.  
- Mover un robot cuesta **1 tenge por cada metro recorrido**.  
- La **ganancia** se calcula como:  ganancia = dinero_tienda - distancia_robot_tienda 
- Cada día aparece **un robot** o **una tienda** en una nueva posición.  
- Al inicio de cada día:  
- Los robots regresan a su posición inicial.  
- Las tiendas se reabastecen con la misma cantidad de dinero inicial.  

 La tarea es calcular, para cada día, el **máximo beneficio posible** que se puede obtener al mover robots hacia las tiendas.

---

##  Objetivo del proyecto
Construir una aplicación que permita **simular y resolver** el problema, en varias fases llamadas **ciclos de desarrollo**.

---

##  Ciclos de desarrollo

###  Ciclo 1 – Simulador inicial
Los mini-ciclos definidos corresponden a los requisitos planteados en la guía:  
1.	Create – Crear la estructura inicial del sistema.  
2.	Add / remove store or resupply stores – Administración de tiendas.  
3.	Add / delete robot or return robots – Administración de robots.  
4.	Move robot – Movimiento de los robots en la espiral.  
5.	Reboot – Reinicio del sistema.  
6.	Consult profit – Consulta de ganancias.  
7.	Consult silk road – Consulta del estado de la Ruta de la Seda.  
8.	Make visible / invisible – Manejo de la visibilidad de elementos.  
9.	Finish – Finalización del sistema.  

---

###  Ciclo 2 – Refactorización y extensión
Los mini-ciclos definidos corresponden a los nuevos requisitos planteados en la guía:  
1.	Create extensión – Construcción de la Ruta de la Seda a partir de la entrada del problema de la maratón.  
2.	Move robot extensión – Decisión de movimientos de los robots buscando maximizar la ganancia.  
3.	Consult extensión (stores) – Consulta del número de veces que cada tienda ha sido desocupada.  
4.	Consult extensión (robots) – Consulta de las ganancias que ha logrado cada robot en cada movimiento.  

---

###  Ciclo 3 – Solución final del problema
Los mini-ciclos definidos corresponden a los nuevos requisitos planteados en la guía:  
1.	Solve – Resolver el problema de la maratón (cálculo de la máxima utilidad diaria).  
2.	Simulate – Simular la solución obtenida para visualizar el comportamiento en la Ruta de la Seda.  

---

##  Entregables por ciclo
- Diseño en **Astah**.  
- Código en **Java** siguiendo estándares de documentación.  
- Casos de prueba unitarios (ej: `SilkRoadTest`, `SilkRoadC2Test`, `SilkRoadContestTest`).  
- Documento de retrospectiva del ciclo.  

---
##  Autores
Proyecto desarrollado por:  
- **Marco Jiménez**  
- **Santiago Ruiz**  

Materia: **DOPO – Grupo 4**  


