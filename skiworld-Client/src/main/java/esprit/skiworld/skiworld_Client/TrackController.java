package esprit.skiworld.skiworld_Client;

import java.io.IOException;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class TrackController implements Initializable {
	public static Stage s = new Stage();
	MainApp application;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	private TableView<Track> TableTrack;
	@FXML
	private TableColumn<Track, String> lengthCol;
	@FXML
	private TableColumn<Track, String> diffCol;
	@FXML
	private TableColumn<Track, String> priceCol;
	private Task<?> task;
	@FXML
	private TableColumn<Track, String> Title;
	@FXML
	private TextField RechercheTF;
	@FXML
	ImageView loading;
	ObservableList<Track> champs;
	public static Track comp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TableTrack.setVisible(false);
		TableTrack.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
		Title.setCellValueFactory(new PropertyValueFactory<>("title"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		diffCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				TableTrack.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(2), TableTrack);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});
		
			
			ObservableList<Track> champs = FXCollections.observableArrayList(new TrackBusiness().findAll());
			TableTrack.setItems(champs);
		

		RechercheTF.textProperty().addListener(new ChangeListener<Object>() {

			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {

				filterTrackList((String) oldValue, (String) newValue);
			}

		});

	}

	@FXML
	private void DeleteTrack() {
		comp = TableTrack.getSelectionModel().getSelectedItem();
		if (comp == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("No Track Selected");
			alert.showAndWait();
		}

		comp = TableTrack.getSelectionModel().getSelectedItem();

		new TrackBusiness().deleteTrack(comp);
		int selectedIndex = TableTrack.getSelectionModel().getSelectedIndex();
		TableTrack.getItems().remove(selectedIndex);

		Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
				.title("Action Completed").text("The Track was successfuly Deleted");
		notBuilder.showConfirm();

	}

	@FXML
	private void Add() {
		try {

			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/AjoutTrack.fxml"));
			Scene scene = new Scene(root);
			s.setScene(scene);
			s.showAndWait();
		} catch (IOException e) {

			e.printStackTrace();
		}
		champs = FXCollections.observableArrayList(new TrackBusiness().findAll());
		TableTrack.setItems(champs);
	}

	@FXML
	private void Edit() {

		comp = TableTrack.getSelectionModel().getSelectedItem();
		if (comp == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("No Track Selected");
			alert.showAndWait();
		}

		try {
			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/UpdateTrack.fxml"));
			Scene scene = new Scene(root);
			s.setScene(scene);
			s.showAndWait();
		} catch (IOException e) {

			e.printStackTrace();
		}
		champs = FXCollections.observableArrayList(new TrackBusiness().findAll());
		TableTrack.setItems(champs);
	}

	private void filterTrackList(String oldValue, String newValue) {
		ObservableList<Track> filteredList = FXCollections.observableArrayList();

		if (RechercheTF == null || (newValue.length() < oldValue.length()) || newValue == null) {
				ObservableList<Track> champs = FXCollections.observableArrayList(new TrackBusiness().findAll());
				TableTrack.setItems(champs);
		} else {

			newValue = newValue.toUpperCase();

			for (Track tracks : TableTrack.getItems()) {

				String filterDiff = tracks.getDifficulty();

				 Float filterPrice = (tracks.getPrice());

				if (filterDiff.toUpperCase().contains(newValue) || filterPrice.toString().contains(newValue)  ) {

					filteredList.add(tracks);

				}

			}
			TableTrack.setItems(filteredList);
		}
	}

}
