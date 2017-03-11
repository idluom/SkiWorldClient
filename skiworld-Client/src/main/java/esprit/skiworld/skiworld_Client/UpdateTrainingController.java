package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;
import Entity.Training;
import Service.TrackEJBRemote;
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

public class UpdateTrainingController implements Initializable, Comparable<LocalDate> {

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
		PriceTF.setText(Float.valueOf(TrainingController.comp.getPrice()).toString());
		NumberTF.setText(Integer.valueOf(TrainingController.comp.getNumber()).toString());
		
		
	}

	@FXML
	public void Update() {
		int ok = 0;
		if (BdTF.getValue()== null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("Empty Begining Date Field");
			alert.showAndWait();
			ok = 1;
			System.out.println("ffff");
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
		if(dateB.compareTo(dateE)>0){
			System.out.println("ggg");
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Date Error");
			alert.setHeaderText("Begining Date Can not before the End Date !!!!");
			alert.showAndWait();
			ok=1;
		}
		 if(ok==0){
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
					//ObservableList<Track> champs = FXCollections.observableArrayList(proxy.findAll());
					Training training=new Training();
					float price = Float.valueOf(PriceTF.getText());
					int number = Integer.valueOf(NumberTF.getText());
					String level = (String) LevelTF.getValue();
			        training=proxy.findTrainingById(TrackController.comp.getIdTrack());
			        training.setBegeningDate(java.sql.Date.valueOf(dateB));
					training.setEndDate(java.sql.Date.valueOf(dateE));
					training.setLevel(level);
					training.setPrice(price);
					training.setNumber(number);
					proxy.updateTraing(training);
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        // les alerts
		        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		        alert.setTitle("SELECTED");
		        alert.setHeaderText("Training Updated");
		        alert.showAndWait();
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
