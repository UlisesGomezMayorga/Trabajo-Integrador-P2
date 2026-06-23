package integrado.prog2;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService(categoriaService);
    private static UsuarioService usuarioService = new UsuarioService();
    private static PedidoService pedidoService = new PedidoService(usuarioService, productoService);

    public static void main(String[] args) {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenuPrincipal();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 0:
                        System.out.println("\nSaliendo del sistema Food Store...");
                        break;
                    default:
                        System.out.println("\nOpción inválida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: Ingrese un número válido.");
                scanner.nextLine();
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
        System.out.println("1. Categorías");
        System.out.println("2. Productos");
        System.out.println("3. Usuarios");
        System.out.println("4. Pedidos");
        System.out.println("0. Salir");
        System.out.print("Seleccione: ");
    }

    private static void menuCategorias() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        listarCategorias();
                        break;
                    case 2:
                        crearCategoria();
                        break;
                    case 3:
                        editarCategoria();
                        break;
                    case 4:
                        eliminarCategoria();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void listarCategorias() {
        List<Categoria> lista = categoriaService.listarActivas();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay categorías cargadas.");
        } else {
            System.out.println("\nListado de Categorías:");
            for (Categoria c : lista) {
                System.out.println(c.toString());
            }
        }
    }

    private static void crearCategoria() throws Exception {
        System.out.println("\n--- CREAR CATEGORÍA ---");
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese descripción: ");
        String desc = scanner.nextLine();
        Categoria c = categoriaService.crear(nombre, desc);
        System.out.println("Categoría creada con éxito. ID asignado: " + c.getId());
    }

    private static void editarCategoria() throws Exception {
        System.out.println("\n--- EDITAR CATEGORÍA ---");
        listarCategorias();
        System.out.print("\nIngrese el ID de la categoría a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Ingrese nuevo nombre (presione Enter para mantener el actual): ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nueva descripción (presione Enter para mantener la actual): ");
        String desc = scanner.nextLine();
        categoriaService.modificar(id, nombre, desc);
        System.out.println("Categoría editada correctamente.");
    }

    private static void eliminarCategoria() throws Exception {
        System.out.println("\n--- ELIMINAR CATEGORÍA ---");
        listarCategorias();
        System.out.print("\nIngrese el ID de la categoría a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("¿Está seguro que desea eliminarla? (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            categoriaService.eliminarLogico(id);
            System.out.println("Categoría eliminada con éxito.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private static void menuProductos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        listarProductos();
                        break;
                    case 2:
                        crearProducto();
                        break;
                    case 3:
                        editarProducto();
                        break;
                    case 4:
                        eliminarProducto();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static void listarProductos() {
        List<Producto> lista = productoService.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay productos cargados.");
        } else {
            System.out.println("\nListado de Productos:");
            for (Producto p : lista) {
                System.out.println(p.toString());
            }
        }
    }
    private static void crearProducto() throws Exception {
        System.out.println("\n--- CREAR PRODUCTO ---");
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese descripción: ");
        String desc = scanner.nextLine();
        System.out.print("Ingrese precio: ");
        Double precio = scanner.nextDouble();
        System.out.print("Ingrese stock inicial: ");
        int stock = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Ingrese URL o ruta de la imagen (o Enter para omitir): ");
        String imagen = scanner.nextLine();
        System.out.println("\nCategorías disponibles:");
        listarCategorias();
        System.out.print("Ingrese el ID de la categoría a la que pertenece: ");
        Long idCat = scanner.nextLong();
        scanner.nextLine();
        Producto p = productoService.crear(nombre, precio, desc, stock, imagen, true, idCat);
        System.out.println("Producto creado con éxito. ID asignado: " + p.getId());
    }
    private static void editarProducto() throws Exception {
        System.out.println("\n--- EDITAR PRODUCTO ---");
        listarProductos();
        System.out.print("\nIngrese el ID del producto a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); 
        System.out.print("Ingrese nuevo nombre (presione Enter para mantener el actual): ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nuevo precio (-1 para mantener el actual): ");
        Double precio = scanner.nextDouble();
        System.out.print("Ingrese nuevo stock (-1 para mantener el actual): ");
        int stock = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Ingrese nuevo ID de categoría (-1 para mantener la actual): ");
        Long idCat = scanner.nextLong();
        scanner.nextLine();
        Double precioFinal = (precio != -1) ? precio : null;
        Integer stockFinal = (stock != -1) ? stock : null;
        Long idCatFinal = (idCat != -1) ? idCat : null;
        productoService.modificar(id, nombre, precioFinal, stockFinal, idCatFinal);
        System.out.println("Producto editado correctamente.");
    }
    private static void eliminarProducto() throws Exception {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        listarProductos();
        System.out.print("\nIngrese el ID del producto a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("¿Está seguro que desea eliminarlo? (S/N): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            productoService.eliminarLogico(id);
            System.out.println("Producto eliminado con éxito.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private static void menuUsuarios() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        listarUsuarios();
                        break;
                    case 2:
                        crearUsuario();
                        break;
                    case 3:
                        editarUsuario();
                        break;
                    case 4:
                        eliminarUsuario();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static void listarUsuarios() {
        List<Usuario> lista = usuarioService.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay usuarios cargados.");
        } else {
            System.out.println("\nListado de Usuarios:");
            for (Usuario u : lista) {
                System.out.println(u.toString());
            }
        }
    }
    private static void crearUsuario() throws Exception {
        System.out.println("\n--- CREAR USUARIO ---");
        System.out.print("Ingrese nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Ingrese apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("Ingrese email: ");
        String mail = scanner.nextLine();
        
        System.out.print("Ingrese celular: ");
        String celular = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Rol (1. ADMIN, 2. USUARIO): ");
        int opcRol = scanner.nextInt();
        scanner.nextLine();
        integrado.prog2.enums.Rol rol = (opcRol == 1) ? integrado.prog2.enums.Rol.ADMIN : integrado.prog2.enums.Rol.USUARIO;

        Usuario u = usuarioService.crear(nombre, apellido, mail, celular, contraseña, rol);
        System.out.println("Usuario creado con éxito. ID asignado: " + u.getId());
    }
    private static void editarUsuario() throws Exception {
        System.out.println("\n--- EDITAR USUARIO ---");
        listarUsuarios();
        System.out.print("\nIngrese el ID del usuario a editar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("Ingrese nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nuevo apellido (Enter para mantener): ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese nuevo email (Enter para mantener): ");
        String mail = scanner.nextLine();
        System.out.print("Ingrese nuevo celular (Enter para mantener): ");
        String celular = scanner.nextLine();
        System.out.print("Ingrese nueva contraseña (Enter para mantener): ");
        String contraseña = scanner.nextLine();
        System.out.print("Rol (1. ADMIN, 2. USUARIO, 0 para mantener): ");
        int opcRol = scanner.nextInt();
        scanner.nextLine();
        integrado.prog2.enums.Rol rol = null;
        if (opcRol == 1) rol = integrado.prog2.enums.Rol.ADMIN;
        if (opcRol == 2) rol = integrado.prog2.enums.Rol.USUARIO;
        usuarioService.modificar(id, nombre, apellido, mail, celular, contraseña, rol);
        System.out.println("Usuario editado correctamente.");
    }
    private static void eliminarUsuario() throws Exception {
        System.out.println("\n--- ELIMINAR USUARIO ---");
        listarUsuarios();
        System.out.print("\nIngrese el ID del usuario a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.print("¿Está seguro que desea eliminarlo? (S/N): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("S")) {
            usuarioService.eliminarLogico(id);
            System.out.println("Usuario eliminado con éxito.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
    private static void menuPedidos() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear Pedido con Detalles");
            System.out.println("3. Actualizar Estado / Pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione: ");
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
                switch (opcion) {
                    case 1:
                        listarPedidos();
                        break;
                    case 2:
                        crearPedido();
                        break;
                    case 3:
                        actualizarPedido();
                        break;
                    case 4:
                        eliminarPedido();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static void listarPedidos() {
        List<Pedido> lista = pedidoService.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("\nNo hay pedidos cargados.");
        } else {
            System.out.println("\nListado de Pedidos:");
            for (Pedido p : lista) {
                System.out.println(p.toString());
            }
        }
    }
    private static void crearPedido() throws Exception {
        System.out.println("\n--- CREAR PEDIDO ---");
        listarUsuarios();
        System.out.print("Ingrese el ID del usuario: ");
        Long idUsuario = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Formas de Pago: 1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO");
        System.out.print("Seleccione forma de pago: ");
        int opcPago = scanner.nextInt();
        scanner.nextLine();
        integrado.prog2.enums.FormaPago formaPago = integrado.prog2.enums.FormaPago.EFECTIVO;
        if (opcPago == 1) formaPago = integrado.prog2.enums.FormaPago.TARJETA;
        if (opcPago == 2) formaPago = integrado.prog2.enums.FormaPago.TRANSFERENCIA;
        List<Long> idsProductos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();
        boolean agregarMas = true;
        while (agregarMas) {
            listarProductos();
            System.out.print("Ingrese ID del producto: ");
            Long idProd = scanner.nextLong();
            System.out.print("Ingrese cantidad: ");
            int cant = scanner.nextInt();
            scanner.nextLine();
            idsProductos.add(idProd);
            cantidades.add(cant);
            System.out.print("¿Desea agregar otro producto al pedido? (S/N): ");
            String resp = scanner.nextLine();
            if (!resp.equalsIgnoreCase("S")) {
                agregarMas = false;
            }
        }
        Pedido p = pedidoService.crear(idUsuario, formaPago, idsProductos, cantidades);
        System.out.println("Pedido creado con éxito. Total: $" + String.format("%.2f", p.getTotal()));
    }
    private static void actualizarPedido() throws Exception {
        System.out.println("\n--- ACTUALIZAR ESTADO/PAGO DEL PEDIDO ---");
        listarPedidos();
        System.out.print("\nIngrese el ID del pedido: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Nuevo Estado (1. PENDIENTE, 2. CONFIRMADO, 3. TERMINADO, 4. CANCELADO, 0 para omitir): ");
        int opcEst = scanner.nextInt();
        scanner.nextLine();
        integrado.prog2.enums.Estado estado = null;
        if (opcEst == 1) estado = integrado.prog2.enums.Estado.PENDIENTE;
        if (opcEst == 2) estado = integrado.prog2.enums.Estado.CONFIRMADO;
        if (opcEst == 3) estado = integrado.prog2.enums.Estado.TERMINADO;
        if (opcEst == 4) estado = integrado.prog2.enums.Estado.CANCELADO;
        System.out.println("Nueva Forma de Pago (1. TARJETA, 2. TRANSFERENCIA, 3. EFECTIVO, 0 para omitir): ");
        int opcPago = scanner.nextInt();
        scanner.nextLine();
        integrado.prog2.enums.FormaPago pago = null;
        if (opcPago == 1) pago = integrado.prog2.enums.FormaPago.TARJETA;
        if (opcPago == 2) pago = integrado.prog2.enums.FormaPago.TRANSFERENCIA;
        if (opcPago == 3) pago = integrado.prog2.enums.FormaPago.EFECTIVO;
        pedidoService.actualizarEstadoYPago(id, estado, pago);
        System.out.println("Pedido actualizado correctamente.");
    }
    private static void eliminarPedido() throws Exception {
        System.out.println("\n--- ELIMINAR PEDIDO ---");
        listarPedidos();
        System.out.print("\nIngrese el ID del pedido a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("¿Está seguro que desea eliminarlo? (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            pedidoService.eliminarLogico(id);
            System.out.println("Pedido eliminado con éxito.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}