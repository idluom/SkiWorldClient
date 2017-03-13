package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Entity.Track;
import Service.TrackEJBRemote;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class UpdateTrackController implements Initializable {
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
		diff.setText(TrackController.comp.getDifficulty().toString());
		price.setText(Float.valueOf(TrackController.comp.getPrice()).toString());
		length.setText(Float.valueOf(TrackController.comp.getLength()).toString());
	}

	@FXML
	private void Update() {

		int ok = 0;
		if (diff.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Difficulty Field");
			alert.showAndWait();
			ok = 1;
			System.out.println("ffff");
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
				// ObservableList<Track> champs =
				// FXCollections.observableArrayList(proxy.findAll());
				Track track = new Track();

				track = proxy.findTrackById(TrackController.comp.getIdTrack());
				track.setDifficulty(diff.getText());
				;
				track.setLength(Float.valueOf(length.getText()));
				track.setPrice(Float.valueOf(price.getText()));
				proxy.updateTrack(track);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// les alerts
			Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
					.title("Action Completed").text("The Track was successfuly Updated");
			notBuilder.showConfirm();
		}
	}

	@FXML
	private void Cancel() {
		diff.clear();
		length.clear();
		price.clear();
	}

}
