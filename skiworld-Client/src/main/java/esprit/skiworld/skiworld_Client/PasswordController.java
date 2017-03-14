package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXTextField;

import Entity.Member;
import esprit.skiworld.Business.AdminBusiness;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class PasswordController implements Initializable {

	@FXML
	private JFXTextField CinId;

	@FXML
	private JFXTextField MailId;

	@FXML
	private JFXTextField LoginId;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	void SendAction(ActionEvent event) {
		boolean verif = false;
		int isOk = 0;
		String pwd="";
		String mail="";
		String masque = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
                + "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";
		Pattern pattern = Pattern.compile(masque);
		Matcher controler = pattern.matcher(MailId.getText());
		 if(!controler.matches())
		 {
			 Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Fields Missing");
				alert.setHeaderText("Wrong Mail");

				alert.showAndWait();
				isOk =1;
		 }
		
		if(isOk==0){
		List<Member> lm = new AdminBusiness().DisplayAllMember();
		for (Member member : lm) {
			
			String s=member.getCin()+"";
			
			if (LoginId.getText().equals(member.getLogin()) && CinId.getText().equals(s)
					&& MailId.getText().equals(member.getMail())) {
				verif = true;
				pwd=member.getPassword();
				mail=member.getMail();
				
			}
			
		}
		if(verif)
		{
			envoyer(pwd,mail);
		}
		}
		
	}

	public void envoyer(String pwd, String mail)
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication("dhiaeddine.foudhaili@esprit.tn", "00193424619");
			}
		});	
		try
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("dhiaeddine.foudhaili@esprit.tn"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
			message.setSubject("Forgotten Password");
			message.setText("Your Password "+ pwd);
			Transport.send(message);
			Notifications nb = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).title("Logout")
					.text("Mail sent successfully !")
					.hideCloseButton().onAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							MainApp.s.close();
						}
					});
			nb.showConfirm();
			
		}
		catch(MessagingException e){
			
				 Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Failure to send");
					alert.setHeaderText("Please check your informations !!");

					alert.showAndWait();
				
			 }		
		
	}
	
	
}
