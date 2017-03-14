package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import Entity.Transport;
import esprit.skiworld.Business.TransportBusiness;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
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
	ValidationSupport validationSupport;
	ListView<ValidationMessage> messageList;
	private ValidationSupport validationSupport2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validationSupport = new ValidationSupport();
		validationSupport2 = new ValidationSupport();
		validationSupport.registerValidator(depP, Validator.createEmptyValidator("Text is required"));
		validationSupport2.registerValidator(arrPlace, Validator.createEmptyValidator("Text"));

		ObservableList<String> ls = FXCollections.observableArrayList("Avion", "Bus", "Taxi", "Train");
		type.setItems(ls);
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

		System.out.println("tr" + tr.getDepartureDate());
		if (validationSupport.getValidationResult().getErrors().isEmpty()
				&& validationSupport2.getValidationResult().getErrors().isEmpty()) {
			new TransportBusiness().addMeanTransport(tr);
			MainApp.changeScene("/fxml/Transport.fxml", "Transport");
			MainApp.s.close();
		} else
			new Alert(AlertType.ERROR).show();
	}
}
