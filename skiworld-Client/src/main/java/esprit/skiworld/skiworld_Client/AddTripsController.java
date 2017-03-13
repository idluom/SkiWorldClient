package esprit.skiworld.skiworld_Client;

import java.net.URL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import Entity.Trip;
import Service.TripEJB;
import Service.TripEJBRemote;
import esprit.skiworld.Business.TripBusiness;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddTripsController implements Initializable{

	 
	 @FXML
	    private JFXTextField DescriptionId;
	 @FXML
	    private JFXTextField PriceId;

	    @FXML
	    private JFXTextField NumberId;
	 
	 @FXML
	    private JFXButton AddTrip;

	    
	    @FXML
	    private JFXTimePicker TimeId;
	    @FXML
	    private JFXTextField LinkId;
	    @FXML
	    private JFXDatePicker dateId;

	    private Stage dialogStage;
	    public static boolean okClicked =false;
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			NumberId.textProperty().addListener(new  ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if(newValue.matches("\\d*")) {
						NumberId.setText(newValue);
					}
					else NumberId.setText(oldValue);
				}
			});
			PriceId.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
					if(newValue.matches("\\d*")) {
						PriceId.setText(newValue);
					}
					else PriceId.setText(oldValue);
				}
							
			});
			
		}
		public boolean isOk()
		{
			return okClicked;
		}
		public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }
		  @FXML
		    void AjouterTrip(ActionEvent event) throws NamingException {
			  TripEJBRemote t =new TripEJB();			  
			 
			  try {
				  String di = dateId.getValue()+"";
				  String ti = TimeId.getValue()+"";
				  String Error="";
				 if(DescriptionId.getText().length()==0||PriceId.getText().length()==0||NumberId.getText().length()==0||di.length()==0||ti.length()==0)
				 {
					 Error+="Empty Field(s) \n";
				 }
				 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate localDate = LocalDate.parse("2000-01-01", formatter);
					if(dateId.getValue() != null)
					{if(dateId.getValue().isBefore(localDate))
					{
						Error+="Wrong Date!! \n";				
					}
					}
					if(Error != ""){
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Fields Missing");
					alert.setHeaderText(Error);
					 alert.showAndWait();}
				if(Error=="")
				{
				 String d = dateId.getValue()+" "+TimeId.getValue();
				  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
				 Date da = df.parse(d);
				 Trip tr = new Trip((String) DescriptionId.getText(), Float.parseFloat((String) PriceId.getText()), Integer.parseInt(NumberId.getText()+""),da);
				 tr.setVideo(LinkId.getText());
				
				 new TripBusiness().addTrip(tr);
				 TripsController.stage.close();
				 okClicked=true;
				 Notifications nb = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).title("Update")
							.text(DescriptionId.getText() + " Has been Add successfully !").graphic(DescriptionId).hideCloseButton()
							.onAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									MainApp.s.close();
								}
							});
					nb.showConfirm();
		        Clear();
				}
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			  
			  
		    }
	  	    
		  @FXML
		    void BackAction(ActionEvent event) {
			  TripsController.stage.close();
		    }

		  
	    public void Clear()
	    {
	    	 DescriptionId.clear();
			 PriceId.clear();
			 NumberId.clear();
			 dateId = new JFXDatePicker(null);
			 TimeId = new JFXTimePicker(null);
			
	    }
	    


}
