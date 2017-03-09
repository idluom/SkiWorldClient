package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Admin;
import Entity.Member;
import Entity.RestaurantOwner;
import Service.MemberEJBRemote;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


public class AuthenticationController implements Initializable{
	
	@FXML
	TextField usernameTF;
	
	@FXML
	TextField passwordTF;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void connectAction() {
		String jndiName ="/skiworld-ear/skiworld-ejb/MemberEJB!Service.MemberEJBRemote";
		InitialContext ctx = null;
		Member auth = new Member();
		MemberEJBRemote proxy = null;
		try {
			ctx = new InitialContext();
			proxy = (MemberEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		auth = proxy.authentication(usernameTF.getText(), passwordTF.getText());
		if (auth instanceof Admin ) {
			System.out.println("Hello "+auth.getFirstName());
		} else {
			System.out.println("You Shall Not Pass !!!!!");
		}
	}

}
