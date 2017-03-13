package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Box;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class MainPageController implements Initializable {
    ParallelTransition pat;
    PathTransition pt = new PathTransition();
    PathTransition pt2 = new PathTransition();
    PathTransition pt3 = new PathTransition();
    @FXML
    private Box box;
    @FXML
    private Ellipse skiier;
    @FXML
    private Ellipse skiier1;
    @FXML
    private Ellipse skiier11;

    /**
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        pt = new PathTransition();
        Path pl = new Path();
        pl.getElements().add(new MoveTo(0, 0));
        pl.getElements().add(new LineTo(100, 10));
        pl.getElements().add(new LineTo(200, 7));
        pl.getElements().add(new LineTo(320, 25));
        pl.getElements().add(new LineTo(440, 17));
        pl.getElements().add(new LineTo(600, 70));
        pl.getElements().add(new LineTo(690, 90));
        pl.getElements().add(new LineTo(790, 70));
        pl.getElements().add(new LineTo(900, 78));

        pt.setNode(skiier);
        pt.setAutoReverse(true);
        pt.setDuration(Duration.seconds(9));
        pt.setPath(pl);
        pt.setCycleCount(PathTransition.INDEFINITE);

        pt2 = new PathTransition();
        Path pl1 = new Path();

        pl1.getElements().add(new MoveTo(-10, 10));
        pl1.getElements().add(new LineTo(230, -170));
        pl1.getElements().add(new LineTo(115, -228));
        pl1.getElements().add(new LineTo(195, -265));
        pl1.getElements().add(new LineTo(175, -275));
        pt2.setPath(pl1);
        pt2.setNode(skiier1);
        pt2.setDuration(Duration.seconds(5));
        pt2.setAutoReverse(true);
        pt2.setCycleCount(PathTransition.INDEFINITE);

        pt3 = new PathTransition();
        Path pl11 = new Path();
        pl11.getElements().add(new MoveTo(-10, 10));
        pl11.getElements().add(new LineTo(75, -55));
        pl11.getElements().add(new LineTo(130, -35));
        pl11.getElements().add(new LineTo(185, -30));
        pl11.getElements().add(new LineTo(285, -45));
        pl11.getElements().add(new LineTo(415, -40));
        pl11.getElements().add(new LineTo(475, -60));
        pl11.getElements().add(new LineTo(600, -150));
        pl11.getElements().add(new LineTo(700, -185));
        pl11.getElements().add(new LineTo(855, -195));
        pl11.getElements().add(new LineTo(900, -190));

        pt3.setPath(pl11);
        pt3.setNode(skiier11);
        pt3.setDuration(Duration.seconds(9));
        pt3.setAutoReverse(true);
        pt3.setCycleCount(PathTransition.INDEFINITE);

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setSpecularColor(Color.TRANSPARENT);
        redMaterial.setDiffuseColor(Color.TRANSPARENT);
        box.setMaterial(redMaterial);

        pat = new ParallelTransition(pt, pt2, pt3);
        pat.play();
    }

    @FXML
    private void clickCube(MouseEvent event) throws IOException {
        MainApp.changeScene("/fxml/CommentsAndReports.fxml", "Comments And Reports");
        pat.pause();
    }
    
    @FXML
    private void clickRestau(ActionEvent event) {
    	System.out.println("HELLOOOO");
    	pat.pause();
    }

    @FXML
    private void clickHotel(ActionEvent event) {
    	System.out.println("Good Morning");
    	pat.pause();
    }

    @FXML
    private void clickSkiTrips(ActionEvent event) throws IOException {
    	System.out.println("Bye Bye");
    	MainApp.changeScene("/fxml/Track.fxml", "Comments And Reports");
    	pat.pause();
    }
    
    @FXML
    private void clickTransport(ActionEvent event) throws IOException {
    	MainApp.changeScene("/fxml/Transport.fxml","Transportation");
    	pat.pause();
    }
    
    @FXML
    public void clickSchool (ActionEvent event) {
    	try {
			MainApp.changeScene("/fxml/Training.fxml", "Ski School");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
