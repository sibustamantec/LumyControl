import java.time.LocalDate;
import java.time.Period;
public class Mascota {
    private String nombre, raza, temperamento, sexo, alimentacion, especie, color, estReproductivo, alergias, microchip;
    private LocalDate fechaNacimiento;
    private double peso;
    private Tutor tutor;
    public Mascota(Tutor tutor, String nombre, String raza, String temperamento,  String fechaNacimiento, String sexo, String alimentacion, String especie, String color, String estReproductivo, String alergias, String microchip, double peso){
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
        this.alergias = alergias; // alergias se puede manejar como array en un futuro
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
    public String getMicrochip(){return microchip;}
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
    //setAlergias() se usa así por el momento porque no estamos trabajando listas
    public void setAlergias(String alergiasNuevas){
        alergias +=  " " + alergiasNuevas;
    }
    public void setAlimentacion(String alimentacion){
        this.alimentacion = alimentacion;
    }
    public String calcularEdad(){
        LocalDate hoy = LocalDate.now();
        if(fechaNacimiento!=null){
            Period edad = Period.between(fechaNacimiento, hoy);
            return edad.getYears() + " años y " + edad.getMonths() + " meses.";
        }
        else{
            return "Fecha de nacimiento no registrada";
        }
    }
    public String ficha(){
        String ficha = """
                Nombre: %s.
                Microchip: %s
                Especie: %s.
                Propietario: %s.
                """.formatted(nombre, microchip, especie, tutor.getNombre());
        return ficha;
    }
    @Override
    public String toString(){
        return "Nombre: " + nombre +
                "\nFecha nacimiento: " + fechaNacimiento +
                "\nEdad: " + calcularEdad() +
                "\nNumero microhip: " + microchip +
                "\nEspecie: " + especie +
                "\nRaza: " + raza +
                "\nSexo: " + sexo +
                "\nColor: " + color +
                "\nEstado reproductivo: " + estReproductivo +
                "\nAlergias: " + alergias;
    }
}
