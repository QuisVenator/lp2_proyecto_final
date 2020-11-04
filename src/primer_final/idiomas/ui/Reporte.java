/*
Para generar archivos pdf, este programa utiliza la libreria itext7 en su forma compilada.
El código fuente está disponbible libremente en https://github.com/itext/itext7
*/

package primer_final.idiomas.ui;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TabAlignment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Reporte {
    private String direction;
    private PdfDocument archivoPdf;
    public Document documento;
    private PdfFont fuente;
    private PdfWriter writer;
    public App app;
    public File file;
    
    Reporte(App app) {
        this.app = app;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        file = new File("informe-"+dateFormat.format(new Date())+".pdf");
        direction = file.getAbsolutePath();
        this.writer = null;
        try {
            this.writer = new PdfWriter(direction);    // Instaciación clase escritura.        
        } catch (FileNotFoundException e) {
            System.out.println("E: " + e);
        }
        this.archivoPdf = new PdfDocument(this.writer);
        this.documento = new Document(archivoPdf,PageSize.A4);
        try {
            this.fuente = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        } catch (IOException ex) {
            System.out.println("Error Fuentes: " + ex);
        }
    }
    
    /**
    *Agrega el título principal con el nro de cuenta, centrado.
    * @param nroCuenta nro de la cuenta.
    */
    public void addTituloPrincipal(String nroCuenta){
        List<TabStop> tabStops = new ArrayList<>();
        nroCuenta = app.getLanguage().getString("informe")+" " + nroCuenta;
        Rectangle pageSize = archivoPdf.getDefaultPageSize();
        float anchoDePagina = pageSize.getWidth() - documento.getLeftMargin() - documento.getRightMargin();
        tabStops.add(new TabStop(anchoDePagina / 2, TabAlignment.CENTER));
        tabStops.add(new TabStop(anchoDePagina, TabAlignment.LEFT));
        Paragraph titulo = new Paragraph().addTabStops(tabStops).setFont(fuente).setFontSize(20f);
        titulo.add(new Tab());
        titulo.add(nroCuenta);
        titulo.add(new Tab());
        documento.add(titulo);
    }
    
    /**
    * Genera el párrafo con los datos de la cuenta.
    * @param titular Nombre del titular de la cuenta.
    * @param cuenta Nro de cuenta.
    * @param saldoCuenta Saldo actual en la cuenta.
    */
    public void addDatosCliente(String titular, String cuenta, String saldoCuenta) {
        Paragraph writeLinea = new Paragraph().setFont(fuente).setFontSize(16f);
        writeLinea.add(new Tab());
        writeLinea.add(app.getLanguage().getString("cuenta") + " ");
        writeLinea.add(new Tab());
       
        Paragraph writeTitular = new Paragraph().setFont(fuente).setFontSize(14f);
        writeTitular.add(new Tab());
        writeTitular.add(app.getLanguage().getString("titular") + " " + titular);
        writeTitular.add(new Tab());

        
        Paragraph writeCuenta = new Paragraph().setFont(fuente).setFontSize(14f);
        writeCuenta.add(new Tab());
        writeCuenta.add(app.getLanguage().getString("nroCuenta") + " " + cuenta);
        writeCuenta.add(new Tab());

        
        Paragraph writeSaldo = new Paragraph().setFont(fuente).setFontSize(14f);
        writeSaldo.add(new Tab());
        writeSaldo.add(app.getLanguage().getString("saldoActual") + " " + saldoCuenta);
        writeSaldo.add(new Tab());


        Paragraph writeFecha = new Paragraph().setFont(fuente).setFontSize(14f);
        writeFecha.add(new Tab());
        writeFecha.add(app.getLanguage().getString("fecha") + " " + new Date());
        writeFecha.add(new Tab());

        documento.add(writeLinea);
        documento.add(writeTitular);
        documento.add(writeCuenta);
        documento.add(writeSaldo);
        documento.add(writeFecha);
        //documento.add(new Paragraph("\n\n"));
    }
    /**
    *Método público para agregar una nueva tabla al pdf.
     * @param nombreTabla titulo que figura antes de la tabla.
     * @param titulosColumnas array string con el encabezado de cada columna.
     * @param datos datos con los que rellenar la tabla.
    */
    public void addTabla(String nombreTabla, String[] titulosColumnas, LinkedList<LinkedList<String>> datos) {
        float [] ancho = new float[titulosColumnas.length];
        for(int i = 0; i < titulosColumnas.length; i++)
            ancho[i] = 450F / titulosColumnas.length;
        try {
            Table tabla = private_addTabla(app.getLanguage().getString(nombreTabla),titulosColumnas,datos,ancho);
            this.documento.add(tabla);
            documento.add(new Paragraph("\n\n"));
        } catch (IOException e) {
            System.out.println("E: " + e);
        }    
    }
    
    /**
    * Método para generar una tabla en el pdf. 
    * @param nombreTabla titulo que figura antes de la tabla.
    * @param titulosColumnas array string con el encabezado de cada columna.
    * @param datos datos con los que rellenar la tabla.
    * @param tamanho dimensiones de la tabla.
    * @return Objeto Table para agregar al doc pdf.
    */
    private Table private_addTabla(String nombreTabla, String[] titulosColumnas, LinkedList<LinkedList<String>> datos, float[] tamanho) throws IOException {
        Table tabla = new Table(tamanho).setHorizontalAlignment(HorizontalAlignment.CENTER);
        Paragraph writeLinea = new Paragraph().setFont(fuente).setFontSize(16f).setHorizontalAlignment(HorizontalAlignment.RIGHT);
        writeLinea.add(new Tab());
        writeLinea.add(nombreTabla);
        writeLinea.add(new Tab());
        documento.add(writeLinea);
        
        //Definimos los encabezados.
        for (String titulosColumna : titulosColumnas) {
            tabla.addCell(new Cell().add(new Paragraph(titulosColumna)).setFont(fuente).setFontSize(16f));
        }
        
        datos.forEach(linea -> {
            linea.forEach(campo -> {
                tabla.addCell(new Cell().add(new Paragraph(campo)));
            });
        });
        return tabla;
    }
    
    /**
    *Cierra el documento. Vuelca el contenido en el pdf.
    */
    public void cerrar() {
        this.documento.close();
    }
}