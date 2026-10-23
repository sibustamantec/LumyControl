# LumyControl - Proyecto de Gestión Veterinaria

**Universidad Nacional de Colombia - Programación Orientada a Objetos**

LumyControl es una aplicación de consola escrita en **Java** que ofrece a las veterinarias una herramienta digital para llevar el control de los aspectos principales del negocio: pacientes, historias clínicas, consultas, facturación, inventario y cierre diario.

> **Estado del proyecto:** fase temprana de desarrollo (Primera Entrega)

## Tabla de contenidos

- [Equipo](#equipo)
- [Funcionalidades](#funcionalidades)
- [Reglas de negocio](#reglas-de-negocio)
- [Tecnologías](#tecnologías)
- [Requisitos previos](#requisitos-previos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Guía de uso](#guía-de-uso)

## Equipo

| Nombre | Rol | GitHub |
|---|---|---|
| Sindy Tatiana Bustamante Cardenas | Desarrolladora | sibustamantec |
| Joel Emmanuel García Góngora | Dearrollador | JGarcia734 |
| Marlon Steven Montes Arguello | Presente | mmontesar |
| Andrey Santiago García Perilla | Desarrollador | AndGarcia-Unal | 
| Manuel Felipe Reyes Gutiérrez | Desarrollador | mareyesgu |

## Funcionalidades

- **Pacientes:** registro de tutores (propietarios) y de sus mascotas, con datos como especie, raza, sexo, color, peso, microchip, alimentación y alergias.
- **Consultas:** registro de anamnesis, examen clínico por sistemas, diagnóstico y tratamiento.
- **Historia clínica:** consulta en versión resumida o completa para cada paciente.
- **Alertas clínicas:** aviso automático de fiebre o constantes vitales fuera de rango, y de vacunación o desparasitación pendientes.
- **Recibos:** generación de recibos con servicios y productos, y registro del pago.
- **Programa de fidelidad:** los tutores acumulan puntos con cada pago y obtienen descuentos.
- **Inventario:** control de productos de venta e insumos de la clínica, con reabastecimiento y alerta de stock bajo.
- **Catálogo de servicios:** definición de los servicios que ofrece la clínica y su costo base.
- **Cierre del día:** resumen de consultas, pacientes atendidos, ventas, utilidad, ingresos, insumos gastados y productos con stock bajo.

## Reglas de negocio

| Regla | Detalle |
|---|---|
| Fiebre | Temperatura mayor a 39.2 °C |
| Constantes vitales (canino) | FC: 60-150 lpm · FR: 10-35 rpm |
| Constantes vitales (felino) | FC: 100-240 lpm · FR: 12-40 rpm |
| Vacunación | Se requiere si no hay registro o pasó más de 1 año |
| Desparasitación | Se requiere si no hay registro o pasaron más de 3 meses |
| Recargo nocturno | 20 % sobre los servicios en consultas entre las 20:00 y las 06:00 |
| Puntos de fidelidad | 1 punto por cada $2.000 del costo base, al pagar el recibo |
| Descuento por fidelidad | 5 % (50-99 puntos), 10 % (100-199), 15 % (200 o más) |
| Stock bajo | Cuando la cantidad es menor o igual al stock mínimo |

## Tecnologías

- **Lenguaje:** Java 21
- **Paradigma:** Programación Orientada a Objetos
- **Interfaz:** consola (menú interactivo)
- **IDE utilizado:** IntelliJ IDEA

## Requisitos previos

- Git

- JDK 21 o superior

## Instalación y ejecución

```bash
# 1. Clonar el repositorio
git clone https://github.com/sibustamantec/LumyControl.git
cd LumyControl/src

# 2. Compilar
javac *.java

# 3. Ejecutar
java Main
```

## Estructura del proyecto

```
LumyControl/
├── src/
│   ├── Main.java            # Menú y lógica de interacción con el usuario
│   ├── Clinica.java         # Clase central: tutores, mascotas, inventario, recibos y cierre
│   ├── Tutor.java           # Propietario de las mascotas y puntos de fidelidad
│   ├── Mascota.java         # Paciente
│   ├── HistoriaClinica.java # Historial de consultas de una mascota
│   ├── ConsultaGeneral.java # Consulta médica
│   ├── Anamnesis.java       # Datos reportados por el tutor
│   ├── ExamenClinico.java   # Constantes vitales y examen por sistemas
│   ├── Servicio.java        # Procedimiento del catálogo
│   ├── Producto.java        # Producto de venta o insumo
│   ├── LineaProducto.java   # Producto y cantidad dentro de un recibo
│   └── Recibo.java          # Facturación, recargos, descuentos y pago
├── .gitignore
└── README.md
```

## Guía de uso

Al iniciar, el programa muestra el siguiente menú:

```
----------- MENÚ PRINCIPAL -----------
 1. Registrar paciente nuevo (tutor + mascota)
 2. Nueva consulta (historia clínica + recibo)
 3. Ver historia clínica
 4. Recibos (ver / agregar / pagar)
 5. Inventario de productos
 6. Registrar servicio en el catálogo
 7. Registrar gasto de insumo
 8. Consultar tutor y sus mascotas
 9. Cierre del día
10. Iniciar nuevo día
 0. Salir
--------------------------------------
```

**Flujo recomendado la primera vez:**

1. **Opción 6:** registrar al menos un servicio en el catálogo (ej. "Consulta general").
2. **Opción 5:** registrar productos o insumos en el inventario (opcional).
3. **Opción 1:** registrar un tutor y su mascota.
4. **Opción 2:** crear una consulta. Se pide anamnesis, examen clínico, diagnóstico y tratamiento; luego se agregan servicios y productos, y se genera el recibo.
5. **Opción 4:** consultar o pagar los recibos pendientes.
6. **Opción 9:** ver el cierre del día y, después, **opción 10** para iniciar un nuevo día.

**Notas:**
- Las mascotas se buscan por **microchip o nombre**.
- Los tutores se identifican por su **documento de identidad**.
- Las fechas se ingresan en formato `aaaa-mm-dd`, y fecha y hora en `aaaa-mm-dd HH:mm`.
