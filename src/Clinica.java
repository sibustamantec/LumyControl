import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class Clinica {
    private ArrayList<Tutor> tutores;
    private ArrayList<Mascota> mascotas;
    private ArrayList<Producto> productos;
    private ArrayList<Servicio> serviciosDisponibles; // catálogo; al usarlos en una consulta se usa servicio.copiar(...)
    private ArrayList<Recibo> recibos;
    private ArrayList<ConsultaGeneral> consultasDelDia;
    private ArrayList<LineaProducto> insumosGastados; // insumos usados en el día, para el cierre
    private Jaula[][] jaulas; // Matriz de jaulas: 2 filas x 5 columnas

    public Clinica(){
        tutores = new ArrayList<>();
        mascotas = new ArrayList<>();
        productos = new ArrayList<>();
        serviciosDisponibles = new ArrayList<>();
        recibos = new ArrayList<>();
        consultasDelDia = new ArrayList<>();
        insumosGastados = new ArrayList<>();
        jaulas = new Jaula[2][5];
        for(int i = 0; i < jaulas.length; i++){
            for(int j = 0; j < jaulas[i].length; j++){
                jaulas[i][j] = new Jaula(i, j);
            }
        }
    }

    public ArrayList<Tutor> getTutores(){return tutores;}
    public ArrayList<Mascota> getMascotas(){return mascotas;}
    public ArrayList<Producto> getProductos(){return productos;}
    public ArrayList<Servicio> getServiciosDisponibles(){return serviciosDisponibles;}
    public ArrayList<Recibo> getRecibos(){return recibos;}
    public ArrayList<ConsultaGeneral> getConsultasDelDia(){return consultasDelDia;}

    public boolean registrarTutor(Tutor t){
        if(buscarTutor(t.getDocumento()) != null){
            System.out.println("Ya existe un tutor con ese documento.");
            return false;
        }
        tutores.add(t);
        return true;
    }

    public boolean registrarMascota(Mascota m){
        String chip = m.getMicrochip();
        if(chip != null && !chip.isEmpty()){
            for(Mascota otra : mascotas){
                if(chip.equals(otra.getMicrochip())){
                    System.out.println("Ya existe una mascota con ese microchip.");
                    return false;
                }
            }
        }
        mascotas.add(m);
        // deja la mascota también en la lista de su tutor (agregarMascota evita duplicados)
        if(m.getTutor() != null){m.getTutor().agregarMascota(m);}
        return true;
    }

    public boolean registrarProducto(Producto p){
        if(buscarProducto(p.getCodigo()) != null){
            System.out.println("Ya existe un producto con ese código.");
            return false;
        }
        productos.add(p);
        return true;
    }

    public void registrarServicio(Servicio s){
        serviciosDisponibles.add(s);
    }

    public boolean registrarConsulta(ConsultaGeneral c){
        for(ConsultaGeneral otra : consultasDelDia){
            if(otra.getNumeroConsulta().equals(c.getNumeroConsulta())){
                System.out.println("Ya existe una consulta con ese número.");
                return false;
            }
        }
        consultasDelDia.add(c);
        return true;
    }

    public void registrarRecibo(Recibo r){
        recibos.add(r);
    }

    public boolean registrarGastoInsumo(String codigo, int cantidad){
        Producto p = buscarProducto(codigo);
        if(p == null){System.out.println("Producto no encontrado."); return false;}
        if(p.getTipo() != Producto.Tipo.INSUMO){System.out.println("El producto no es un insumo."); return false;}
        if(!p.reducirCantidad(cantidad)){return false;}
        insumosGastados.add(new LineaProducto(p, cantidad));
        return true;
    }

    public Tutor buscarTutor(String documento){
        for(Tutor t : tutores){
            if(t.getDocumento().equals(documento)){return t;}
        }
        return null;
    }

    public Mascota buscarMascota(String dato){
        for(Mascota m : mascotas){
            if(m.getMicrochip() != null && m.getMicrochip().equals(dato)){return m;}
        }
        for(Mascota m : mascotas){
            if(m.getNombre().equalsIgnoreCase(dato)){return m;}
        }
        return null;
    }

    public Producto buscarProducto(String codigo){
        for(Producto p : productos){
            if(p.getCodigo().equals(codigo)){return p;}
        }
        return null;
    }

    public Servicio buscarServicio(String tipoProcedimiento){
        for(Servicio s : serviciosDisponibles){
            if(s.getTipoProcedimiento().equalsIgnoreCase(tipoProcedimiento)){return s;}
        }
        return null;
    }

    public String generarCierreDelDia(){
        LocalDate hoy = LocalDate.now();
        HashSet<Mascota> pacientes = new HashSet<>();
        HashSet<Tutor> tutoresAtendidos = new HashSet<>();
        double ventasProductos = 0, costoCompra = 0, ingresosServicios = 0, totalRecaudado = 0;

        for(Recibo r : recibos){
            if(!r.getFechaEmision().equals(hoy)){continue;}
            pacientes.add(r.getMascota());
            tutoresAtendidos.add(r.getTutor());
            if(r.getPagado()){
                for(LineaProducto l : r.getProducto()){
                    ventasProductos += l.calcularSubtotal();
                    costoCompra += l.calcularCostoCompra();
                }
                for(Servicio s : r.getServicio()){
                    ingresosServicios += s.calcularCostoServiciosTotal();
                }
                totalRecaudado += r.getTotalFinal();
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("===== CIERRE DEL DÍA ").append(hoy).append(" =====");
        sb.append("\nConsultas registradas: ").append(consultasDelDia.size());
        sb.append("\nPacientes atendidos: ").append(pacientes.size());
        sb.append("\nTutores atendidos: ").append(tutoresAtendidos.size());
        sb.append("\nVentas de productos: ").append(ventasProductos);
        sb.append("\nCosto de compra de lo vendido: ").append(costoCompra);
        sb.append("\nUtilidad bruta (ventas - costo de compra): ").append(ventasProductos - costoCompra);
        sb.append("\nIngresos por servicios: ").append(ingresosServicios);
        sb.append("\nTotal recaudado (con recargos y descuentos): ").append(totalRecaudado);

        sb.append("\nInsumos gastados:");
        if(insumosGastados.isEmpty()){sb.append(" ninguno");}
        for(LineaProducto l : insumosGastados){
            sb.append("\n  - ").append(l.getProducto().getNombre()).append(" x").append(l.getCantidad());
        }

        sb.append("\nProductos con stock bajo:");
        boolean hayStockBajo = false;
        for(Producto p : productos){
            if(p.verificarStockBajo()){
                sb.append("\n  - ").append(p.getNombre()).append(" (").append(p.getCantidad())
                  .append(" disponibles, mínimo ").append(p.getStockMinimo()).append(")");
                hayStockBajo = true;
            }
        }
        if(!hayStockBajo){sb.append(" ninguno");}
        return sb.toString();
    }

    public void iniciarNuevoDia(){
        consultasDelDia.clear();
        insumosGastados.clear();
    }

    //Matriz de jaulas: 2 filas x 5 columnas

    public int getCantidadFilasJaulas(){return jaulas.length;}
    public int getCantidadColumnasJaulas(){return jaulas[0].length;}

    public Jaula buscarJaula(int fila, int columna){
        if(fila < 0 || fila >= jaulas.length || columna < 0 || columna >= jaulas[0].length){
            return null;
        }
        return jaulas[fila][columna];
    }

    public Jaula buscarMascotaEnJaula(Mascota m){
        for(int i = 0; i < jaulas.length; i++){
            for(int j = 0; j < jaulas[i].length; j++){
                Jaula jActual = jaulas[i][j];
                if(jActual.getPaciente() != null && jActual.getPaciente().equals(m)){
                    return jActual;
                }
            }
        }
        return null;
    }

    public String mostrarCuadricula(){
        StringBuilder sb = new StringBuilder();
        sb.append("==== JAULAS ====\n");
        for(int i = 0; i < jaulas.length; i++){
            for(int j = 0; j < jaulas[i].length; j++){
                Jaula jActual = jaulas[i][j];
                sb.append("[").append(jActual.getEstadoChar()).append("]");
            }
            sb.append("\n");
        }
        sb.append("L: Libre, O: Ocupada, S: Sin adecuar\n");
        return sb.toString();
    }

    public String hospitalizarPaciente(Mascota paciente, int fila, int columna){
        if(paciente == null){
            return "Paciente inválido.";
        }
        Jaula jaula = buscarJaula(fila, columna);
        if(jaula == null){
            return "Jaula no encontrada.";
        }
        Jaula actual = buscarMascotaEnJaula(paciente);
        if(actual != null && actual != jaula){
            return "La mascota ya está hospitalizada en otra jaula.";
        }
        if(!jaula.getEstado().equalsIgnoreCase("libre")){
            return "La jaula no está libre.";
        }
        jaula.ocuparJaula(paciente);
        return "Paciente hospitalizado en la jaula [" + (fila + 1) + "," + (columna + 1) + "].";
        
    }
    public String darAltaPaciente(Mascota paciente){
        Jaula jaula = buscarMascotaEnJaula(paciente);
        if(jaula == null){
            return "La mascota no está hospitalizada.";
        }
        jaula.liberarJaula();
        return "Paciente dado de alta de la jaula [" + (jaula.getFila() + 1) + "," + (jaula.getColumna() + 1) + "].";
    }
    
    public String adecuarJaula(int fila, int columna){
        Jaula jaula = buscarJaula(fila, columna);
        if(jaula == null){
            return "Jaula no encontrada.";
        }
        if(!jaula.getEstado().equalsIgnoreCase("sin adecuar")){
            return "La jaula está en estado "+ jaula.getEstado() + ".";
        }
        jaula.adecuarJaula();
        return "Jaula [" + (fila + 1) + "," + (columna + 1) + "] preparada.";
    }

    public String consultarJaula(int fila, int columna){
        Jaula jaula = buscarJaula(fila, columna);
        if(jaula == null){
            return "Jaula no encontrada.";
        }
        return jaula.toString();
    }
}
