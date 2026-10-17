public class LineaProducto {
    private Producto producto;
    private int cantidad;
    public LineaProducto(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }
    public Product getProducto(){return producto;}
    public int getCantidad(){return cantidad;}

    public double calcularSubtotal(){
        return producto.getPrecioVenta()*cantidad;
    }
}
//Es necesario hacer una lista con Productos p que itere en todos los
//productos que se agreguen para calcular el subtotal.
