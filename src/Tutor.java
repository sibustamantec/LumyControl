import java.util.ArrayList;
public class Tutor {
    private String nombre, direccion, correo, telefono, documento;
    private int puntosFidelidad;
    private ArrayList<Mascota> mascotas; //por el momento es una variable del tipo mascota, en el futuro se planea usar un array de mascotas
    public Tutor(String nombre, String direccion, String correo, String telefono, String documento, int puntosFidelidad) {
        this.nombre = nombre;
        this.documento = documento;
        this.puntosFidelidad = puntosFidelidad;
        this.mascotas = new ArrayList<>();
        setDireccion(direccion);
        setCorreo(correo);
        setTelefono(telefono);
    }
    public String getNombre() {return nombre;}
    public String getDocumento() {return documento;}
    public String getTelefono() {return telefono;}
    public String getCorreo() {return correo;}
    public String getDireccion() {return direccion;}
    public int getPuntosFidelidad(){return puntosFidelidad;}
    public ArrayList<Mascota> getMascotas() {return mascotas;}

    public void setNombre(String nombre){this.nombre = nombre;}
    public void setDocumento(){this.documento = documento;}
    public void setDireccion(String direccion) {this.direccion = direccion;}
    public void setCorreo(String correo) {this.correo = correo;}
    public void setTelefono(String telefono) {this.telefono = telefono;}

    @Override
    public String toString(){
        return "Nombre: "+ nombre +
                "\n Número: "+ telefono +
                "\n Dirección: "+ direccion +
                "\n Correo: "+ correo;
    }
    public int sumarPuntos(Recibo r){
        if(r.getPagado()){puntosFidelidad += r.getPuntosGenerados();}
        else{return puntosFidelidad;}
        return puntosFidelidad;
    }
    public double obtenerPorcentajeDescuento(){
        double descuento = 0;
        if (puntosFidelidad == 0){return 0;}
        else if (puntosFidelidad >= 50 && puntosFidelidad <= 99){descuento = 0.05;}
        else if (puntosFidelidad >= 100 && puntosFidelidad <= 200){descuento = 0.1;}
        else if (puntosFidelidad >= 200){descuento = 0.15;}
        return descuento;
    }
    public void agregarMascota(Mascota mascota){
        if(mascota != null && !mascotas.contains(mascota)){
            mascotas.add(mascota);
        }
    }

    public boolean eliminarMascota(Mascota mascota){
        return mascotas.remove(mascota);
    }

    public boolean reemplazarMascota(Mascota actual, Mascota nueva){
        int posicion = mascotas.indexOf(actual);
        if(posicion < 0){return false;}
        mascotas.set(posicion, nueva);
        return true;
    }
}
