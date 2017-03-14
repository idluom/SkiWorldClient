package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.Room;
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.RoomBusiness;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class RoomListController implements Initializable {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane Conteneur;
	@FXML
	private TableColumn<Room, String> sbed;
	@FXML
	private TableColumn<Room, String> dbed;
	@FXML
	private TableColumn<Room, String> price;
	@FXML
	private TableColumn<Room, String> description;
	@FXML
	private TableView<Room> tableRooms;

	@FXML
	private Button addRoom;
	@FXML
	private Button deleteRoom;
	@FXML
	private TextField filterInput;
	@FXML
	private TextField filterInput1;
	@FXML
	ObservableList<Room> champs;
	public static Stage s = new Stage();
	public static Room room;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Conteneur.setVisible(false);
		
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				Conteneur.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(1), Conteneur);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});
		
		    champs = FXCollections.observableArrayList(new RoomBusiness().findAllRoom());
			
		
			sbed.setCellValueFactory(new PropertyValueFactory<Room, String>("nbrSimpleBed"));
			dbed.setCellValueFactory(new PropertyValueFactory<Room, String>("nbrDoubleBed"));
			price.setCellValueFactory(new PropertyValueFactory<Room, String>("price"));
			description.setCellValueFactory(new PropertyValueFactory<Room, String>("description"));
			tableRooms.setItems(champs);

			tableRooms.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
						RoomListController.setRoom(tableRooms.getSelectionModel().getSelectedItem());
						
							try {
								update();
							} catch (IOException e) {
								
								e.printStackTrace();
							}
							champs.removeAll(champs);
							champs = FXCollections.observableArrayList(new RoomBusiness().findAllRoom());
							tableRooms.setItems(champs);
						

				}
			}
			});
			
			for (Room room : champs) {
				System.out.println(room.getIdRoom());
			}
		
	}

	

	// Event Listener on Button[#addRoom].onAction
	@FXML
	public void addRoom(ActionEvent event) throws IOException {
		add();
		champs.removeAll(champs);
		champs = FXCollections.observableArrayList(new RoomBusiness().findAllRoom());
		tableRooms.setItems(champs);
	}

	// Event Listener on Button[#deleteRoom].onAction
	@FXML
	public void deleteRoom(ActionEvent event) {
		Room room = tableRooms.getSelectionModel().getSelectedItem();
		new RoomBusiness().deleteRoom(room);
		tableRooms.getItems().remove(tableRooms.getSelectionModel().getSelectedItem());

	}

	public void add() throws IOException {
		Parent root = FXMLLoader.load(AddRoomController.class.getResource("/fxml/AddRoom.fxml"));
		s.setScene(new Scene(root));
		s.showAndWait();
		
	}

	public void update() throws IOException {
		Parent root = FXMLLoader.load(UpdateRoomController.class.getResource("/fxml/UpdateRoom.fxml"));
		s.setScene(new Scene(root));
		s.showAndWait();
	}

	public static Room getRoom() {
		return room;
	}

	public static void setRoom(Room room) {
		RoomListController.room = room;
	}

}
