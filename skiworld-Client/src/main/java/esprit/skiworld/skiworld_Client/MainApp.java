package esprit.skiworld.skiworld_Client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

public static Stage s = new Stage();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			changeScene("/fxml/Authentication.fxml", "Hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
        MainApp.s.show();
	}
    
    public static void changeScene(String fxmlFile, String StageTitle) throws IOException {
        Parent root = FXMLLoader.load(MainApp.class.getResource(fxmlFile));
        Scene scene = new Scene(root);
        MainApp.s.setScene(scene);
        MainApp.s.setTitle(StageTitle);
    }
    
    
}
