package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import Entity.Transport;
import esprit.skiworld.Business.TransportBusiness;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class UpdateTransportController implements Initializable{
	@FXML
	JFXTextField depP;
	@FXML
	JFXTextField nPlaces;
	@FXML
	JFXTextField arrPlace;
	@FXML
	JFXTextField cost;
	@FXML
	ChoiceBox<String> type;
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> ls = FXCollections.observableArrayList("Avoin","Bus","Taxi","Train");
		type.setItems(ls);
		
		depP.setText(TransportController.upTrans.getDeparturePlace().toString());
		arrPlace.setText(TransportController.upTrans.getArrivalPlace().toString());
		cost.setText(TransportController.upTrans.getPrice().toString());
		type.setValue(TransportController.upTrans.getType().toString());
		nPlaces.setText(TransportController.upTrans.getNumberPlaces().toString());
		arrDate.setValue(LocalDate.now());
		arrTime.setValue(LocalTime.now());
		depDate.setValue(LocalDate.now());
		depTime.setValue(LocalTime.now());
	}
	@FXML
	public void updateAction(ActionEvent event) throws IOException, ParseException {
		TransportController.upTrans.setArrivalPlace(arrPlace.getText());
		TransportController.upTrans.setDeparturePlace(depP.getText());
		TransportController.upTrans.setNumberPlaces(Integer.parseInt(nPlaces.getText()));
		TransportController.upTrans.setPrice(Float.parseFloat(cost.getText()));
		TransportController.upTrans.setType(type.getSelectionModel().getSelectedItem());
		LocalDate dt = depDate.getValue();
		LocalTime dt2 = depTime.getValue();
		String s = dt.toString()+" "+dt2.toString();
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		LocalDate dt3 = arrDate.getValue();
		LocalTime dt4 = arrTime.getValue();
		String s1 = dt3.toString()+" "+dt4.toString();
		Date d = df1.parse(s);
		Date d1 = df1.parse(s1);
		TransportController.upTrans.setDepartureDate(d);
		TransportController.upTrans.setArrivalDate(d1);
		new TransportBusiness().updateTransport(TransportController.upTrans);
		MainApp.changeScene("/fxml/Transport.fxml", "Transport");
	}
}
