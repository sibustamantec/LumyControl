import java.time.LocalDate;
public class Mascota {
    private String nombre, raza, temperamento, sexo, alimentacion, especie, color, estReproductivo, alergias;
    private LocalDate fechaNacimiento;
    private double peso;
    private long microchip;
    private Tutor tutor;
    public Mascota(Tutor tutor, String nombre, String raza, String temperamento,  String fechaNacimiento, String sexo, String alimentacion, String especie, String color, String estReproductivo, String alergias, double peso, long microchip){
        this.tutor = tutor;
        this.nombre = nombre;
        this.raza = raza;
        this.temperamento = temperamento;
        setFechaNacimiento(fechaNacimiento);
        this.sexo = sexo;
        this.alimentacion = alimentacion;
        this.especie = especie;
        this.color = color;
        this.estReproductivo = estReproductivo;
        this.alergias = alergias;
        setPeso(peso);
        this.microchip = microchip;
    }
    public void setFechaNacimiento(String fechaNacimiento){
        try{
            this.fechaNacimiento = LocalDate.parse(fechaNacimiento);
        }catch(Exception e){
            System.out.println("Fecha inválida");
        }
    }
    public String getNombre(){return nombre;}
    public String getRaza(){return raza;}
    public String getTemperamento(){return temperamento;}
    public String getSexo(){return sexo;}
    public String getAlimentacion(){return alimentacion;}
    public String getEspecie(){return especie;}
    public String getColor(){return color;}
    public String getEstReproductivo(){return estReproductivo;}
    public String getAlergias(){return alergias;}
    public double getPeso(){return peso;}
    public long getMicrochip(){return microchip;}
    public LocalDate getFechaNacimiento(){return fechaNacimiento;}
    public void setPeso(double peso) {
        if(peso>0){
            this.peso = peso;
        }else{
            System.out.println("Peso inválido");
        }
    }
    public void setEstReproductivo(String estReproductivo){
        this.estReproductivo = estReproductivo;
    }
    public void setAlergias(String alergiasNuevas){
        alergias +=  " " + alergiasNuevas;
    }
    public void setAlimentacion(String alimentacion){
        this.alimentacion = alimentacion;
    }
    public void imprimirFicha(){
        String ficha = """
                Nombre: %s.
                Especie: %s.
                Propietario: %s.
                """.formatted(nombre, especie, tutor.getNombre());
    }
}
