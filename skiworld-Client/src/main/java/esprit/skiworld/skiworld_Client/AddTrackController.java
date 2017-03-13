package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.ObjectUtils.Null;
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
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AddTrackController implements Initializable {

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
	@FXML
	private TextField TitleTF;
	@FXML
	private TextArea DescriptionTF;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		diff.setValue("Hard");
		diff.setItems(comboList);
	}

	@FXML
	private void Add() {
		Track track = new Track();

		int ok = 0;

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
			String newDiff = diff.getValue();

			Float newLength = Float.valueOf(length.getText());
			Float newPrice = Float.valueOf(price.getText());
			track.setDifficulty(newDiff);
			track.setLength(Float.valueOf(newLength));
			track.setPrice(Float.valueOf(newPrice));
			track.setDescription(DescriptionTF.getText());
			track.setTitle(TitleTF.getText());
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

				proxy.addTrack(track);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// les alerts
			Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).
					title("Action Completed").text("The Track was successfuly Added");
			notBuilder.showConfirm();
			
		}
		TrackController.s.close();
	}

	@FXML
	private void Cancel() {
		//diff.clear();
		length.clear();
		price.clear();
	}

}
