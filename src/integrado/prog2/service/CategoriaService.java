package integrado.prog2.service;
import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;
public class CategoriaService {
    private List<Categoria> categorias;
    private Long contadorId;
    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.contadorId = 1L;
    }
    public Categoria crear(String nombre, String descripcion) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre de la categoría no puede estar vacío.");
        }
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                throw new Exception("Error: Ya existe una categoría con el nombre '" + nombre + "'.");
            }
        }
        
        Categoria nuevaCategoria = new Categoria(contadorId++, nombre, descripcion);
        categorias.add(nuevaCategoria);
        return nuevaCategoria;
    }
    public List<Categoria> listarActivas() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                activas.add(c);
            }
        }
        return activas;
    }
    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("La categoría con ID " + id + " no existe o fue eliminada"); // [cite: 71, 267]
    }
    public void modificar(Long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException {
        Categoria c = buscarPorId(id);
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            c.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            c.setDescripcion(nuevaDescripcion);
        }
    }
    public void eliminarLogico(Long id) throws EntidadNoEncontradaException {
        Categoria c = buscarPorId(id);
        c.setEliminado(true);
    }
}