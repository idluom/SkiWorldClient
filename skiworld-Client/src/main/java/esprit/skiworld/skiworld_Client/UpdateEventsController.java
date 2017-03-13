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
import com.jfoenix.controls.JFXTimePicker;

import Entity.Events;
import esprit.skiworld.Business.EventsBusiness;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXDatePicker;

public class UpdateEventsController implements Initializable {
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
	public void updateEvent(ActionEvent event) throws ParseException {
		if (isInputValid()) {
			Events event1=new EventsBusiness().findEventById(EventsListController.getEvent().getIdEvents());
			event1.setName(nameTF.getText());
			event1.setPlace(placeTF.getText());
			event1.setPrice(Float.valueOf(priceTF.getText()));
			event1.setNbrPlaces(Integer.parseInt(numberPTF.getText()));
			event1.setDescription(descTA.getText());
			String date=dateP.getValue()+" "+timeP.getValue();
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date1=sdf.parse(date);
			event1.setDate(date1);
			new EventsBusiness().updateEvent(event1);
			EventsListController.s.close();
			Notifications notifBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
					.title("Success").text("Event updated");
			notifBuilder.showConfirm();
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
		nameTF.setText(EventsListController.getEvent().getName());
		placeTF.setText(EventsListController.getEvent().getPlace());
		descTA.setText(EventsListController.getEvent().getDescription());
		priceTF.setText((EventsListController.getEvent().getPrice()+""));
		numberPTF.setText((EventsListController.getEvent().getNbrPlaces()+""));
		DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date=(EventsListController.getEvent().getDate()+"").substring(0, 10);
		LocalDate localdate=LocalDate.parse(date,dtf);
		DateTimeFormatter dtf2=DateTimeFormatter.ofPattern("HH:mm");		
		String time=(EventsListController.getEvent().getDate()+"").substring(11, 16);
		LocalTime localTime=LocalTime.parse(time,dtf2);
		dateP.setValue(localdate);
		timeP.setValue(localTime);
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
		
		if (dateP.getValue() == null ) {
			errorMessage += "No valid Date\n";
		}
		if (timeP.getValue() == null ) {
			errorMessage += "No valid Time\n";
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
