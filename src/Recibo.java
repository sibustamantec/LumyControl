import java.time.LocalDate;
import java.util.ArrayList;
public class Recibo {
    private String numeroRecibo;
    private LocalDate fechaEmision;
    private Tutor tutor;
    private Mascota mascota;
    private ArrayList<Servicio> servicio;
    private ArrayList<LineaProducto> producto;
    private double costoBaseTotal, recargo;
    private int totalFinal, puntosGenerados;
    private boolean pagado;
    public Recibo(String numeroRecibo,Tutor tutor,Mascota mascota,double costoBaseTotal,int totalFinal,boolean pagado) {
        this.numeroRecibo = numeroRecibo;
        this.fechaEmision = LocalDate.now();
        this.tutor = tutor;
        this.mascota = mascota;
        this.consulta = consulta;
        this.servicio = new ArrayList<>();
        this.producto = new ArrayList<>();
        this.costoBaseTotal = costoBaseTotal;
        this.recargo = 0;
        this.totalFinal = totalFinal;
        this.puntosGenerados = 0;
        this.pagado = false;
    }
    public String getNumeroRecibo(){return numeroRecibo;}
    public LocalDate getFechaEmision(){return fechaEmision;}
    public Tutor getTutor(){return tutor;}
    public Mascota getMascota(){return mascota;}
    public ConsultaGeneral getConsulta() {return consulta;}
    public ArrayList getServicio(){return servicio;}
    public void agregarServicio(Servicio s){servicio.add(s);}
    public ArrayList getProducto(){return producto;}
    public void agregarLineaProducto(LineaProducto p){producto.add(p);}
    public double getCostoBaseTotal(){return costoBaseTotal;}
    public double getRecargo(){return recargo;}
    public int getTotalFinal(){return totalFinal;}
    public int getPuntosGenerados(){return puntosGenerados;}
    public boolean getPagado(){return pagado;}

    public double calcularCostoBaseTotal(){
        double total = 0;
        for(Servicio s : servicio){total += s.obtenerCostoBase();}
        for(LineaProducto p : producto){total += p.calcularSubtotal();}
        return total;
    }
    public int puntosGenerados(){
        puntosGenerados = (int) calcularCostoBaseTotal()/2000;
        return puntosGenerados;
    }
    public double calcularRecargo() {
        if (!consulta.esNocturno()){return recargo;}
        double costoServicios = 0;
        for(Servicio s : servicio){
            costoServicios += s.obtenerCostoBase();
            recargo =  costoServicios * 0.20;
        }
        return recargo;
    }
    public double calcularDescuento(){
        return calcularCostoBaseTotal()*tutor.obtenerPorcentajeDescuento();
    }

    public int calcularTotalFinal(){
        return (int)(calcularCostoBaseTotal()+calcularRecargo()-calcularDescuento());
    }

    public String imprimirRecibo(){
        return "Recibo N°: "+ numeroRecibo +
                "\nFecha: "+ fechaEmision +
                "\nDocumento del Tutor: "+ tutor.getDocumento()+
                "\nNombre de la Mascota: "+ mascota.getNombre()+
                "\nServicios: "+ getServicio() +
                "\nProductos: "+ getProducto() +
                "\nPrecio Base: "+ getCostoBaseTotal() +
                "\nRecargo: +"+ calcularRecargo() +
                "\nDescuento: -"+ calcularDescuento() +
                "\nPrecio Total: "+calcularTotalFinal()+
                "\nPagado: "+pagado+
                "\nPuntos Generados: "+puntosGenerados;
    }
    public void pagar(){
        if (pagado){
            System.out.println("Este Recibo ya fue Cancelado.");
            return;
        }
        pagado = true;
        puntosGenerados();
        tutor.sumarPuntos(this);
    }
}