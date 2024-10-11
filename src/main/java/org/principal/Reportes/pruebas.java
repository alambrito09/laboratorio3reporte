package org.principal.Reportes;

import org.principal.basededatos.model.Producto;
import org.principal.basededatos.service.ProductoService;

import javax.swing.*;
import java.util.List;

public class pruebas {
    public static void main(String[] args) {
        try {
            // Obtener productos con existencia menor a 20
            List<Producto> productos = new ProductoService().obtenerTodosmenores30("");


            // Generar el reporte en formato PDF
            new PdfReport().generateProductReport(productos, "D:\\hola\\reporte.pdf");

            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
