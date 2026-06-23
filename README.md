classDiagram
    class Base {
        -Long id
        -boolean eliminado
        -LocalDateTime createdAt
    }
    
    class Categoria {
        -String nombre
        -String descripcion
    }
    
    class Producto {
        -String nombre
        -Double precio
        -String descripcion
        -int stock
        -String imagen
        -Boolean disponible
    }
    
    class Usuario {
        -String nombre
        -String apellido
        -String mail
        -String celular
        -String contraseña
        -Rol rol
    }
    
    class Pedido {
        -LocalDate fecha
        -Estado estado
        -Double total
        -FormaPago formaPago
        +addDetallePedido(int, Double, Producto) void
        +findeDetallePedidoByProducto(Producto) DetallePedido
        +deleteDetallePedidoByProducto(Producto) void
    }
    
    class DetallePedido {
        -int cantidad
        -Double subtotal
    }
    
    class Calculable {
        <<interface>>
        +calcularTotal() void
    }
    
    class Rol {
        <<enumeration>>
        ADMIN
        USUARIO
    }
    
    class FormaPago {
        <<enumeration>>
        TARJETA
        TRANSFERENCIA
        EFECTIVO
    }
    
    class Estado {
        <<enumeration>>
        PENDIENTE
        CONFIRMADO
        TERMINADO
        CANCELADO
    }

    Base <|-- Categoria
    Base <|-- Producto
    Base <|-- Usuario
    Base <|-- Pedido
    Base <|-- DetallePedido
    
    Calculable <|.. Pedido
    
    Categoria "1" <-- "m" Producto
    Usuario "1" <-- "m" Pedido
    Producto "1" <-- "m" DetallePedido
    Pedido "1" *-- "m" DetallePedido
