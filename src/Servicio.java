import java.time.LocalDate;
public class Servicio {
    private LocalDate fecha;
    private String tipoProcedimiento, descripcion, veterinario, resultado;
    private double costoBase;
    public Servicio(String tipoProcedimiento, String descripcion, String veterinario, String resultado, double costoBase) {
        this.fecha = LocalDate.now();
        this.tipoProcedimiento = tipoProcedimiento;
        this.descripcion = descripcion;
        this.veterinario = veterinario;
        this.resultado = resultado;
        setCostoBase(costoBase);
    }
    public LocalDate getFecha() {return fecha;}
    public String getTipoProcedimiento(){return tipoProcedimiento;}
    public String getDescripcion(){return descripcion;}
    public String getVeterinario(){return veterinario;}
    public String getResultado(){return resultado;}
    public double getCostoBase(){return costoBase;}

    public void setVeterinario(String veterinario){this.veterinario = veterinario;}
    public void setResultado(String resultado){this.resultado = resultado;}
    public void setCostoBase(double costoBase){
        if(costoBase >= 0){this.costoBase = costoBase;}
        else{System.out.println("Costo inválido");}
    }

    public Servicio copiar(String veterinario){
        return new Servicio(tipoProcedimiento, descripcion, veterinario, "", costoBase);
    }

    public String toString(){
        return "Fecha: "+fecha+
                "\nTipo Procedimiento: "+tipoProcedimiento+
                "\nDescripcion: "+descripcion+
                "\nVeterinario: "+veterinario+
                "\nResultado: "+resultado+
                "\nPrecio base: "+costoBase;
    }
}
