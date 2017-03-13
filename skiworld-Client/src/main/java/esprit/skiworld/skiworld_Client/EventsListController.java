package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import Entity.Events;
import esprit.skiworld.Business.EventsBusiness;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

public class EventsListController implements Initializable {
	@FXML
	private TableView<Events> tableEvents;
	@FXML
	private TableColumn<Events, String> name;
	@FXML
	private TableColumn<Events, String> place;
	@FXML
	private TableColumn<Events, String> date;
	@FXML
	private TableColumn<Events, String> number;
	@FXML
	private TableColumn<Events, String> price;
	@FXML
	private TableColumn<Events, String> description;
	@FXML
	private Button addEvent;
	@FXML
	private Button deleteEvent;
	@FXML
	ObservableList<Events> champs;
	
	public static Stage s = new Stage();
	public static Events event;

	
	// Event Listener on Button[#addEvent].onAction
	@FXML
	public void addEvent(ActionEvent event) throws IOException {
		add();
		champs.removeAll(champs);
		champs = FXCollections.observableArrayList(new EventsBusiness().findAllEvent());
		tableEvents.setItems(champs);

	}
	// Event Listener on Button[#deleteEvent].onAction
	@FXML
	public void deleteEvent(ActionEvent event) {
		Events events = tableEvents.getSelectionModel().getSelectedItem();
		new EventsBusiness().deleteEvent(events);
		tableEvents.getItems().remove(tableEvents.getSelectionModel().getSelectedItem());
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		champs = FXCollections.observableArrayList(new EventsBusiness().findAllEvent());
		
		name.setCellValueFactory(new PropertyValueFactory<Events, String>("name"));
		place.setCellValueFactory(new PropertyValueFactory<Events, String>("place"));
		date.setCellValueFactory(new PropertyValueFactory<Events, String>("date"));
		number.setCellValueFactory(new PropertyValueFactory<Events, String>("nbrPlaces"));
		price.setCellValueFactory(new PropertyValueFactory<Events, String>("price"));
		description.setCellValueFactory(new PropertyValueFactory<Events, String>("description"));
		
		tableEvents.setItems(champs);
		
		tableEvents.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					EventsListController.setEvent(tableEvents.getSelectionModel().getSelectedItem());
					
						try {
							update();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						champs.removeAll(champs);
						champs = FXCollections.observableArrayList(new EventsBusiness().findAllEvent());
						tableEvents.setItems(champs);
					

			}
		}
		});
	}
	public void add() throws IOException {
		Parent root = FXMLLoader.load(AddRoomController.class.getResource("/fxml/AddEvents.fxml"));
		s.setScene(new Scene(root));
		s.showAndWait();
		
		
	}
	public void update() throws IOException {
		Parent root = FXMLLoader.load(UpdateRoomController.class.getResource("/fxml/UpdateEvents.fxml"));
		s.setScene(new Scene(root));
		s.showAndWait();
		
	}
	public static Events getEvent() {
		return event;
	}
	public static void setEvent(Events event) {
		EventsListController.event = event;
	}
	
}
