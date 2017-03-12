package esprit.skiworld.skiworld_Client;

import java.net.URL;

import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.ObjectUtils.Null;

import Entity.Track;
import Service.TrackEJBRemote;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AddTrackController implements Initializable {

	@FXML
    private TextField diff;
    @FXML
    private TextField length;
    @FXML
    private TextField price;
    @FXML 
    private Label pricelab;
    @FXML 
    private Label difflab;
    @FXML 
    private Label lengthlab;
    
  
   
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	@FXML
	private void Add(){
		Track track=new Track();
		
		int ok=0;
		if(diff.getText().toString().equals("")){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("SELECTED");
	        alert.setHeaderText("Empty Difficulty Field");
	        alert.showAndWait();
	        ok=1;
	        System.out.println("ffff");
		}
		if(price.getText().toString().equals("")){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("SELECTED");
	        alert.setHeaderText("Empty Price Field");
	        alert.showAndWait();
	        ok=1;
		}
		if(length.getText().toString().equals("")){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle("SELECTED");
	        alert.setHeaderText("Empty Length Field");
	        alert.showAndWait();
	        ok=1;
		}
		if(ok==0){
			String newDiff = diff.getText();
			
			Float newLength = Float.valueOf(length.getText());
			Float newPrice = Float.valueOf(price.getText());
        track.setDifficulty(newDiff);
        track.setLength(Float.valueOf(newLength));
        track.setPrice(Float.valueOf(newPrice));
        
        InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TrackEJBRemote proxy;
		try {
			proxy = (TrackEJBRemote) ctx.lookup("/skiworld-ejb/TrackEJB!Service.TrackEJBRemote");
			//ObservableList<Track> champs = FXCollections.observableArrayList(proxy.findAll());

			proxy.addTrack(track);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // les alerts
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SELECTED");
        alert.setHeaderText("Track Added");
        alert.showAndWait();
		}
	}
	@FXML
	private void Cancel(){
		diff.clear();
		length.clear();
		price.clear();
	}

}
