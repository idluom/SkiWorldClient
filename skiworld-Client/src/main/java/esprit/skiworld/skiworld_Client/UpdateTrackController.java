package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

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
import javafx.util.Duration;

public class UpdateTrackController implements Initializable {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane TableTrack;
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
		TableTrack.setVisible(false);
		TitleTF.setText(TrackController.comp.getTitle());
		DescriptionTF.setText(TrackController.comp.getDescription());
		diff.setValue("Hard");
		diff.setItems(comboList);
		price.setText(Float.valueOf(TrackController.comp.getPrice()).toString());
		length.setText(Float.valueOf(TrackController.comp.getLength()).toString());
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
				
				TrackController.comp.setDifficulty(diff.getValue());
				TrackController.comp.setDescription(DescriptionTF.getText());
				TrackController.comp.setTitle(TitleTF.getText());
				TrackController.comp.setLength(Float.valueOf(length.getText()));
				TrackController.comp.setPrice(Float.valueOf(price.getText()));
				new TrackBusiness().updateTrack(TrackController.comp);
			
			
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
