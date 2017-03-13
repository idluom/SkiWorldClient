package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXTimePicker;

import Entity.Training;

import Service.TrainingEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AddTrainingController implements Initializable,Comparable<LocalDate> {
	@FXML
	private JFXTimePicker BdTTF;
	
	@FXML
	private DatePicker BdTF;
	@FXML
	private DatePicker EdTF;
	@FXML
	private TextField PriceTF;

	@FXML
	private TextField NumberTF;

	@FXML
	private ChoiceBox<String> LevelTF;
	ObservableList<String> comboList = FXCollections.observableArrayList("Easy", "Amateur", "Average", "Hard",
			"Professional");
	@FXML
	private Label Pricelab;
	@FXML
	private Label Edlab;
	@FXML
	private Label Numberlab;
	@FXML
	private Label Bdlab;
	@FXML
	private Label Levellab;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LevelTF.setValue("Hard");
		LevelTF.setItems(comboList);
	}

	@FXML
	private void Add() {
		
		Training training = new Training();

		int ok = 0;
		if (BdTF.getValue()== null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Begining Date Field");
			alert.showAndWait();
			ok = 1;
			
		}
		if (BdTTF.getValue()== null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Begining Time Field");
			alert.showAndWait();
			ok = 1;
			
		}
		if (EdTF.getValue()==null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty End Date Field");
			alert.showAndWait();
			ok = 1;
		}
		if (NumberTF.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Number Field");
			alert.showAndWait();
			ok = 1;
		}
		if (PriceTF.getText().toString().equals("")) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Price Field");
			alert.showAndWait();
			ok = 1;
		}
		LocalDate dateB = BdTF.getValue();
		LocalDate dateE = EdTF.getValue();
		//LocalTime dateET = EdTTF.getValue();
		LocalTime dateBT = BdTTF.getValue();
		//LocalTime dateET = EdTTF.getValue();

		if(dateB.compareTo(dateE)>0){
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Begining Date Can not before the End Date !!!!");
			alert.showAndWait();
			ok=1;
		}
		
		if(dateB.isBefore(LocalDate.now())) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Begining Date Unsuportable !!!!");
			alert.showAndWait();
			ok=1;
		}
		
		if (ok == 0) {

			
			float price = Float.valueOf(PriceTF.getText());
			int number = Integer.valueOf(NumberTF.getText());
			String level = (String) LevelTF.getValue();
//			LocalDate dateB = BdTF.getValue();
//			LocalDate dateE = EdTF.getValue();
			InitialContext ctx = null;
			try {
				ctx = new InitialContext();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TrainingEJBRemote proxy;
			try {
				proxy = (TrainingEJBRemote) ctx.lookup("/skiworld-ejb/TrainingEJB!Service.TrainingEJBRemote");
				// ObservableList<Track> champs =
				// FXCollections.observableArrayList(proxy.findAll());
				String DD = java.sql.Date.valueOf(dateB)+" "+(java.sql.Time.valueOf(dateBT));
				Date DDD = java.sql.Date.valueOf(dateB);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
				
				
				java.util.Date da;
				java.util.Date Df;
				try {
					String a = EdTF.getValue().toString();
					String b = BdTF.getValue().toString();
					da = df.parse(DD);
					Df = dff.parse(b);
					if(proxy.findAllTrainingByLevel(level,Df).size()==0){
						training.setBegeningDate(da);
						training.setEndDate(java.sql.Date.valueOf(dateE));
						training.setLevel(level);
						training.setPrice(price);
						training.setNumber(number);
						ok=0;
						
					}
					else {
						Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
						alert.setTitle("Date Error");
						alert.setHeaderText("You Can not add Training with the same Level on the same Date of Begining !!!!");
						alert.showAndWait();
						ok=1;
					}
					if(ok==0){
						proxy.addTraining(training);
						// les alerts
						Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).
								title("Action Completed").text("The Training was successfuly Added");
						notBuilder.showConfirm();
						try {
							MainApp.changeScene("/fxml/Training.fxml", "Ski School");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
			
		}
	}

	@FXML
	private void Cancel() {

		NumberTF.clear();
		PriceTF.clear();
		
	}
	
	@Override
	public int compareTo(LocalDate d2) {
		LocalDate d1 = BdTF.getValue();
		
		
		return (d1.compareTo(d2));
	}

}
