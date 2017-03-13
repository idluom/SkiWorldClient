package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Entity.Admin;
import Entity.HotelManager;
import Entity.Member;
import Entity.RestaurantOwner;
import Entity.ShopOwner;
import Service.MemberEJBRemote;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;


public class AuthenticationController implements Initializable{
	
	private static Member auth;
	@FXML
	TextField usernameTF;
	
	@FXML
	TextField passwordTF;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameTF.setFocusTraversable(true);
		usernameTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					passwordTF.requestFocus();
				}
			}
		});
		
		passwordTF.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					connectAction();
				}
			}
		});
	}
	
	@FXML
	public void connectAction() {
		String jndiName ="/skiworld-ear/skiworld-ejb/MemberEJB!Service.MemberEJBRemote";
		InitialContext ctx = null;
		MemberEJBRemote proxy = null;
		try {
			ctx = new InitialContext();
			proxy = (MemberEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		auth = proxy.authentication(usernameTF.getText(), passwordTF.getText());
		if (auth instanceof Admin || auth instanceof RestaurantOwner || auth instanceof ShopOwner || auth instanceof HotelManager ) {
			
			try {
				
				MainApp.changeScene("/fxml/MainPage.fxml", "Home Page");
				MainApp.s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error !");
			alert.setHeaderText("Error while trying to login.");
			alert.setContentText("Incorrect username / password");
			alert.show();
		}
	}

	public static Member getAuth() {
		return auth;
	}
	
	public static void setAuth(Member auth) {
		AuthenticationController.auth = auth;
	}
	
	public void quit() {
		Notifications nb = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
				.title("Logout").text("Are you sure ? Click here to disconnect !").graphic(usernameTF)
				.position(Pos.CENTER).hideCloseButton()
				.onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						MainApp.s.close();
					}
				});
		nb.showConfirm();
	}
}
