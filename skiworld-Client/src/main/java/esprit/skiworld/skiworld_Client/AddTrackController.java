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
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.TrackBusiness;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AddTrackController implements Initializable {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane TableTrack;
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
		TableTrack.setVisible(false);
		diff.setValue("Hard");
		diff.setItems(comboList);
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				TableTrack.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(1), TableTrack);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});
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
			
			new TrackBusiness().addTrack(track);

			// les alerts
			TrackController.s.close();
			if(ok==0){
			Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).
					title("Action Completed").text("The Track was successfuly Added");
			notBuilder.showConfirm();
			}
			
		}
		
	}

	@FXML
	private void Cancel() {
		//diff.clear();
		length.clear();
		price.clear();
	}

}
