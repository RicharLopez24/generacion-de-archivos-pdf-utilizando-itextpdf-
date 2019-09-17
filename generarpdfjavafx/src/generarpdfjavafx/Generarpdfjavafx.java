/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generarpdfjavafx;


import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author familia
 */
public class Generarpdfjavafx extends Application {
    
    public static final String dest = "C:\\Users\\familia\\Documents\\NetBeansProjects\\generarpdfjavafx\\boletas con javafx.pdf";
    public static final String ESCU = "C:\\Users\\familia\\Pictures\\escudo edo.png";
    public static final String LOGO = "C:\\Users\\familia\\Pictures\\logocbt.png";
    public static final String dest3 = "C:\\Users\\familia\\Documents\\NetBeansProjects\\javafx\\DATOS AUMNO.xlsx";
    
    private final Label nombre = new Label("nombre del alumno");
    private final Label grado = new Label("grado");
    private final Label grupo = new Label("grupo");
    private final TextField nom = new TextField();
    private final TextField gra = new TextField();
    private final TextField gru = new TextField();
   
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Button btn = new Button();
        
   
        
        VBox info = new VBox();
        HBox caja1 = new HBox();
        caja1.setSpacing(10);
        caja1.setAlignment(Pos.CENTER);
        caja1.getChildren().addAll(nombre,nom);
        
        HBox caja2 = new HBox();
        caja2.setSpacing(10);
        caja2.setAlignment(Pos.CENTER);
        caja2.getChildren().addAll(grado,gra);
        
        HBox caja3 = new HBox();
        caja3.setSpacing(10);
        caja3.setAlignment(Pos.CENTER);
        caja3.getChildren().addAll(grupo,gru);
           
        info.getChildren().addAll(caja1,caja2,caja3);
        info.setSpacing(10);
        info.setAlignment(Pos.CENTER);
        
        
        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 350);
        root.setCenter(info);
        root.setBottom(btn);
        
        //root.getChildren().addAll(caja3);
        primaryStage.setTitle("genarador de pdf");
        primaryStage.setScene(scene);
        btn.setText("generar pdf");
        primaryStage.show();
        
         btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    String nomb = nom.getText();
                    String grad = gra.getText();
                    String grup = gru.getText();
                    new Generarpdfjavafx().crearPdf(dest,nomb,grad,grup);
                    System.out.println("pdf generado");
                } catch (IOException ex) {
                    Logger.getLogger(Generarpdfjavafx.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
         
         
                
       
    }

   
    public static void main(String[] args) {
        launch(args);
    }
    
    //crear pdf
    public void crearPdf(String dest,String nombre,String grado, String grupo) throws IOException {
        Scanner lee = new Scanner(System.in);

        PdfWriter writer = new PdfWriter(dest);//escribe el pdf en la ruta que se le asigna 
        PdfDocument pdf = new PdfDocument(writer);
        Document documento = new Document(pdf, PageSize.Default);

        Image escu = new Image(ImageDataFactory.create(ESCU));          //pasamos la ruta a imageDataFactoryy devuelve un objeto y manar informacion de la imagen que itext puede leer
        escu.scaleAbsolute(130, 60);
        //escu.setTextAlignment(TextAlignment.LEFT);
        Image logo = new Image(ImageDataFactory.create(LOGO));          //pasamos la ruta a imageDataFactoryy devuelve un objeto y manar informacion de la imagen que itext puede leer
        logo.scaleAbsolute(60, 60);
        float suma = 0;
        int con =0;
        //logo.setTextAlignment(TextAlignment.RIGHT);

        //escu.setAlignment();
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLDOBLIQUE);

        PdfFont font1 = PdfFontFactory.createFont(FontConstants.COURIER);

        Table tabla = new Table(new float[]{7, 7, 7});
        tabla.setWordSpacing(0);

        FileInputStream archivo = new FileInputStream(dest3);
        XSSFWorkbook libro = new XSSFWorkbook(archivo);

        XSSFSheet hoja = libro.getSheetAt(0);
        Iterator<Row> filas = hoja.iterator();
        Iterator<org.apache.poi.ss.usermodel.Cell> celdas;

        Row fila;
        org.apache.poi.ss.usermodel.Cell celda = null;
        while (filas.hasNext()) {
            fila = filas.next();
            celdas = fila.cellIterator();

            while (celdas.hasNext()) {
                celda = celdas.next();

                switch (celda.getCellType()) {
                    case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                        //System.out.println(celda.getNumericCellValue());
                        
                        String dato = Double.toString(celda.getNumericCellValue());
                        tabla.addCell(dato);
                        suma = (float) (suma+celda.getNumericCellValue());
                        con++;
                        //tabla.addCell((Cell) celda);
                        break;
                    case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                        //System.out.println(celda.getStringCellValue());
                        String dato1 = celda.getStringCellValue();
                        tabla.addCell(dato1);
                        //tabla.addCell((Cell) celda);
                        break;
                }
                
            }
        }
        libro.close();

        Paragraph p1 = new Paragraph()
                .add(escu).setHorizontalAlignment(HorizontalAlignment.LEFT)
                .add("                                                                                              ")
                .add(logo).setHorizontalAlignment(HorizontalAlignment.RIGHT);
        Paragraph p2 = new Paragraph()//.setFont(font)
                .add("\n________________    BOLETA DE CALIFICACIONES    ________________").setFont(font).setTextAlignment(TextAlignment.CENTER);
        Paragraph p3 = new Paragraph()
                .add("\nLA DIRECCION DE LA ESCUELA                                                        C.C.T 15ECT0166Q").setFont(font1).setFontSize(8f).setTextAlignment(TextAlignment.LEFT);

        //documento.add(logo.setHorizontalAlignment(HorizontalAlignment.RIGHT));
        float promedio = suma/con;
        documento.add(p1.setTextAlignment(TextAlignment.CENTER));
        documento.add(p2);
        documento.add(p3);
        documento.add(new Paragraph("\nCBT No. 3, Zumpango").setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("\nESTABLECIDO EN CALLE SAN JUDAS TADEO No. 101 SAN BARTOLO CUAUTLALPAN").setFont(font1).setFontSize(8f).setTextAlignment(TextAlignment.LEFT));
        documento.add(new Paragraph("\nHACE CONSTAR QUE SEGUN REGISTROS QUE OBRAN EN EL ARCHIVO DE ESTE PLANTEL:").setFont(font1).setFontSize(8f).setTextAlignment(TextAlignment.LEFT));
        documento.add(new Paragraph(nombre).setFont(font).setFontSize(10f).setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("ES ALUMNO(A) DEL "+grado+" SEMENTRE DE:").setFont(font1).setFontSize(8f).setTextAlignment(TextAlignment.LEFT));
        documento.add(new Paragraph(" BACHILLERATO ").setFont(font).setFontSize(10f).setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("\n EN EL GRUPO "+grupo+" SUSTENTO LOS EXAMENES FINALES DE LAS MATERIAS QUE ACONTINUACION SE ANOTAN").setFont(font1).setFontSize(8f).setTextAlignment(TextAlignment.LEFT));   
        documento.add(tabla.setBorder(Border.NO_BORDER).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("PROMEDIO: "+promedio).setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("\nLA CALIFICACION MINIMA APROBATORIA ES DE 6 (SEIS) PUNTOS").setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("ESTA BOLETA NO ES VALIDA SI PRESENTA BORRADURAS O ALTERACIONES").setFont(font1).setFontSize(8f).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP));
        documento.add(new Paragraph("\nZUMPANGO MEX., A LOS '#' DIAS DEL MES DE 'MES' DEL 'AÑO'").setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.TOP));
        documento.add(new Paragraph("\nDIRECTO(A) ESCOLAR").setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.BOTTOM));
        documento.add(new Paragraph("\n \n \n  "));
        documento.add(new Paragraph("__________________________________").setTextAlignment(TextAlignment.CENTER));
        documento.add(new Paragraph("MTRO. JUAN MANUEL LONGINOS CALLEJA").setFont(font).setFontSize(8f).setTextAlignment(TextAlignment.CENTER));

        documento.close();
    }
    
    
    //funcion para recorrer el archivo excel
    public void process(Table tabla, String line, PdfFont font, boolean isHeader){
      StringTokenizer token = new StringTokenizer(line,",");
        while(token.hasMoreTokens()){
            if(isHeader){
               tabla.addHeaderCell(new Cell().add(new Paragraph(token.nextToken()).setFont(font)));
            }else{
              tabla.addCell(new Cell().add(new Paragraph(token.nextToken()).setFont(font)));
        }
      }
    }
}
