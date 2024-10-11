package org.principal.basededatos.service;

import org.principal.basededatos.model.Producto;
import org.principal.basededatos.dao.ProductoDAO;

import java.sql.SQLException;
import java.util.List;

public class ProductoService {
    private ProductoDAO productoDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
    }

    public void crearProducto(Producto producto) throws SQLException {
        productoDAO.insertar(producto);
    }

    public Producto obtenerProducto(int id) throws SQLException {
        return productoDAO.obtenerPorId(id);
    }

    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        return productoDAO.obtenerTodos();
    }
    public List<Producto> obtenerTodosmenores30(String condicion) throws SQLException {
        return productoDAO.obtenerTodosmenores30(condicion);
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(int id) throws SQLException {
        productoDAO.eliminar(id);
    }
}
