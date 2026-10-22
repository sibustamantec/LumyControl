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

    public Clinica(){
        tutores = new ArrayList<>();
        mascotas = new ArrayList<>();
        productos = new ArrayList<>();
        serviciosDisponibles = new ArrayList<>();
        recibos = new ArrayList<>();
        consultasDelDia = new ArrayList<>();
        insumosGastados = new ArrayList<>();
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
                    ingresosServicios += s.getCostoBase();
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
}
