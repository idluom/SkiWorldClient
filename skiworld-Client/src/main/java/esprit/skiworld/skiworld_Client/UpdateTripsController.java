package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import com.jfoenix.controls.JFXTimePicker;

import Entity.Trip;
import Service.TripEJBRemote;
import esprit.skiworld.Business.TripBusiness;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.LocalDateStringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

public class UpdateTripsController implements Initializable{
	@FXML
    private JFXTextField LinkId;

    @FXML
    private JFXTextField DescriptionId;

    @FXML
    private JFXTextField PriceId;

    @FXML
    private JFXTextField NumberId;

	@FXML
	private JFXTimePicker TimeId;
	@FXML
	private JFXDatePicker dateId;
	@FXML
	private JFXButton SaveId;

	 private Stage dialogStage;
	    private boolean okClicked =false;
	    public boolean isOk()
		{
			return okClicked;
		}
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	
	// Event Listener on JFXButton[#SaveId].onAction
	@FXML
	public void SaveAction(ActionEvent event) throws NamingException, ParseException {
		String Error="";
		if(DescriptionId.getText().length()<3)
		{
			Error+="Wrong Description \n";
		}
		if(Integer.parseInt(NumberId.getText())<0)
		{
			Error+="Number of places should be superior to 0 !! \n";
		}
		if(Float.parseFloat(PriceId.getText())<0)
		{
			Error+="Price Should be positive \n ";
		}
		
		
		if(Error == ""){
		Trip t = new Trip();
		t=TripsController.getTrip();
		String d = dateId.getValue()+" "+TimeId.getValue();
		  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		 Date da = df.parse(d);
		t.setDate(da);
		t.setDescription(DescriptionId.getText());
		t.setNumber(Integer.parseInt(NumberId.getText()));
		t.setPrice(Float.parseFloat(PriceId.getText()));
		t.setVideo(LinkId.getText());
		new TripBusiness().updateTrip(t);
		TripsController.stage.close();
		
		Notifications nb = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).title("Update")
				.text(DescriptionId.getText() + " Has been updated successfully !").graphic(DescriptionId).position(Pos.CENTER).hideCloseButton()
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						MainApp.s.close();
					}
				});
		nb.showConfirm();
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Trip t = new Trip();
			t=TripsController.getTrip();
			DescriptionId.setText(t.getDescription());
			PriceId.setText(t.getPrice()+"");
			NumberId.setText(t.getNumber()+"");		
			String s = t.getDate()+"";

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
			String date = s.substring(0, 10);
			String time = s.substring(11,16 );
			LocalDate localDate = LocalDate.parse(date, formatter);
			dateId.setValue(localDate);
			if(t.getVideo()==null)
			{
				LinkId.setText(t.getVideo());
			}
		
			LocalTime localTime = LocalTime.parse(time,format);
		    TimeId.setValue(localTime);
	
	}
}
