import java.time.LocalDate;
import java.util.ArrayList;
public class Recibo {
    private String numeroRecibo;
    private LocalDate fechaEmision;
    private Tutor tutor;
    private Mascota mascota;
    private ConsultaGeneral consulta;
    private ArrayList<Servicio> servicio;
    private ArrayList<LineaProducto> producto;
    private double costoBaseTotal, recargo, descuento;
    private int totalFinal, puntosGenerados;
    private boolean pagado;
    public Recibo(String numeroRecibo,Tutor tutor,Mascota mascota,ConsultaGeneral consulta){ 
        this.numeroRecibo = numeroRecibo;
        this.fechaEmision = LocalDate.now();
        this.tutor = tutor;
        this.mascota = mascota;
        this.consulta = consulta;
        this.servicio = new ArrayList<>();
        this.producto = new ArrayList<>();
        this.costoBaseTotal = 0;
        this.recargo = 0;
        this.descuento = 0;
        this.totalFinal = 0;
        this.puntosGenerados = 0;
        this.pagado = false;
        for(Servicio s : consulta.getServicios()){this.servicio.add(s);}
        actualizarTotales();
    }
    public String getNumeroRecibo(){return numeroRecibo;}
    public LocalDate getFechaEmision(){return fechaEmision;}
    public Tutor getTutor(){return tutor;}
    public Mascota getMascota(){return mascota;}
    public ConsultaGeneral getConsulta() {return consulta;}
    public ArrayList<Servicio> getServicio(){return servicio;}
    public void agregarServicio(Servicio s){if(pagado)return; servicio.add(s); actualizarTotales();}
    public ArrayList<LineaProducto> getProducto(){return producto;}
    public void agregarLineaProducto(LineaProducto p){if(pagado)return; producto.add(p); actualizarTotales();}
    public double getCostoBaseTotal(){return costoBaseTotal;}
    public double getRecargo(){return recargo;}
    public double getDescuento(){return descuento;}
    public int getTotalFinal(){return totalFinal;}
    public int getPuntosGenerados(){return puntosGenerados;}
    public boolean getPagado(){return pagado;}

    public double calcularCostoBaseTotal(){
        double total = 0;
        for(Servicio s : servicio){total += s.getCostoBase();}
        for(LineaProducto p : producto){total += p.calcularSubtotal();}
        return total;
    }
    public int puntosGenerados(){
        puntosGenerados = (int) (calcularCostoBaseTotal()/2000);
        return puntosGenerados;
    }
    public double calcularRecargo() {
        if (!consulta.esNocturno()){return recargo;}
        double costoServicios = 0;
        for(Servicio s : servicio){
            costoServicios += s.getCostoBase();
        }
        recargo =  costoServicios * 0.20;
        return recargo;
    }
    public double calcularDescuento(){
        return calcularCostoBaseTotal()*tutor.obtenerPorcentajeDescuento();
    }

    public int calcularTotalFinal(){
        return (int)(calcularCostoBaseTotal()+calcularRecargo()-calcularDescuento());
    }

    public void actualizarTotales(){
        if (pagado){return; }
        costoBaseTotal = calcularCostoBaseTotal();
        recargo = calcularRecargo();
        descuento = calcularDescuento();
        totalFinal = calcularTotalFinal();
    }

    public String imprimirRecibo(){
        return "Recibo N°: "+ numeroRecibo +
                "\nFecha: "+ fechaEmision +
                "\nDocumento del Tutor: "+ tutor.getDocumento()+
                "\nNombre de la Mascota: "+ mascota.getNombre()+
                "\nServicios: "+ getServicio() +
                "\nProductos: "+ getProducto() +
                //verificar si es necesario agregar el metodo imprimirDatos() en Servicio y LineaProducto para que se vea mejor
                "\nPrecio Base: "+ costoBaseTotal +
                "\nRecargo: +"+ recargo +
                "\nDescuento: -"+ descuento +
                "\nPrecio Total: "+totalFinal+
                "\nPagado: "+pagado+
                "\nPuntos Generados: "+puntosGenerados;
    }
    public void pagar(){
        if (pagado){
            System.out.println("Este Recibo ya fue Cancelado.");
            return;
        }
        actualizarTotales();
        pagado = true;
        puntosGenerados();
        tutor.sumarPuntos(this);
    }
}