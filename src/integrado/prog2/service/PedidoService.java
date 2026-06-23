package integrado.prog2.service;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;
public class PedidoService {
    private List<Pedido> pedidos;
    private Long contadorId;
    private UsuarioService usuarioService;
    private ProductoService productoService;
    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new ArrayList<>();
        this.contadorId = 1L;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }
    public Pedido crear(Long idUsuario, FormaPago formaPago, List<Long> idsProductos, List<Integer> cantidades) throws Exception {
        Usuario u = usuarioService.buscarPorId(idUsuario);
        Pedido nuevoPedido = new Pedido(contadorId++, Estado.PENDIENTE, formaPago, u);
        for (int i = 0; i < idsProductos.size(); i++) {
            Producto p = productoService.buscarPorId(idsProductos.get(i));
            int cant = cantidades.get(i);

            if (cant <= 0) {
                throw new Exception("La cantidad debe ser mayor a 0 para el producto ID " + p.getId());
            }
            if (p.getStock() < cant) {
                throw new StockInvalidoException("Stock insuficiente para el producto: " + p.getNombre() + ". Disponible: " + p.getStock());
            }
            nuevoPedido.addDetallePedido(cant, p.getPrecio(), p);
        }
        for (int i = 0; i < idsProductos.size(); i++) {
            Producto p = productoService.buscarPorId(idsProductos.get(i));
            p.setStock(p.getStock() - cantidades.get(i));
        }
        pedidos.add(nuevoPedido);
        return nuevoPedido;
    }
    public List<Pedido> listarActivos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
    public Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("El pedido con ID " + id + " no existe o fue eliminado.");
    }
    public void actualizarEstadoYPago(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) throws EntidadNoEncontradaException {
        Pedido p = buscarPorId(id);
        if (nuevoEstado != null) p.setEstado(nuevoEstado);
        if (nuevaFormaPago != null) p.setFormaPago(nuevaFormaPago);
    }
    public void eliminarLogico(Long id) throws EntidadNoEncontradaException {
        Pedido p = buscarPorId(id);
        p.setEliminado(true);
    }
}