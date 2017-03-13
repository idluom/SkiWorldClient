package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Entity.Training;
import Entity.Trip;
import Service.TrainingEJBRemote;
import Service.TripEJB;
import Service.TripEJBRemote;
import esprit.skiworld.Business.TripBusiness;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TripsController implements Initializable {
	public static Stage stage = new Stage();
	MainApp mainapp;
	// @FXML
	// private Button AddTrip;

	public static Trip trip;

	@FXML
	private JFXButton AddTrip;

	@FXML
	private JFXButton WatchId;

	@FXML
	private JFXButton DeleteTripId;

	@FXML
	private JFXTextField searchId;

	@FXML
	private TableColumn<Trip, String> DescriptionCol;

	@FXML
	private TableColumn<Trip, Float> PriceCol;

	@FXML
	private TableColumn<Trip, Integer> PlacesCol;

	@FXML
	private TableColumn<Trip, Date> DateCol;

	@FXML
	private TableView<Trip> TableTrip = new TableView<Trip>();

	ObservableList<Trip> champs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		champs = FXCollections.observableArrayList(new TripBusiness().DisplayAllTrips());

		DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		PlacesCol.setCellValueFactory(new PropertyValueFactory<>("number"));
		DateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		TableTrip.setItems(champs);
		TableTrip.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		// TableTrip.setTableMenuButtonVisible(true);
		searchId.textProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {

				try {
					filterTripList((String) oldValue, (String) newValue);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		});

	}

	@FXML
	void AddAction(ActionEvent event) throws IOException, NamingException {

		Parent root = FXMLLoader.load(AddTripsController.class.getResource("/fxml/AddTrips.fxml"));
		stage.setScene(new Scene(root));

		stage.showAndWait();
		champs.removeAll(champs);

		champs = FXCollections.observableArrayList(new TripBusiness().DisplayAllTrips());
		TableTrip.setItems(champs);

	}

	@FXML
	void ClickChangeTrip(MouseEvent event) throws IOException {
		if (event.getClickCount() == 1) {
			Trip t = new Trip();
			t = TableTrip.getSelectionModel().getSelectedItem();
			if (t.getVideo() == null) {
				WatchId.setDisable(true);
			} else {
				WatchId.setDisable(false);
			}
		}
		if (event.getClickCount() == 2) {

			TripsController.setTrip(TableTrip.getSelectionModel().getSelectedItem());

			Parent root = FXMLLoader.load(UpdateTripsController.class.getResource("/fxml/UpdateTrips.fxml"));
			stage.setScene(new Scene(root));
			stage.showAndWait();
			TableTrip.refresh();

		}

	}

	@FXML
	void DeleteTripAction(ActionEvent event) throws NamingException, IOException {

		if (TableTrip.getSelectionModel().getSelectedItem() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Delete");
			alert.setHeaderText("You should Select Trip");
			alert.showAndWait();
		} else {
			Trip t = new Trip();
			t = TableTrip.getSelectionModel().getSelectedItem();
			new TripBusiness().deleteTrip(t);

			Notifications nb = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).title("Delete")
					.text(t.getDescription() + " Has been deleted successfully !").position(Pos.CENTER)
					.hideCloseButton().onAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							MainApp.s.close();
						}
					});
			nb.showConfirm();
			TableTrip.refresh();
			mainapp.changeScene("/fxml/Trips.fxml", "Trips");

		}

	}

	@FXML
	void WatchAction(ActionEvent event) throws IOException {
		Trip t = new Trip();
		t = TableTrip.getSelectionModel().getSelectedItem();
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(t.getVideo()));

	}
	/// ***********************listener

	private void filterTripList(String oldValue, String newValue) throws ParseException {
		ObservableList<Trip> filteredList = FXCollections.observableArrayList();

		if (searchId == null || (newValue.length() < oldValue.length()) || newValue == null) {

			ObservableList<Trip> champs = FXCollections.observableArrayList(new TripBusiness().DisplayAllTrips());
			TableTrip.setItems(champs);

		} else {

			newValue = newValue.toUpperCase();

			for (Trip trip : TableTrip.getItems()) {

				String filterDescription = trip.getDescription();

				if (filterDescription.toUpperCase().contains(newValue)) {

					filteredList.add(trip);

				}
			/*	if(searchId.getText().contains("0123456789"));
				{			
					String t=trip.getPrice()+"";
					if (t.contains(newValue)) {
						{System.out.println(Float.parseFloat(t)+"      AAAAAAAAAAAAA");
						System.out.println(Float.parseFloat(searchId.getText())+"        ZZZZZZZZZzz");
						if(Float.parseFloat(t)<=Float.parseFloat(newValue))
							
						filteredList.add(trip);
					}
					}
				}*/

			}
			TableTrip.setItems(filteredList);
		}
	}

	// **************************listee

	public static Trip getTrip() {
		return trip;
	}

	public static void setTrip(Trip trip) {
		TripsController.trip = trip;
	}

}
