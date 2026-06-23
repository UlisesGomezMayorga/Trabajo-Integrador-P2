package integrado.prog2.entities;
public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;
    public DetallePedido() {
        super();
    }
    public DetallePedido(Long id, int cantidad, Double subtotal, Producto producto) {
        super(id);
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }
    // GyS
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public Double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    @Override
    public String toString() {
        String nombreProd = (producto != null) ? producto.getNombre() : "Producto Desconocido";
        return String.format("%d x %s = $%.2f", cantidad, nombreProd, subtotal);
    }
}