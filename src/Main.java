import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Clinica clinica = new Clinica();
    private static final HashMap<Mascota, HistoriaClinica> historias = new HashMap<>();
    private static int contadorConsultas = 1;
    private static int contadorRecibos = 1;

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN VETERINARIA ===");

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> registrarPacienteNuevo();
                case 2 -> nuevaConsulta();
                case 3 -> verHistoriaClinica();
                case 4 -> gestionarRecibos();
                case 5 -> menuInventario();
                case 6 -> registrarServicioCatalogo();
                case 7 -> registrarGastoInsumo();
                case 8 -> consultarTutor();
                case 9 -> System.out.println("\n" + clinica.generarCierreDelDia());
                case 10 -> iniciarNuevoDia();
                case 0 -> System.out.println("Hasta pronto.");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("""

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
                --------------------------------------""");
    }


    private static void registrarPacienteNuevo() {
        System.out.println("\n--- Datos del tutor ---");
        String documento = leerObligatorio("Documento de identidad: ");
        Tutor tutor = clinica.buscarTutor(documento);
        if (tutor == null) {
            String nombre = leerObligatorio("Nombre: ");
            String direccion = leer("Dirección de residencia: ");
            String correo = leer("Correo electrónico: ");
            String telefono = leer("Teléfono: ");
            tutor = new Tutor(nombre, direccion, correo, telefono, documento, 0);
            clinica.registrarTutor(tutor);
            System.out.println("Tutor registrado.");
        } else {
            System.out.println("El tutor ya está registrado: " + tutor.getNombre());
        }

        System.out.println("\n--- Datos del paciente ---");
        Mascota m = pedirMascota(tutor);
        if (clinica.registrarMascota(m)) {
            historias.put(m, new HistoriaClinica(m));
            System.out.println("Paciente registrado:\n" + m.ficha());
        }
    }

    private static Mascota pedirMascota(Tutor tutor) {
        String nombre = leerObligatorio("Nombre: ");
        String fechaNac = leerFecha("Fecha de nacimiento", false);
        String especie = pedirEspecie();
        String raza = leer("Raza: ");
        String sexo = leer("Sexo (Macho/Hembra): ");
        String color = leer("Color: ");
        String temperamento = leer("Temperamento: ");
        double peso = leerDoublePositivo("Peso (kg): ");
        String estRepro = leer("Estado reproductivo (Entero/Esterilizado): ");
        String microchip = leer("Número de microchip (Enter si no tiene): ");
        String alimentacion = leer("Alimentación: ");

        Mascota m = new Mascota(tutor, nombre, raza, temperamento, fechaNac, sexo,
                alimentacion, especie, color, estRepro, microchip, peso);

        String alergias = leer("Alergias (separadas por coma, Enter si no tiene): ");
        if (!alergias.isEmpty()) {
            for (String a : alergias.split(",")) {
                m.agregarAlergia(a.trim());
            }
        }
        return m;
    }

    private static String pedirEspecie() {
        System.out.println("Especie: 1. Canino  2. Felino  3. Otra");
        int op = leerEntero("Opción: ");
        if (op == 1) return "Canino";
        if (op == 2) return "Felino";
        return leerObligatorio("¿Cuál especie?: ");
    }


    private static void nuevaConsulta() {
        Mascota m = seleccionarMascota();
        if (m == null) return;

        HistoriaClinica historia = historias.get(m);
        if (historia == null) {
            historia = new HistoriaClinica(m);
            historias.put(m, historia);
        }

        System.out.println("\n--- Nueva consulta para " + m.getNombre() + " ---");
        String numero = String.format("C-%04d", contadorConsultas);
        LocalDateTime fechaHora = LocalDateTime.now();
        if (!leerSiNo("¿Usar fecha y hora actuales?")) {
            fechaHora = leerFechaHora();
        }
        String veterinario = leerObligatorio("Veterinario: ");

        System.out.println("\n-- Anamnesis --");
        Anamnesis anamnesis = pedirAnamnesis(m);

        System.out.println("\n-- Examen clínico --");
        ExamenClinico examen = pedirExamen();
        m.setPeso(examen.getPeso());

        System.out.println("\n-- Diagnóstico y tratamiento --");
        String diagnostico = leer("Diagnóstico: ");
        String tratamiento = leer("Tratamiento: ");

        ConsultaGeneral consulta = new ConsultaGeneral(numero, fechaHora, veterinario,
                anamnesis, examen, diagnostico, tratamiento);
        if (!clinica.registrarConsulta(consulta)) return;
        contadorConsultas++;
        historia.agregarConsulta(consulta);

        System.out.println();
        if (consulta.hayAlerta(m.getEspecie())) {
            System.out.println("⚠ ALERTA: fiebre o constantes vitales fuera del rango normal.");
        }
        if (anamnesis.requiereVacunacion()) {
            System.out.println("⚠ El paciente requiere vacunación.");
        }
        if (anamnesis.requiereDesparacitacion()) {
            System.out.println("⚠ El paciente requiere desparasitación.");
        }
        if (consulta.esNocturno()) {
            System.out.println("ℹ Consulta en horario nocturno: se aplicará recargo del 20% a los servicios.");
        }

        System.out.println("\n-- Servicios realizados --");
        agregarServiciosAConsulta(consulta, veterinario);

        Recibo recibo = consulta.generarRecibo(String.format("R-%04d", contadorRecibos), m.getTutor(), m);
        contadorRecibos++;
        clinica.registrarRecibo(recibo);

        if (leerSiNo("¿Se vendieron productos en esta consulta?")) {
            venderProductos(recibo);
        }

        System.out.println("\n" + recibo.imprimirRecibo());
        if (leerSiNo("¿Pagar el recibo ahora?")) {
            pagarRecibo(recibo);
        } else {
            System.out.println("Recibo " + recibo.getNumeroRecibo() + " queda pendiente de pago.");
        }
    }

    private static Anamnesis pedirAnamnesis(Mascota m) {
        String motivo = leerObligatorio("Motivo de consulta: ");
        String alimento = leerConDefecto("Tipo de alimento", m.getAlimentacion());
        String racion = leer("Ración: ");
        String heces = leerConDefecto("Heces", "Normales");
        String orina = leerConDefecto("Orina", "Normal");
        String vacuna = leerFecha("Última vacunación", true);
        String desparasitacion = leerFecha("Última desparasitación", true);
        return new Anamnesis(motivo, alimento, racion, heces, orina, vacuna, desparasitacion);
    }

    private static ExamenClinico pedirExamen() {
        double temperatura = leerDoublePositivo("Temperatura (°C): ");
        double peso = leerDoublePositivo("Peso (kg): ");
        int fc = leerEntero("Frecuencia cardíaca (lpm): ");
        int fr = leerEntero("Frecuencia respiratoria (rpm): ");
        String actitud = leerConDefecto("Actitud", "Alerta");

        String[] sistemas = {"Órganos de los sentidos", "Ganglios linfáticos", "Sistema respiratorio",
                "Sistema cardiovascular", "Sistema musculoesquelético", "Sistema digestivo",
                "Sistema neurológico", "Piel y pelaje", "Sistema reproductivo", "Sistema urinario"};
        String[] valores = new String[sistemas.length];
        boolean detallado = leerSiNo("¿Registrar el examen por sistemas? (si no, todo queda 'Sin alteraciones')");
        for (int i = 0; i < sistemas.length; i++) {
            valores[i] = detallado ? leerConDefecto(sistemas[i], "Sin alteraciones") : "Sin alteraciones";
        }
        return new ExamenClinico(temperatura, peso, fc, fr, actitud,
                valores[0], valores[1], valores[2], valores[3], valores[4],
                valores[5], valores[6], valores[7], valores[8], valores[9]);
    }

    private static void agregarServiciosAConsulta(ConsultaGeneral consulta, String veterinario) {
        ArrayList<Servicio> catalogo = clinica.getServiciosDisponibles();
        if (catalogo.isEmpty()) {
            System.out.println("No hay servicios en el catálogo (opción 6 del menú).");
            return;
        }
        while (true) {
            Servicio s = elegirServicio(catalogo, veterinario);
            if (s == null) break;
            String resultado = leer("Resultado (Enter para omitir): ");
            if (!resultado.isEmpty()) s.setResultado(resultado);
            consulta.agregarServicio(s);
            System.out.println("Agregado: " + s.getTipoProcedimiento());
        }
    }

    private static Servicio elegirServicio(ArrayList<Servicio> catalogo, String veterinario) {
        System.out.println("Catálogo de servicios:");
        for (int i = 0; i < catalogo.size(); i++) {
            Servicio s = catalogo.get(i);
            System.out.println("  " + (i + 1) + ". " + s.getTipoProcedimiento() + " - $" + s.getCostoBase());
        }
        int op = leerEntero("Número de servicio (0 para terminar): ");
        if (op == 0) return null;
        if (op < 0 || op > catalogo.size()) {
            System.out.println("Opción inválida.");
            return elegirServicio(catalogo, veterinario);
        }
        return catalogo.get(op - 1).copiar(veterinario);
    }

    private static void venderProductos(Recibo recibo) {
        if (recibo.getPagado()) {
            System.out.println("El recibo ya está pagado, no se pueden agregar productos.");
            return;
        }
        listarProductos(Producto.Tipo.VENTA);
        while (true) {
            String codigo = leer("Código del producto (Enter para terminar): ");
            if (codigo.isEmpty()) break;
            Producto p = clinica.buscarProducto(codigo);
            if (p == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }
            if (p.getTipo() != Producto.Tipo.VENTA) {
                System.out.println("Ese producto es un insumo y no se vende.");
                continue;
            }
            int cantidad = leerEntero("Cantidad: ");
            if (p.reducirCantidad(cantidad)) { // descuenta del inventario
                recibo.agregarLineaProducto(new LineaProducto(p, cantidad));
                System.out.println("Agregado: " + p.getNombre() + " x" + cantidad);
                if (p.verificarStockBajo()) {
                    System.out.println("⚠ Stock bajo de " + p.getNombre() + " (" + p.getCantidad() + " restantes).");
                }
            }
        }
    }


    private static void verHistoriaClinica() {
        Mascota m = seleccionarMascota();
        if (m == null) return;
        HistoriaClinica h = historias.get(m);
        if (h == null) {
            System.out.println("Ese paciente no tiene historia clínica.");
            return;
        }
        System.out.println("1. Resumida  2. Completa");
        int op = leerEntero("Opción: ");
        System.out.println("\n===== HISTORIA CLÍNICA =====");
        System.out.println(op == 2 ? h.historiaCompleta() : h.historiaSencilla());
    }


    private static void gestionarRecibos() {
        System.out.println("\nRecibos pendientes de pago:");
        boolean hay = false;
        for (Recibo r : clinica.getRecibos()) {
            if (!r.getPagado()) {
                System.out.println("  " + r.getNumeroRecibo() + " - " + r.getMascota().getNombre()
                        + " (" + r.getTutor().getNombre() + ") - $" + r.getTotalFinal());
                hay = true;
            }
        }
        if (!hay) System.out.println("  ninguno");

        String numero = leer("Número de recibo (Enter para volver): ");
        if (numero.isEmpty()) return;
        Recibo r = buscarRecibo(numero);
        if (r == null) {
            System.out.println("Recibo no encontrado.");
            return;
        }
        System.out.println("1. Ver  2. Agregar producto  3. Agregar servicio  4. Pagar");
        switch (leerEntero("Opción: ")) {
            case 1 -> System.out.println("\n" + r.imprimirRecibo());
            case 2 -> venderProductos(r);
            case 3 -> {
                if (r.getPagado()) {
                    System.out.println("El recibo ya está pagado.");
                } else {
                    Servicio s = elegirServicio(clinica.getServiciosDisponibles(),
                            r.getConsulta().getVeterinario());
                    if (s != null) r.agregarServicio(s);
                }
            }
            case 4 -> pagarRecibo(r);
            default -> System.out.println("Opción inválida.");
        }
    }

    private static void pagarRecibo(Recibo r) {
        if (r.getPagado()) {
            System.out.println("Este recibo ya fue pagado.");
            return;
        }
        r.pagar();
        System.out.println("\n" + r.imprimirRecibo());
        System.out.println("Puntos de fidelidad del tutor: " + r.getTutor().getPuntosFidelidad());
    }

    private static Recibo buscarRecibo(String numero) {
        for (Recibo r : clinica.getRecibos()) {
            if (r.getNumeroRecibo().equalsIgnoreCase(numero)) return r;
        }
        return null;
    }


    private static void menuInventario() {
        System.out.println("1. Ver inventario  2. Registrar producto  3. Reabastecer");
        switch (leerEntero("Opción: ")) {
            case 1 -> {
                System.out.println("\n-- Productos de venta --");
                listarProductos(Producto.Tipo.VENTA);
                System.out.println("\n-- Insumos --");
                listarProductos(Producto.Tipo.INSUMO);
            }
            case 2 -> registrarProducto();
            case 3 -> {
                Producto p = clinica.buscarProducto(leerObligatorio("Código del producto: "));
                if (p == null) {
                    System.out.println("Producto no encontrado.");
                } else {
                    p.agregarCantidad(leerEntero("Cantidad a agregar: "));
                    System.out.println(p.getNombre() + " ahora tiene " + p.getCantidad() + " unidades.");
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    private static void registrarProducto() {
        String codigo = leerObligatorio("Código: ");
        String nombre = leerObligatorio("Nombre (ej. 'Amoxicilina 500mg - tabletas'): ");
        System.out.println("Tipo: 1. Venta (aparece en recibo)  2. Insumo (se gasta en la clínica)");
        Producto.Tipo tipo = leerEntero("Opción: ") == 2 ? Producto.Tipo.INSUMO : Producto.Tipo.VENTA;
        double precio = tipo == Producto.Tipo.VENTA ? leerDoublePositivo("Precio de venta: ") : 0;
        double costo = leerDoublePositivo("Costo de compra: ");
        int cantidad = leerEntero("Cantidad en stock: ");
        int minimo = leerEntero("Stock mínimo: ");
        if (clinica.registrarProducto(new Producto(codigo, nombre, tipo, precio, costo, cantidad, minimo))) {
            System.out.println("Producto registrado.");
        }
    }

    private static void listarProductos(Producto.Tipo tipo) {
        boolean hay = false;
        for (Producto p : clinica.getProductos()) {
            if (p.getTipo() != tipo) continue;
            System.out.println("  [" + p.getCodigo() + "] " + p.getNombre()
                    + " | stock: " + p.getCantidad()
                    + (tipo == Producto.Tipo.VENTA ? " | precio: $" + p.getPrecioVenta() : "")
                    + (p.verificarStockBajo() ? "  ⚠ STOCK BAJO" : ""));
            hay = true;
        }
        if (!hay) System.out.println("  (sin productos)");
    }


    private static void registrarServicioCatalogo() {
        String tipo = leerObligatorio("Tipo de procedimiento (ej. Consulta general, Ecografía): ");
        String descripcion = leer("Descripción: ");
        double costo = leerDoublePositivo("Costo base: ");
        clinica.registrarServicio(new Servicio(tipo, descripcion, "", "", costo));
        System.out.println("Servicio agregado al catálogo.");
    }

    private static void registrarGastoInsumo() {
        System.out.println("\nInsumos disponibles:");
        listarProductos(Producto.Tipo.INSUMO);
        String codigo = leerObligatorio("Código del insumo: ");
        int cantidad = leerEntero("Cantidad gastada: ");
        if (clinica.registrarGastoInsumo(codigo, cantidad)) {
            System.out.println("Gasto registrado.");
        }
    }


    private static void consultarTutor() {
        Tutor t = clinica.buscarTutor(leerObligatorio("Documento del tutor: "));
        if (t == null) {
            System.out.println("Tutor no encontrado.");
            return;
        }
        System.out.println("\n" + t);
        System.out.println("Puntos de fidelidad: " + t.getPuntosFidelidad()
                + " (descuento actual: " + (int) (t.obtenerPorcentajeDescuento() * 100) + "%)");
        System.out.println("Mascotas:");
        for (Mascota m : t.getMascotas()) {
            System.out.println("  - " + m.getNombre() + " (" + m.getEspecie() + ", " + m.calcularEdad() + ")");
        }
    }


    private static void iniciarNuevoDia() {
        if (leerSiNo("Esto limpia las consultas e insumos del día. ¿Ya hizo el cierre y desea continuar?")) {
            clinica.iniciarNuevoDia();
            System.out.println("Nuevo día iniciado.");
        }
    }

    private static Mascota seleccionarMascota() {
        String dato = leerObligatorio("Microchip o nombre de la mascota: ");
        Mascota m = clinica.buscarMascota(dato);
        if (m == null) {
            System.out.println("Mascota no encontrada. Regístrela primero (opción 1).");
        }
        return m;
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return sc.hasNextLine() ? sc.nextLine().trim() : "";
    }

    private static String leerObligatorio(String mensaje) {
        String s;
        do {
            s = leer(mensaje);
            if (s.isEmpty()) System.out.println("Este dato es obligatorio.");
        } while (s.isEmpty() && sc.hasNextLine());
        return s;
    }

    private static String leerConDefecto(String etiqueta, String defecto) {
        if (defecto == null || defecto.isEmpty()) return leer(etiqueta + ": ");
        String s = leer(etiqueta + " [" + defecto + "]: ");
        return s.isEmpty() ? defecto : s;
    }

    private static boolean leerSiNo(String mensaje) {
        String s = leer(mensaje + " (s/n): ");
        return s.equalsIgnoreCase("s") || s.equalsIgnoreCase("si") || s.equalsIgnoreCase("sí");
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leer(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
                if (!sc.hasNextLine()) return 0;
            }
        }
    }

    private static double leerDoublePositivo(String mensaje) {
        while (true) {
            try {
                double v = Double.parseDouble(leer(mensaje).replace(',', '.'));
                if (v >= 0) return v;
                System.out.println("El valor no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
            if (!sc.hasNextLine()) return 1;
        }
    }
    
    private static String leerFecha(String etiqueta, boolean opcional) {
        while (true) {
            String s = leer(etiqueta + " (aaaa-mm-dd" + (opcional ? ", Enter si no aplica" : "") + "): ");
            if (s.isEmpty() && opcional) return null;
            try {
                LocalDate.parse(s);
                return s;
            } catch (Exception e) {
                System.out.println("Fecha inválida, use el formato aaaa-mm-dd.");
                if (!sc.hasNextLine()) return null;
            }
        }
    }

    private static LocalDateTime leerFechaHora() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            try {
                return LocalDateTime.parse(leer("Fecha y hora (aaaa-mm-dd HH:mm): "), f);
            } catch (Exception e) {
                System.out.println("Formato inválido. Ejemplo: 2026-10-21 22:30");
                if (!sc.hasNextLine()) return LocalDateTime.now();
            }
        }
    }
}