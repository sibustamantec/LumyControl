public class Tutor {
    private String nombre, direccion, correo, telefono, documento;
    private int puntosFidelidad;
    public Tutor(String nombre, String direccion, String correo, String telefono, String documento, int puntosFidelidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.documento = documento;
        this.puntosFidelidad = puntosFidelidad;
    }
    public String getNombre() {return nombre;}
    public String getDocumento() {return documento;}
    public void setDireccion(String direccion) {this.direccion = direccion;}
    public void setCorreo(String correo) {this.correo = correo;}
    public void setTelefono(String telefono) {this.telefono = telefono;}
    public int getPuntosFidelidad() {return puntosFidelidad;}
    public String imprimirDatos(){
        return "Nombre: "+ nombre +
                "\n Número: "+ telefono +
                "\n Dirección: "+ direccion +
                "\n Correo: "+ correo;
    }
    //public int sumarPuntos(Recibo r){
        //puntosFidelidad += r.getPuntosFidelidad();
        //return puntosFidelidad;
    //}
}
