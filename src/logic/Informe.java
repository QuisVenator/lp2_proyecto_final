


package logic;

/**
 *
 * @author ...
 */
import com.itextpdf.io.font.FontConstants;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 *
 * @author ...
 */
public class Informe {
    private String direction;
    private PdfDocument archivoPdf;
    public Document documento;
    private PdfFont fuente;
    private PdfWriter writer;
    
    Informe(String direction) {
        this.direction = direction;
        this.writer = null;
        try {
            this.writer = new PdfWriter(direction);    // Instaciación clase escritura.        
        } catch (FileNotFoundException e) {
            System.out.println("E: " + e);
        }
        this.archivoPdf = new PdfDocument(this.writer);
        this.documento = new Document(archivoPdf,PageSize.A4);
        try {
            this.fuente = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        } catch (IOException ex) {
            System.out.println("Error Fuentes: " + ex);
        }
    }
    /**
     * El orden para implementar es: addTituloPrincipal addDatosClientes, addTabla, cerrar.
     */
    public boolean generateReport() {
        
        return false;
    }
    
    /**
    *Agrega el título principal con el nro de cuenta, centrado.
    * @param nroCuenta nro de la cuenta.
    */
    public void addTituloPrincipal(String nroCuenta){
        List<TabStop> tabStops = new ArrayList<>();
        nroCuenta = "Informe " + nroCuenta;
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
    * @param mes_anho Fecha.
    */
    public void addDatosCliente(String titular, String cuenta, String saldoCuenta, String mes_anho) {
        Paragraph writeLinea = new Paragraph().setFont(fuente).setFontSize(16f);
        writeLinea.add(new Tab());
        writeLinea.add("Cuenta: ");
        writeLinea.add(new Tab());
       
        Paragraph writeTitular = new Paragraph().setFont(fuente).setFontSize(14f);
        writeTitular.add(new Tab());
        writeTitular.add("Titular: " + titular);
        writeTitular.add(new Tab());

        
        Paragraph writeCuenta = new Paragraph().setFont(fuente).setFontSize(14f);
        writeCuenta.add(new Tab());
        writeCuenta.add("Número Cuenta: " + cuenta);
        writeCuenta.add(new Tab());

        
        Paragraph writeSaldo = new Paragraph().setFont(fuente).setFontSize(14f);
        writeSaldo.add(new Tab());
        writeSaldo.add("Saldo Actual: " + saldoCuenta);
        writeSaldo.add(new Tab());


        Paragraph writeFecha = new Paragraph().setFont(fuente).setFontSize(14f);
        writeFecha.add(new Tab());
        writeFecha.add("Saldo Actual: " + saldoCuenta);
        writeFecha.add(new Tab());

        documento.add(writeLinea);
        documento.add(writeTitular);
        documento.add(writeCuenta);
        documento.add(writeSaldo);
        documento.add(writeFecha);
        documento.add(new Paragraph("\n\n"));
    }
    /**
    *Método público para agregar una nueva tabla al pdf.
     * @param nombreTabla titulo que figura antes de la tabla.
     * @param titulosColumnas array string con el encabezado de cada columna.
     * @param datos datos con los que rellenar la tabla.
    */
    public void addTabla(String nombreTabla, String[] titulosColumnas, LinkedList<LinkedList<String>> datos) {
        float [] ancho = {150F, 150F, 150F}; 
        try {
            Table tabla = private_addTabla(nombreTabla,titulosColumnas,datos,ancho);
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
            tabla.addCell(new Cell().add(titulosColumna).setFont(fuente).setFontSize(16f));
        }
        
        //Excepcion si no tienen la misma dimension.
        //No olvidarme agregar!!!!!!!!!!
        int size = datos.get(0).size();
        while(size > 0) {
            datos.forEach(dato -> {
                tabla.addCell(new Cell().add(dato.remove()));
            });            
            size--;
        }
        return tabla;
    }
    
    /**
    *Cierra el documento. Vuelca el contenido en el pdf.
    */
    public void cerrar() {
        this.documento.close();
    }
}