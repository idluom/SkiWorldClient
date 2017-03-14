package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import Entity.Mail;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

public class SelectedReportController implements Initializable{
	@FXML
	private Label email;
	@FXML
	private Label object;
	@FXML
	private Label date;
	@FXML
	private TextArea body;
	@FXML
	private TextArea response;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		email.setText(CommentsAndReportsController.selected.getSenderMail());
		object.setText(CommentsAndReportsController.selected.getMailObject());
		date.setText(CommentsAndReportsController.selected.getDateReport().toString());
		body.setText(CommentsAndReportsController.selected.getMailBody());
		body.setEditable(false);
		body.setFocusTraversable(false);
		body.setScrollTop(1000);
		body.setWrapText(true);
		response.setScrollTop(1000);
		response.setWrapText(true);
	}

	@FXML
	public void respondToReport(ActionEvent event) throws Throwable {
		Mail.setUSER_NAME((String) /*AuthenticationController.getAuth().getMail()*/"m.bouden1993" );
        Mail.setPASSWORD(/*(String) PwTF.getText()*/"bouden93");
        Mail.setRECIPIENT(/*(String) ToTF.getText()*/"bilel.nebli@esprit.tn");
        
        Mail.setSsubject((String) CommentsAndReportsController.selected.getMailObject());
        String Texte=response.getText();
        Mail.setBbody(Texte);
        String[] to = {Mail.getRECIPIENT()};
        Mail.sendFromGMaill(Mail.getUSER_NAME(), Mail.getPASSWORD(), to, Mail.getSsubject(), Mail.getBbody());
        Notifications notifBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
				.title("Success").text("Mail Sent With Success");
		notifBuilder.showConfirm();
        CommentsAndReportsController.s.close();
	}
	
}
