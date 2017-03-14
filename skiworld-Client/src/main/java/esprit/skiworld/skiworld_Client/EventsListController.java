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

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;

import Entity.Events;
import Entity.Skier;
import Service.SkierEJBRemote;
import esprit.skiworld.Business.EventsBusiness;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
	@FXML
	CheckBox checkbox;
	@FXML
	private Button skierListButton;

	public static Stage s = new Stage();
	public static Events event;
	public static Events event2;

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

	@FXML
	public void skierList(ActionEvent event) {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			SkierEJBRemote proxy = (SkierEJBRemote) ctx
					.lookup("/skiworld-ear/skiworld-ejb/SkierEJB!Service.SkierEJBRemote");
			List<Skier> list = proxy.findSkiersByEvent(tableEvents.getSelectionModel().getSelectedItem());
			for (Skier skier : list) {
				System.out.println(skier.getName() + " / " + skier.getLastName());
			}
		} catch (NamingException e) {

			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkbox.setOnAction((event) -> {
			champs.removeAll(champs);
			if (checkbox.isSelected()) {

				champs = FXCollections.observableArrayList(new EventsBusiness().findAllEventNotStarted());
				tableEvents.setItems(champs);
			} else
				champs = FXCollections.observableArrayList(new EventsBusiness().findAllEvent());
			tableEvents.setItems(champs);
		});

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
				
				if (event.isSecondaryButtonDown() && event.getClickCount() == 1) {
					PopOver pop = new PopOver();
					
					JFXButton btn = new JFXButton(" View Skiers ");
					JFXButton btn2 = new JFXButton("Delete Event");
					btn2.setMinWidth(btn.getMaxWidth());
					VBox root = new VBox(btn,btn2);
					btn2.setDisable(true);
					pop.setDetachable(false);
					pop.setDetached(false);
					pop.setArrowSize(10);
					pop.setContentNode(root);
					pop.setHeight(btn.getHeight());
					pop.setOpacity(.9);
					pop.show(tableEvents);
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							event2=tableEvents.getSelectionModel().getSelectedItem();
							try {
								fetchSkiers();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
	}

	public void add() throws IOException {
		Parent root = FXMLLoader.load(AddEventsController.class.getResource("/fxml/AddEvents.fxml"));
		s.setScene(new Scene(root));
		s.showAndWait();

	}

	public void update() throws IOException {
		Parent root = FXMLLoader.load(UpdateRoomController.class.getResource("/fxml/UpdateEvents.fxml"));
		s.setScene(new Scene(root));
		s.showAndWait();

	}
	public void fetchSkiers() throws IOException {
		Parent root = FXMLLoader.load(EventSkierListController.class.getResource("/fxml/EventSkierList.fxml"));
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
