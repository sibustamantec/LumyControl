import java.time.LocalDate;
public class Servicio {
    private LocalDate fecha;
    private String tipoProcedimiento, descripcion, veterinario, resultado;
    private double costoBase;
    public Servicio(LocalDate fecha, String tipoProcedimiento, String descripcion, String veterinario, String resultado, double costoBase) {
        this.fecha = LocalDate.now();
        this.tipoProcedimiento = tipoProcedimiento;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.resultado = resultado;
    }
    public LocalDate getFecha() {return fecha;}
    public String tipoProcedimiento(){return tipoProcedimiento;}
    public String getdescripcion(){return descripcion;}
    public String getVeterinario(){return veterinario;}
    public String getResultado(){return resultado;}
    public double getCostoBase(){return costoBase;}

    public double obtenerCostoBase(){
        return costoBase;
    }

    public String imprimirDatos(){
        return "Fecha: "+fecha+
                "\nTipo Procedimiento: "+tipoProcedimiento+
                "\nDescripcion: "+descripcion+
                "\nVeterinario: "+veterinario+
                "\nResultado: "+resultado+
                "\nPrecio base: "+costoBase;
    }
}
//lo mismo que en lineaproductos, pero no me queda claro si es
//necesario hacer la clase TipoProcedimiento
