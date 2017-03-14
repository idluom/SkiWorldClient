package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Hotel;
import Entity.Room;
import Service.HotelEJBRemote;
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.RoomBusiness;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class AddRoomController implements Initializable {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane Conteneur;
	@FXML
	private TextField sbedTF;
	@FXML
	private TextField dbedTF;
	@FXML
	private TextField priceTF;
	@FXML
	private TextArea descTA;
	private Stage dialogStage;
	// Event Listener on Button.onAction

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Conteneur.setVisible(false);
		descTA.setScrollLeft(1000);
		descTA.setWrapText(true);
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
		
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	@FXML
	public void ajouterRoom(ActionEvent event) throws NamingException {
		if (isInputValid()) {
			if (new RoomBusiness().findNumberRoom(Integer.valueOf(sbedTF.getText()), Integer.valueOf(dbedTF.getText())) < 5) {

				if (descTA.getText() == null || descTA.getText().length() == 0) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confirmation Dialog");
					alert.setHeaderText("No description !!");
					alert.setContentText("Do you want to proceed?");

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						Room room = new Room();
						room.setNbrSimpleBed(Integer.valueOf(sbedTF.getText()));
						room.setNbrDoubleBed(Integer.valueOf(dbedTF.getText()));
						room.setPrice(Float.valueOf(priceTF.getText()));
						room.setDescription(descTA.getText());
						InitialContext ctx = new InitialContext();
						HotelEJBRemote proxy2 = (HotelEJBRemote) ctx
								.lookup("/skiworld-ejb/HotelEJB!Service.HotelEJBRemote");
						Long l = new Long(1);
						Hotel hotel = proxy2.findHotelById(l);
						room.setHotel(hotel);

						new RoomBusiness().addRoom(room);
						RoomListController.s.close();

					} else {

					}
				} else {
					Room room = new Room();
					room.setNbrSimpleBed(Integer.valueOf(sbedTF.getText()));
					room.setNbrDoubleBed(Integer.valueOf(dbedTF.getText()));
					room.setPrice(Float.valueOf(priceTF.getText()));
					room.setDescription(descTA.getText());
					InitialContext ctx = new InitialContext();
					HotelEJBRemote proxy2 = (HotelEJBRemote) ctx
							.lookup("/skiworld-ejb/HotelEJB!Service.HotelEJBRemote");
					Long l = new Long(1);
					Hotel hotel = proxy2.findHotelById(l);
					room.setHotel(hotel);

					new RoomBusiness().addRoom(room);
					RoomListController.s.close();

				}

			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("ERROR");
				alert.setHeaderText("You already have 5 exemples of this room");
				alert.setContentText("Change simple or double beds numbers");

				alert.showAndWait();

				
			}
		}
	}

	// Event Listener on Button.onAction
	@FXML
	public void handleCancel(ActionEvent event) {
		RoomListController.s.close();
	}

	private boolean isInputValid() {
		String errorMessage = "";
		if (sbedTF.getText() == null || sbedTF.getText().length() == 0) {
			errorMessage += "No valid Simple Bed Number\n";
		}
		if (dbedTF.getText() == null || dbedTF.getText().length() == 0) {
			errorMessage += "No valid Double Bed Number\n";
		}
		if (priceTF.getText() == null || priceTF.getText().length() == 0) {
			errorMessage += "No valid Price\n";
		}

		if (sbedTF.getText().length() != 0) {
			try {
				int i = Integer.parseInt(sbedTF.getText());
				if (i > 4) {
					errorMessage += "The number must be less than 4\n";
				}
			} catch (Exception e) {
				errorMessage += "Enter a valid simple bed number \n";
			}
		}
		if (dbedTF.getText().length() != 0) {
			try {

				int i = Integer.parseInt(dbedTF.getText());
				if (i > 2) {
					errorMessage += "The number must be less than 3 \n";
				}
			} catch (Exception e) {
				errorMessage += "Enter a valid double bed number \n";

			}
		}
		if (priceTF.getText().length() != 0) {
			try {

				float i = Float.parseFloat(priceTF.getText());
			} catch (Exception e) {
				errorMessage += "Enter a valid price number \n";

			}
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}

}
