package integrado.prog2.service;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;
public class UsuarioService {
    private List<Usuario> usuarios;
    private Long contadorId;
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.contadorId = 1L;
    }
    public Usuario crear(String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) throws Exception {
        if (mail == null || mail.trim().isEmpty()) {
            throw new Exception("El email no puede estar vacío");
        }
        for (Usuario u : usuarios) {
            if (u.getMail().equalsIgnoreCase(mail) && !u.isEliminado()) {
                throw new Exception("El email ya está registrado");
            }
        }
        Usuario nuevoUsuario = new Usuario(contadorId++, nombre, apellido, mail, celular, contraseña, rol);
        usuarios.add(nuevoUsuario);
        return nuevoUsuario;
    }
    public List<Usuario> listarActivos() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }
    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("El usuario con ID " + id + " no existe o fue eliminado");
    }
    public void modificar(Long id, String nombre, String apellido, String mail, String celular, String contraseña, Rol rol) throws Exception {
        Usuario u = buscarPorId(id);
        if (mail != null && !mail.trim().isEmpty() && !mail.equalsIgnoreCase(u.getMail())) {
            for (Usuario existente : usuarios) {
                if (existente.getMail().equalsIgnoreCase(mail) && !existente.isEliminado()) {
                    throw new Exception("El email ya está registrado por otro usuario");
                }
            }
            u.setMail(mail);
        }
        if (nombre != null && !nombre.trim().isEmpty()) u.setNombre(nombre);
        if (apellido != null && !apellido.trim().isEmpty()) u.setApellido(apellido);
        if (celular != null && !celular.trim().isEmpty()) u.setCelular(celular);
        if (contraseña != null && !contraseña.trim().isEmpty()) u.setContraseña(contraseña);
        if (rol != null) u.setRol(rol);
    }

    public void eliminarLogico(Long id) throws EntidadNoEncontradaException {
        Usuario u = buscarPorId(id);
        u.setEliminado(true);
    }
}