package esprit.skiworld.skiworld_Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    public static Stage s1 = new Stage(StageStyle.DECORATED);
    public static Stage s = new Stage();
    
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) {
		s1.setResizable(false);
		s = new Stage(s.getStyle().UNDECORATED);
		try {
			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/Authentication.fxml"));
	        Scene scene = new Scene(root);
	        MainApp.s.setScene(scene);
		} catch (IOException e) {}
        MainApp.s.show();
	}
    
    public static void changeScene(String fxmlFile, String StageTitle) throws IOException {
    	
        Parent root = FXMLLoader.load(MainApp.class.getResource(fxmlFile));
        Scene scene = new Scene(root);
        MainApp.s1.setScene(scene);
        MainApp.s1.setTitle(StageTitle);
        MainApp.s1.show();
    }
    
}
