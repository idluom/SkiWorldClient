package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Entity.Track;
import Service.TrackEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class UpdateTrackController implements Initializable {
	@FXML 
	private TextField TitleTF;
	@FXML 
	private TextArea DescriptionTF;
	@FXML
	private ChoiceBox<String> diff;
	ObservableList<String> comboList = FXCollections.observableArrayList("Easy", "Amateur", "Average", "Hard",
			"Professional");
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
	ObservableList<Track> champs;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TitleTF.setText(TrackController.comp.getTitle());
		DescriptionTF.setText(TrackController.comp.getDescription());
		diff.setValue("Hard");
		diff.setItems(comboList);
		price.setText(Float.valueOf(TrackController.comp.getPrice()).toString());
		length.setText(Float.valueOf(TrackController.comp.getLength()).toString());
	}

	@FXML
	private void Update() {

		int ok = 0;
		if (TitleTF.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Title Field");
			alert.showAndWait();
			ok = 1;
			
		}
		if (price.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Price Field");
			alert.showAndWait();
			ok = 1;
		}
		if (length.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Length Field");
			alert.showAndWait();
			ok = 1;
		}
		if (ok == 0) {
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
				//champs = FXCollections.observableArrayList(proxy.findAll());
				
				Track track = new Track();

				track = proxy.findTrackById(TrackController.comp.getIdTrack());
				track.setDifficulty(diff.getValue());
				track.setDescription(DescriptionTF.getText());
				track.setTitle(TitleTF.getText());
				track.setLength(Float.valueOf(length.getText()));
				track.setPrice(Float.valueOf(price.getText()));
				proxy.updateTrack(track);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TrackController.s.close();
			
			// les alerts
			Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
					.title("Action Completed").text("The Track was successfuly Updated");
			notBuilder.showConfirm();
		}
		TrainingController.s.close();
	}

	@FXML
	private void Cancel() {
		
		length.clear();
		price.clear();
	}

}
