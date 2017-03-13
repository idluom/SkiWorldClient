package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Room;
import Service.RoomEJBRemote;
import esprit.skiworld.Business.RoomBusiness;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class UpdateRoomController implements Initializable {
	private Stage dialogStage;
	@FXML
	private TextField sbedTF;
	@FXML
	private TextField dbedTF;
	@FXML
	private TextField priceTF;
	@FXML
	private TextArea descTA;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		sbedTF.setText((RoomListController.getRoom().getNbrSimpleBed()+""));
		dbedTF.setText((RoomListController.getRoom().getNbrDoubleBed()+""));
		priceTF.setText((RoomListController.getRoom().getPrice()+""));
		descTA.setText((RoomListController.getRoom().getDescription()));
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void updateRoom(ActionEvent event) throws NamingException {
		if (isInputValid()) {
			
			Room room = new RoomBusiness().findRoomById(RoomListController.getRoom().getIdRoom());
			room.setNbrSimpleBed(Integer.valueOf(sbedTF.getText()));
			room.setNbrDoubleBed(Integer.valueOf(dbedTF.getText()));
			room.setPrice(Float.valueOf(priceTF.getText()));
			room.setDescription(descTA.getText());
			new RoomBusiness().updateRoom(room);
			RoomListController.s.close();	
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleCancel(ActionEvent event) {
		// TODO Autogenerated
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
				if (i>4){
	                errorMessage += "The number must be less than 4\n";
	            }
			} catch (Exception e) {
				errorMessage += "Enter a valid simple bed number \n";
			}
		}
		if (dbedTF.getText().length() != 0) {
			try {

				int i = Integer.parseInt(dbedTF.getText());
				if (i>2){
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
		}
		else {
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