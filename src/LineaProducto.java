public class LineaProducto {
    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    public LineaProducto(Producto producto, int cantidad){
        this.producto = producto;
        setCantidad(cantidad);
        this.precioUnitario = producto.getPrecioVenta();
    }
    public Producto getProducto(){return producto;}
    public int getCantidad(){return cantidad;}
    public double getPrecioUnitario(){return precioUnitario;}
    public void setCantidad(int cantidad){
        if(cantidad > 0){this.cantidad = cantidad;}
        else{System.out.println("Cantidad inválida");}
    }

    public double calcularSubtotal(){
        return precioUnitario*cantidad;
    }
    public double calcularCostoCompra(){
        return producto.getCostoCompra() * cantidad;
    }
    @Override
    public String toString(){
        return producto.getNombre() + " x" + cantidad + " (" + precioUnitario + " c/u) = " + calcularSubtotal();
    }
}