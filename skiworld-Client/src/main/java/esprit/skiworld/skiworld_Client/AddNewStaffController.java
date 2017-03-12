package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Entity.Member;
import esprit.skiworld.Business.StaffBusiness;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddNewStaffController implements Initializable{

	@FXML
	private TextField first;
	@FXML
	private TextField last;
	@FXML
	private TextField id;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField confirmation;
	@FXML
	private TextField address;
	@FXML
	private TextField mail;
	@FXML
	private TextField phone;
	@FXML
	private DatePicker date;
	@FXML
	private ChoiceBox<String> role;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.textProperty().addListener(new ChangeListener<String>() {
		    @Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
		            id.setText(newValue);
		        } else {
		            id.setText(oldValue);
		        }
		    }
		});
		
		phone.textProperty().addListener(new ChangeListener<String>() {
		    @Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
		            phone.setText(newValue);
		        } else {
		            phone.setText(oldValue);
		        }
		    }
		});
		
		ObservableList<String> listRoles = FXCollections.
				observableArrayList("Admin","Hotel Manager","Shop Owner","Restaurant Owner");
		role.setItems(listRoles);
	}

	@FXML
	public void confirm(ActionEvent event) throws IOException {
		Member member = new Member();
		member.setAddress(address.getText());
		LocalDate dt = date.getValue();
		member.setBirthDate(java.sql.Date.valueOf(dt));
		member.setCin(Integer.parseInt(id.getText()));
		member.setFirstName(first.getText());
		member.setLastName(last.getText());
		member.setLogin(username.getText());
		member.setMail(mail.getText());
		member.setNumTel(Integer.parseInt(phone.getText()));
		member.setPassword(password.getText());
		if (checkAttributes()) {
			try {
				new StaffBusiness().addMember(member, role.getSelectionModel().getSelectedItem());
			} catch (NamingException e) {
				e.printStackTrace();
			}
			Notifications notif = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).
					title("Confirmation").text(role.getSelectionModel().getSelectedItem()+" Successfully Added");
			notif.showConfirm();
			MainApp.changeScene("/fxml/MainPage.fxml", "Home Page");
		}
	}
	
	public boolean checkAttributes() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error !");
		alert.setHeaderText("The mentionned fields do not match :");
		String text = "";
		if (!(password.getText().equals(confirmation.getText()))) {
			text += "\nPassword Confirmation";
		}
		if (date.getValue().isAfter(LocalDate.of(2000, 1, 1))) {
			text += "\nDate of Birth";
		}
		if (!(mail.getText().contains("@gmail.com"))) {
			text += "\nMail Address.";
		}
		if (role.getSelectionModel().getSelectedItem() == "") {
			text += "\nRole Missing.";
		}
		
		if (text == "") {
			return true;
		} else {
			alert.setContentText(text);
			alert.show();
			return false;
		}
	}
}