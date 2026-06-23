package integrado.prog2.entities;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario; 
    private List<DetallePedido> detalles; 
    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.fecha = LocalDate.now();
        this.total = 0.0;
        this.estado = Estado.PENDIENTE; 
    }
    public Pedido(Long id, Estado estado, FormaPago formaPago, Usuario usuario) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }
    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        Double subtotal = cantidad * precio; 
        DetallePedido detalle = new DetallePedido(null, cantidad, subtotal, producto);
        this.detalles.add(detalle);
        this.calcularTotal(); 
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto() != null && dp.getProducto().getId().equals(producto.getId())) {
                return dp;
            }
        }
        return null;
    }
    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido dp = findeDetallePedidoByProducto(producto);
        if (dp != null) {
            detalles.remove(dp);
            this.calcularTotal();
        }
    }
    @Override
    public void calcularTotal() {
        this.total = 0.0;
        for (DetallePedido dp : detalles) {
            if (!dp.isEliminado()) {
                this.total += dp.getSubtotal();
            }
        }
    }
    // GyS
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
    @Override
    public String toString() {
        String mailUsuario = (usuario != null) ? usuario.getMail() : "Sin Usuario";
        return String.format("[%d] Fecha: %s | Usuario: %s | Estado: %s | Pago: %s | Total: $%.2f",
                getId(), fecha, mailUsuario, estado, formaPago, total);
    }
}