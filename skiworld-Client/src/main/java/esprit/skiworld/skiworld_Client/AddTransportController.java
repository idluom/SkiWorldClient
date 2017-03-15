package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.Control;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import Entity.Transport;
import esprit.skiworld.Business.TransportBusiness;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

public class AddTransportController implements Initializable {
	@FXML
	JFXTextField depP;
	@FXML
	JFXTextField nPlaces;
	@FXML
	JFXTextField arrPlace;
	@FXML
	JFXTextField cost;
	@FXML
	ChoiceBox<String> type = new ChoiceBox<>();
	@FXML
	JFXDatePicker depDate;
	@FXML
	JFXDatePicker arrDate;
	@FXML
	JFXTimePicker depTime;
	@FXML
	JFXTimePicker arrTime;
	@FXML
	JFXButton addButton;
	DatePicker arrDate2;
	ValidationSupport validationSupport;
	ListView<ValidationMessage> messageList;
	private ValidationSupport validationSupport2;
	private ValidationSupport validationSupport3;
	private ValidationSupport validationSupport4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validationSupport = new ValidationSupport();
		validationSupport2 = new ValidationSupport();
		validationSupport3 = new ValidationSupport();
		validationSupport4 = new ValidationSupport();
		depDate.setValue(LocalDate.now());
		arrDate.setValue(LocalDate.now());
		validationSupport.registerValidator(depP, Validator.createEmptyValidator("Departure place is required"));
		validationSupport2.registerValidator(arrPlace, Validator.createEmptyValidator("Arrival place is required"));
		validationSupport3.registerValidator(arrDate, false, (Control c, LocalDate newValue) -> 
        ValidationResult.fromWarningIf(arrDate, "The date should be in the future", newValue.isBefore(LocalDate.now())));
		validationSupport4.registerValidator(depDate, false, (Control c, LocalDate newValue) -> 
        ValidationResult.fromWarningIf(depDate, "Departure Date should be before arrival date", newValue.isAfter(arrDate.getValue())||
        		newValue.isBefore(LocalDate.now())));
		ObservableList<String> ls = FXCollections.observableArrayList("Avion", "Bus", "Taxi", "Train");
		type.setItems(ls);
		cost.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					cost.setText(newValue);
				} else {
					cost.setText(oldValue);
				}
			}
		});
		nPlaces.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					nPlaces.setText(newValue);
				} else {
					nPlaces.setText(oldValue);
				}
			}
		});
	}

	@FXML
	public void addAction(ActionEvent event) throws IOException, ParseException {
		Transport tr = new Transport();
		tr.setArrivalPlace(arrPlace.getText());
		tr.setDeparturePlace(depP.getText());
		tr.setNumberPlaces(Integer.parseInt(nPlaces.getText()));
		tr.setPrice(Float.parseFloat(cost.getText()));
		tr.setType(type.getSelectionModel().getSelectedItem());
		LocalDate dt = depDate.getValue();
		LocalTime dt2 = depTime.getValue();
		String s = dt.toString() + " " + dt2.toString();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LocalDate dt3 = arrDate.getValue();
		LocalTime dt4 = arrTime.getValue();
		String s1 = dt3.toString() + " " + dt4.toString();
		System.out.println("s " + s);
		// SimpleDateFormat sdf = new SimpleDateFormat();
		// String s = sdf.format(dt);
		// s+= sdf.format(dt2);
		Date d = df1.parse(s);
		Date d1 = df1.parse(s1);
		tr.setDepartureDate(d);
		tr.setArrivalDate(d1);
		System.out.println("dt " + dt);
		System.out.println("dt2 " + dt2);

		System.out.println(validationSupport3.getValidationResult().getErrors().toString());
		if (validationSupport.getValidationResult().getErrors().isEmpty()&& 
				validationSupport2.getValidationResult().getErrors().isEmpty() && 
				validationSupport3.getValidationResult().getWarnings().isEmpty()
				&& validationSupport4.getValidationResult().getWarnings().isEmpty()) {
			new TransportBusiness().addMeanTransport(tr);
			MainApp.changeScene("/fxml/Transport.fxml", "Transport");
			MainApp.s.close();
		} else {
			Alert a = new Alert(AlertType.WARNING);
			a.setHeaderText("Missing Fileds");
			a.setContentText(validationSupport.getValidationResult().getErrors().toString()+
					"\n"+validationSupport2.getValidationResult().getErrors().toString()+
					"\n"+ validationSupport3.getValidationResult().getWarnings().toString()
					+"\n"+ validationSupport4.getValidationResult().getWarnings().toString());
			a.show();
			
		}
	}
}
