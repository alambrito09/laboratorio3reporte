package org.principal.formularios.prodcutos;

import com.itextpdf.text.DocumentException;
import org.principal.Reportes.PdfReport;
import org.principal.basededatos.dao.ProductoDAO;
import org.principal.basededatos.model.Producto;
import org.principal.basededatos.service.ProductoService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class frmProductos {
    private JPanel lblDescripcion;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JTextField textFieldProducto;
    private JTextField textFieldDescripcion;
    private JLabel lblOrigen;
    private JButton buttonLimpiar;
    private JButton buttonGrabar;
    private JButton buttonBuscar;
    private JComboBox<String> comboBoxOrigen;  // Aseguramos que maneje strings
    private JButton buttonSalir;
    private JComboBox comboBoxreporte;
    private JButton imprimirReporteButton;

    // Instancia del DAO
    private ProductoDAO productoDAO;

    public frmProductos() {
        productoDAO = new ProductoDAO();  // Inicializamos el DAO
        comboBoxreporte.addItem("Existencia menor a 20 unidades");
        comboBoxreporte.addItem("Precios mayores a 2000");
        comboBoxreporte.addItem("Por país específico");


        // Inicializar comboBox con opciones

        comboBoxOrigen.addItem("China");
        comboBoxOrigen.addItem("Japón");
        comboBoxOrigen.addItem("Corea");
        comboBoxOrigen.addItem("Estados Unidos");
        comboBoxOrigen.addItem("México");
        comboBoxOrigen.addItem("Brasil");
        comboBoxOrigen.addItem("Alemania");
        comboBoxOrigen.addItem("Francia");
        comboBoxOrigen.addItem("Reino Unido");
        comboBoxOrigen.addItem("Italia");
        comboBoxOrigen.addItem("España");
        comboBoxOrigen.addItem("India");
        comboBoxOrigen.addItem("Rusia");
        comboBoxOrigen.addItem("Canadá");
        comboBoxOrigen.addItem("Australia");
        comboBoxOrigen.addItem("Argentina");
        comboBoxOrigen.addItem("Chile");
        comboBoxOrigen.addItem("Sudáfrica");
        comboBoxOrigen.addItem("Egipto");
        comboBoxOrigen.addItem("Arabia Saudita");
        comboBoxOrigen.addItem("Indonesia");
        comboBoxOrigen.addItem("Vietnam");
        comboBoxOrigen.addItem("Tailandia");
        comboBoxOrigen.addItem("Filipinas");
        comboBoxOrigen.addItem("Malasia");
        comboBoxOrigen.addItem("Singapur");
        comboBoxOrigen.addItem("Nueva Zelanda");
        comboBoxOrigen.addItem("Perú");
        comboBoxOrigen.addItem("Colombia");
        comboBoxOrigen.addItem("Venezuela");
        comboBoxOrigen.addItem("Uruguay");
        comboBoxOrigen.addItem("Paraguay");
        comboBoxOrigen.addItem("Bolivia");
        comboBoxOrigen.addItem("Ecuador");
        comboBoxOrigen.addItem("Guatemala");
        comboBoxOrigen.addItem("Honduras");
        comboBoxOrigen.addItem("El Salvador");
        comboBoxOrigen.addItem("Nicaragua");
        comboBoxOrigen.addItem("Costa Rica");
        comboBoxOrigen.addItem("Panamá");
        comboBoxOrigen.addItem("Cuba");
        comboBoxOrigen.addItem("Puerto Rico");
        comboBoxOrigen.addItem("República Dominicana");
        comboBoxOrigen.addItem("Turquía");
        comboBoxOrigen.addItem("Irán");
        comboBoxOrigen.addItem("Pakistán");
        comboBoxOrigen.addItem("Bangladés");
        comboBoxOrigen.addItem("Nepal");
        comboBoxOrigen.addItem("Sri Lanka");
        comboBoxOrigen.addItem("Nigeria");
        comboBoxOrigen.addItem("Kenya");
        comboBoxOrigen.addItem("Etiopía");
        comboBoxOrigen.addItem("Marruecos");
        comboBoxOrigen.addItem("Túnez");
        comboBoxOrigen.addItem("Argelia");
        comboBoxOrigen.addItem("Israel");
        comboBoxOrigen.addItem("Palestina");
        comboBoxOrigen.addItem("Jordania");
        comboBoxOrigen.addItem("Emiratos Árabes Unidos");
        comboBoxOrigen.addItem("Qatar");
        comboBoxOrigen.addItem("Kuwait");
        comboBoxOrigen.addItem("Omán");


        // Acción del botón "Limpiar"
        buttonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldProducto.setText("");
                textFieldDescripcion.setText("");
                comboBoxOrigen.setSelectedIndex(0);  // Selecciona la primera opción
            }
        });

        // Acción del botón "Grabar"
        buttonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear un nuevo producto con los valores del formulario
                Producto producto = new Producto();
                producto.setDescripcion(textFieldDescripcion.getText());
                producto.setOrigen(comboBoxOrigen.getSelectedItem().toString());

                try {
                    productoDAO.insertar(producto);
                    JOptionPane.showMessageDialog(null, "Producto guardado correctamente con ID: " + producto.getIdProducto());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Acción del botón "Buscar"
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el ID del producto del campo de texto
                int idProducto;
                try {
                    idProducto = Integer.parseInt(textFieldProducto.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
                    return;
                }

                try {
                    Producto producto = productoDAO.obtenerPorId(idProducto);
                    if (producto != null) {
                        // Llenar los campos con la información del producto
                        textFieldDescripcion.setText(producto.getDescripcion());
                        comboBoxOrigen.setSelectedItem(producto.getOrigen());
                        JOptionPane.showMessageDialog(null, "Producto encontrado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        // Acción del botón "Salir"
        buttonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Cerrar la aplicación
            }
        });
        imprimirReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filtroSeleccionado = comboBoxreporte.getSelectedItem().toString();
                generarReporteSegunFiltro(filtroSeleccionado);
            }
        });

    }
    private void generarReporteSegunFiltro(String filtro) {
        ProductoService productoService = new ProductoService();
        List<Producto> productos = null;

        try {
            if (filtro.equals("Existencia menor a 20 unidades")) {
                productos = productoService.obtenerTodosmenores30("existencia < 20");
            } else if (filtro.equals("Precios mayores a 2000")) {
                productos = productoService.obtenerTodosmenores30("precio > 2000");
            } else if (filtro.equals("Por país específico")) {
                String paisSeleccionado = comboBoxOrigen.getSelectedItem().toString();
                productos = productoService.obtenerTodosmenores30("origen = '" + paisSeleccionado + "'");
            }

            // Generar el reporte PDF
            new PdfReport().generateProductReport(productos, "D:\\hola\\reporte_filtrado.pdf");
            JOptionPane.showMessageDialog(null, "Reporte generado correctamente.");
        } catch (SQLException | DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmProductos");
        frmProductos form = new frmProductos();

        // Asegurarse de que todos los componentes se agregan al panel correctamente
        frame.setContentPane(form.lblDescripcion);  // lblDescripcion es el JPanel principal
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centrar la ventana
        frame.setLocationRelativeTo(null);

        // Ajustar el tamaño de la ventana y hacerla visible
        frame.pack();
        frame.setVisible(true);
    }
}

