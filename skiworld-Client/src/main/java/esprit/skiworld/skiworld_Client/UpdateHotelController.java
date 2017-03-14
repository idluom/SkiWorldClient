package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import Entity.Hotel;
import esprit.skiworld.Business.HotelBusiness;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class UpdateHotelController implements Initializable {
	@FXML
	private TextField nameTF;
	@FXML
	private TextArea descTA;
	@FXML
	private Spinner<Integer> starSpinner = new Spinner<>(00, 01, 02);
	@FXML
	private Rating starRate=new Rating();

	// Event Listener on Button.onAction
	@FXML
	public void updateHotel(ActionEvent event) {
		Long l=new Long(1);
		Hotel hotel=new HotelBusiness().findHotelById(l);
		hotel.setName(nameTF.getText());
		hotel.setDescription(descTA.getText());
		hotel.setStars((int) starRate.getRating());
		new HotelBusiness().updateHotel(hotel);
	}
	// Event Listener on Button.onAction
	@FXML
	public void handleCancel(ActionEvent event) {
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Long l=new Long(1);
		Hotel hotel=new HotelBusiness().findHotelById(l);
		nameTF.setText(hotel.getName());
		descTA.setText(hotel.getDescription());
		starRate.setRating(hotel.getStars());
		
		
		
	}
}
