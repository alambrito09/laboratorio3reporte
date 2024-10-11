package org.principal.Reportes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.principal.basededatos.model.Producto;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PdfReport {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    // Generar reporte
    public void generateProductReport(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        addTitle(document);  // Añadir título al reporte
        addProductTable(document, productos);  // Añadir la tabla con los productos

        // Manejo del código QR con un bloque try-catch
        try {
            addQrCode(document, "https://www.google.com"); // Añadir el código QR
        } catch (Exception e) {
            System.out.println("Error al generar el código QR: " + e.getMessage());
        }

        document.close();
    }

    // Añadir título al reporte
    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Reporte de Productos", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);  // Espacio entre título y tabla
    }

    // Añadir el código QR
    private void addQrCode(Document document, String qrCodeData) throws DocumentException, Exception {
        // Generar el código QR
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        // Convertir el código QR a imagen
        Image qrImage = Image.getInstance(pngOutputStream.toByteArray());
        qrImage.setAlignment(Element.ALIGN_CENTER);
        document.add(qrImage);
    }

    // Añadir la tabla con encabezados y datos agrupados por origen
    private void addProductTable(Document document, List<Producto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6);  // 6 columnas: ID, Descripción, Origen, Precio, Existencia, Precio Total
        table.setWidthPercentage(100);
        addTableHeader(table);  // Añadir encabezados
        addRowsGroup(table, productos);  // Añadir filas agrupadas
        document.add(table);
    }

    // Encabezados de la tabla
    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Descripción", "Origen", "Precio", "Existencia", "Precio Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GREEN);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                    table.addCell(header);
                });
    }

    // Añadir las filas agrupadas por origen
    private void addRowsGroup(PdfPTable table, List<Producto> productos) {
        String currentOrigen = null;
        double groupTotalPrecio = 0.0;
        int groupTotalExistencia = 0;

        for (Producto producto : productos) {
            if (currentOrigen == null) {
                // Primer grupo
                currentOrigen = producto.getOrigen();

                // Agregar fila de grupo
                PdfPCell groupCell = new PdfPCell(new Phrase("Grupo: " + currentOrigen, NORMAL_FONT));
                groupCell.setColspan(6);
                table.addCell(groupCell);
            } else if (!producto.getOrigen().equals(currentOrigen)) {
                // El grupo ha cambiado, imprimir totales del grupo anterior
                PdfPCell totalCellLabel = new PdfPCell(new Phrase("Total Grupo " + currentOrigen, NORMAL_FONT));
                totalCellLabel.setColspan(4);
                table.addCell(totalCellLabel);

                table.addCell(new Phrase(String.valueOf(groupTotalPrecio), NORMAL_FONT));
                table.addCell(new Phrase(String.valueOf(groupTotalExistencia), NORMAL_FONT));

                // Reiniciar totales
                groupTotalPrecio = 0.0;
                groupTotalExistencia = 0;

                // Actualizar el origen actual al nuevo grupo
                currentOrigen = producto.getOrigen();

                // Agregar fila de nuevo grupo
                PdfPCell groupCell = new PdfPCell(new Phrase("Grupo: " + currentOrigen, NORMAL_FONT));
                groupCell.setColspan(6);
                table.addCell(groupCell);
            }

            // Agregar fila del producto
            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));

            if (producto.getOrigen().equals("China")) {
                PdfPCell cell = new PdfPCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
                cell.setBackgroundColor(BaseColor.GREEN);
                table.addCell(cell);
            } else {
                table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            }
            table.addCell(new Phrase(String.format("Q%.2f", producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));

            // Cálculo del precio total
            double precioTotal = producto.getPrecio() * producto.getExistencia();
            table.addCell(new Phrase(String.format("Q%.2f", precioTotal), NORMAL_FONT));

            // Acumular totales por grupo
            groupTotalPrecio += producto.getPrecio();
            groupTotalExistencia += producto.getExistencia();
        }

        // Imprimir totales para el último grupo
        if (currentOrigen != null) {
            PdfPCell totalCellLabel = new PdfPCell(new Phrase("Total Grupo " + currentOrigen, NORMAL_FONT));
            totalCellLabel.setColspan(4);
            table.addCell(totalCellLabel);

            table.addCell(new Phrase(String.valueOf(groupTotalPrecio), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(groupTotalExistencia), NORMAL_FONT));
        }
    }
}
