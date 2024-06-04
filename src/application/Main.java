package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			primaryStage.setTitle("Battleship");
						
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,700,700);
            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
