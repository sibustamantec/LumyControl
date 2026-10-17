public class Tutor {
    private String nombre, direccion, correo, telefono, documento;
    private int puntosFidelidad;
    public Tutor(String nombre, String direccion, String correo, String telefono, String documento, int puntosFidelidad) {
        this.nombre = nombre;
        this.documento = documento;
        this.puntosFidelidad = puntosFidelidad;
        setDireccion(direccion);
        setCorreo(correo);
        setTelefono(telefono);
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
    public int sumarPuntos(Recibo r){
        if(r.getPagado()){puntosFidelidad += r.getPuntosGenerados();}
        else{return puntosFidelidad;}
        return puntosFidelidad;
    }
    public double obtenerPorcentajeDescuento(){
        double descuento = 0;
        if (puntosFidelidad == 0){return 0;}
        else if (puntosFidelidad >= 50 && puntosFidelidad <= 99){descuento = 0.05;}
        else if (puntosFidelidad >= 100 && puntosFidelidad <= 149){descuento = 0.1;}
        else if (puntosFidelidad >= 150){descuento = 0.15;}
        return descuento;
    }
}
