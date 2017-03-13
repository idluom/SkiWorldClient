package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import Entity.Events;
import esprit.skiworld.Business.EventsBusiness;

public class AddEventsController implements Initializable {
	@FXML
	private TextField nameTF;
	@FXML
	private TextField placeTF;
	@FXML
	private TextArea descTA;
	@FXML
	private TextField priceTF;
	@FXML
	private TextField numberPTF;
	@FXML
	private JFXDatePicker dateP;
	@FXML
	private JFXTimePicker timeP;
	private Stage dialogStage;

	// Event Listener on Button.onAction
	@FXML
	public void addEvent(ActionEvent event) throws ParseException {
		if (isInputValid()) {
			String date = dateP.getValue() + " " + timeP.getValue();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date1 = sdf.parse(date);
			if (new EventsBusiness().findEventByDate(date1)) {
				Events event1 = new Events();
				event1.setName(nameTF.getText());
				event1.setPlace(placeTF.getText());
				event1.setPrice(Float.valueOf(priceTF.getText()));
				event1.setNbrPlaces(Integer.parseInt(numberPTF.getText()));
				event1.setDate(date1);
				event1.setDescription(descTA.getText());
				new EventsBusiness().addEvent(event1);
				EventsListController.s.close();
				Notifications notifBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
						.title("Success").text("Event added");
				notifBuilder.showConfirm();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText("Cant add more events on that time");
				alert.showAndWait();
			}
		}

	}

	// Event Listener on Button.onAction
	@FXML
	public void handleCancel(ActionEvent event) {
		EventsListController.s.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		descTA.setScrollLeft(1000);
		descTA.setWrapText(true);

	}

	private boolean isInputValid() {
		String errorMessage = "";
		if (nameTF.getText() == null || nameTF.getText().length() == 0) {
			errorMessage += "No valid Name \n";
		}
		if (placeTF.getText() == null || placeTF.getText().length() == 0) {
			errorMessage += "No valid Place\n";
		}
		if (priceTF.getText() == null || priceTF.getText().length() == 0) {
			errorMessage += "No valid Price\n";
		}
		if (numberPTF.getText() == null || numberPTF.getText().length() == 0) {
			errorMessage += "No valid number places\n";
		}

		if (dateP.getValue() == null) {
			errorMessage += "No valid Date\n";
		}

		if (timeP.getValue() == null) {
			errorMessage += "No valid Time\n";
		}
		
		if (dateP.getValue() != null && timeP.getValue() != null) {
			String date0 = dateP.getValue() + " " + timeP.getValue();
			String date1=dateP.getValue()+" 08:00";
			String date2=dateP.getValue()+" 12:00";
			String date3=dateP.getValue()+" 14:00";
			String date4=dateP.getValue()+" 20:00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Date date = sdf.parse(date0);
				Date date01 = sdf.parse(date1);
				Date date02 = sdf.parse(date2);
				Date date03 = sdf.parse(date3);
				Date date04 = sdf.parse(date4);
				if (date.before(date01)||date.before(date03)&&date.after(date02)||date.after(date04)){
					errorMessage+="Event time should be between 8h00-12h00 and 14h00-20h00 \n";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			
		}

		if (numberPTF.getText().length() != 0) {
			try {
				System.out.println(numberPTF.getText());
				Integer.parseInt(numberPTF.getText());

			} catch (Exception e) {
				errorMessage += "Enter a valid number places \n";
			}
		}
		if (priceTF.getText().length() != 0) {
			try {
				Float.parseFloat(priceTF.getText());

			} catch (Exception e) {
				errorMessage += "Enter a valid Price \n";
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
