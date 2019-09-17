package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application implements EventHandler<ActionEvent>{
	private Button btnSend;
	private Button btnClear;
	private ComboBox<String> cmbDeportes;
	private TextField txtNombre;
	private TextField txtApellido;
	private TextField txtEdad;
	@Override
	public void start(Stage primaryStage) {
		try {
			//seccion superior
			HBox hboxTitle = new HBox();
			Label lblTitle = new Label("Sobre mi..");
			hboxTitle.getChildren().addAll(lblTitle);
			//seccion central
			VBox infoArea = new VBox();
			
			HBox hboxOne = new HBox();
			hboxOne.setSpacing(10);
			hboxOne.setAlignment(Pos.CENTER);
			Label lblNombre = new Label("Ingresa tu nombre");
			lblNombre.setPadding(new Insets(0,5,0,0));
			txtNombre = new TextField();
			hboxOne.getChildren().addAll(lblNombre, txtNombre);
			HBox hboxTwo = new HBox();
			hboxTwo.setSpacing(10);
			hboxTwo.setAlignment(Pos.CENTER);
			Label lblApellido = new Label("Ingresa tu Apellido");
			txtApellido = new TextField();
			hboxTwo.getChildren().addAll(lblApellido, txtApellido);
			HBox  hboxThree = new HBox();
			hboxThree.setSpacing(10);
			hboxThree.setAlignment(Pos.CENTER);
			Label lblEdad = new Label("Ingresa tu Edad");
			lblEdad.setPadding(new Insets(0,15,0,0));
			txtEdad = new TextField();
			hboxThree.getChildren().addAll(lblEdad, txtEdad);
			//Nuevo componente
			HBox hboxFour = new HBox();
			hboxFour.setAlignment(Pos.CENTER);
			cmbDeportes = new ComboBox<String>();
			cmbDeportes.setOnAction(this);
			cmbDeportes.setPromptText("Selecciona tu deporte favorito...");
			cmbDeportes.getItems().addAll("football","baseball","baketball");
			hboxFour.getChildren().addAll(cmbDeportes);
			
			infoArea.getChildren().addAll(hboxOne, hboxTwo, hboxThree, hboxFour);
			infoArea.setSpacing(10);
			infoArea.setAlignment(Pos.CENTER);
			
			//Parte inferior
			HBox buttonArea = new HBox();
			btnSend = new Button("Enviar");
			btnSend.setOnAction(this);
			btnClear = new Button("Limpiar");
			btnClear.setOnAction(this);
			buttonArea.getChildren().addAll(btnSend, btnClear);
			buttonArea.setSpacing(10);
			buttonArea.setAlignment(Pos.CENTER_RIGHT);
			
			BorderPane root = new BorderPane();
			root.setTop(hboxTitle);
			root.setCenter(infoArea);
			root.setBottom(buttonArea);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ejemplo ComboBox");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() instanceof Button){
			if((Button)event.getSource() == btnSend){
				if(txtNombre.getText() != "" && txtApellido.getText() != "" && txtEdad.getText() != "" && cmbDeportes.getValue() != null){
					Alert alertInfo = new Alert(AlertType.INFORMATION);
					alertInfo.setTitle("Informacion recolectada");
					alertInfo.setHeaderText("Hola : "+txtNombre.getText());
					alertInfo.setContentText("Tu apellido es "+txtApellido.getText()
											+"\nTienes "+txtEdad.getText()+" años"
											+"\nTu deporte favorito es : "+cmbDeportes.getValue());
					alertInfo.showAndWait();
				}else{
					Alert alertInfo = new Alert(AlertType.ERROR);
					alertInfo.setTitle("ERROR !!");
					alertInfo.setHeaderText("Falta informacion!");
					alertInfo.setContentText("Querio usuario favor de llenar todos los campos");
					alertInfo.showAndWait();
					
				}
			} else if((Button)event.getSource() == btnClear){
				txtApellido.setText("");
				txtEdad.setText("");
				txtNombre.setText("");
				cmbDeportes.setValue(null);
			}
		}else if(event.getSource() instanceof ComboBox<?>){
			System.out.println("Deporte : "+cmbDeportes.getValue());
		}
	}
}
