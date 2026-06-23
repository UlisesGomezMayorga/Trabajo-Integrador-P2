package integrado.prog2.service;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;
public class ProductoService {
    private List<Producto> productos;
    private Long contadorId;
    private CategoriaService categoriaService; 
    public ProductoService(CategoriaService categoriaService) {
        this.productos = new ArrayList<>();
        this.contadorId = 1L;
        this.categoriaService = categoriaService;
    }
    public Producto crear(String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Long idCategoria) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del producto no puede estar vacío."); 
        }
        if (precio < 0) {
            throw new StockInvalidoException("El precio no puede ser negativo."); 
        }
        if (stock < 0) {
            throw new StockInvalidoException("El stock no puede ser negativo.");
        }
        Categoria categoriaAsociada = categoriaService.buscarPorId(idCategoria); // 
        Producto nuevoProducto = new Producto(contadorId++, nombre, precio, descripcion, stock, imagen, disponible, categoriaAsociada);
        productos.add(nuevoProducto);
        return nuevoProducto;
    }
    public List<Producto> listarActivos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }
    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("El producto con ID " + id + " no existe o fue eliminado.");
    }
    public void modificar(Long id, String nuevoNombre, Double nuevoPrecio, Integer nuevoStock, Long nuevoIdCategoria) throws Exception {
        Producto p = buscarPorId(id); 
        
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            p.setNombre(nuevoNombre);
        }
        if (nuevoPrecio != null) {
            if (nuevoPrecio < 0) throw new StockInvalidoException("El precio no puede ser menor a 0.");
            p.setPrecio(nuevoPrecio);
        }
        if (nuevoStock != null) {
            if (nuevoStock < 0) throw new StockInvalidoException("El stock no puede ser menor a 0."); 
            p.setStock(nuevoStock);
        }
        if (nuevoIdCategoria != null) {
            Categoria nuevaCategoria = categoriaService.buscarPorId(nuevoIdCategoria);
            p.setCategoria(nuevaCategoria);
        }
    }
    public void eliminarLogico(Long id) throws EntidadNoEncontradaException {
        Producto p = buscarPorId(id);
        p.setEliminado(true);
    }
}