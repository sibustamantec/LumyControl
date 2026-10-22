public class Producto {
    public enum Tipo {
        VENTA,   // se vende al tutor y aparece en el recibo
        INSUMO   // se gasta dentro de la clínica (no se vende)
    }

    private String codigo, nombre;
    private Tipo tipo;
    private double precioVenta, costoCompra;
    private int cantidad, stockMinimo;

    public Producto(String codigo, String nombre, Tipo tipo, double precioVenta, double costoCompra, int cantidad, int stockMinimo){
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        setPrecioVenta(precioVenta);
        setCostoCompra(costoCompra);
        if(cantidad >= 0){this.cantidad = cantidad;}
        else{System.out.println("Cantidad inválida");}
        if(stockMinimo >= 0){this.stockMinimo = stockMinimo;}
        else{System.out.println("Stock mínimo inválido");}
    }

    public String getCodigo(){return codigo;}
    public String getNombre(){return nombre;}
    public Tipo getTipo(){return tipo;}
    public double getPrecioVenta(){return precioVenta;}
    public double getCostoCompra(){return costoCompra;}
    public int getCantidad(){return cantidad;}
    public int getStockMinimo(){return stockMinimo;}

    public void setPrecioVenta(double precioVenta){
        if(precioVenta >= 0){this.precioVenta = precioVenta;}
        else{System.out.println("Precio de venta inválido");}
    }
    public void setCostoCompra(double costoCompra){
        if(costoCompra >= 0){this.costoCompra = costoCompra;}
        else{System.out.println("Costo de compra inválido");}
    }

    public boolean verificarDisponibilidad(int cantidad){
        return cantidad > 0 && this.cantidad >= cantidad;
    }
    // al reabastecer
    public void agregarCantidad(int cantidad){
        if(cantidad > 0){this.cantidad += cantidad;}
        else{System.out.println("Cantidad inválida");}
    }
    // al vender o gastar un insumo; devuelve false si no alcanza el stock
    public boolean reducirCantidad(int cantidad){
        if(!verificarDisponibilidad(cantidad)){
            System.out.println("Stock insuficiente de " + nombre);
            return false;
        }
        this.cantidad -= cantidad;
        return true;
    }
    public boolean verificarStockBajo(){
        return cantidad <= stockMinimo;
    }

    @Override
    public String toString(){
        return "Código: " + codigo +
                "\nNombre: " + nombre +
                "\nTipo: " + tipo +
                "\nPrecio de venta: " + precioVenta +
                "\nCosto de compra: " + costoCompra +
                "\nCantidad: " + cantidad +
                "\nStock mínimo: " + stockMinimo;
    }
}
