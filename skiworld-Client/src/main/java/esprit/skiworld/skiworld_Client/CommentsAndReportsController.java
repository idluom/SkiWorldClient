package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Entity.Comment;
import Entity.Report;
import Service.CommentEJBRemote;
import Service.ReportEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import javafx.scene.control.TableColumn;

public class CommentsAndReportsController implements Initializable{
	@FXML
	private TableView <Comment> commentTable;
	@FXML
	private TableColumn <Comment,String> alias;
	@FXML
	private TableColumn <Comment,String> comments;
	@FXML
	private TableColumn <Comment,Date> dates;
	@FXML
	private TableView <Report> reportTable;
	@FXML
	private TableColumn <Report, String> mail;
	@FXML
	private TableColumn <Report, String> reportObject;
	@FXML
	private TableColumn <Report, Date> reportDate;
	
	ObservableList<Comment> champs;
	ObservableList<Report> reports;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/**
		 * Comments Table Filling
		 */
		InitialContext ctx = null;
		CommentEJBRemote proxy = null;
		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/CommentEJB!Service.CommentEJBRemote" ;
			proxy = (CommentEJBRemote) ctx.lookup(jndiName);
			champs = FXCollections.observableArrayList(proxy.findAllComments());
		} catch (NamingException e) {}
		
		alias.setCellValueFactory(new PropertyValueFactory<>("userAlias"));
		comments.setCellValueFactory(new PropertyValueFactory<>("comment"));
		dates.setCellValueFactory(new PropertyValueFactory<>("dateComment"));
		commentTable.setItems(champs);
		
		/**
		 * Reports Table Filling
		 */
		ReportEJBRemote proxy2 = null;
		try {
			ctx = new InitialContext();
			String jndiName2 = "/skiworld-ear/skiworld-ejb/ReportEJB!Service.ReportEJBRemote" ;
			proxy2 = (ReportEJBRemote) ctx.lookup(jndiName2);
			reports = FXCollections.observableArrayList(proxy2.findAllReports());
		} catch (NamingException e) {}
		
		mail.setCellValueFactory(new PropertyValueFactory<>("senderMail"));
		reportObject.setCellValueFactory(new PropertyValueFactory<>("mailObject"));
		reportDate.setCellValueFactory(new PropertyValueFactory<>("dateReport"));
		reportTable.setItems(reports);
	}
	
	@FXML
    public void refreshTables() {
		Notifications nf = Notifications.create()
                .title("Notification").text("Here's a notification")
                .darkStyle()
                .hideAfter(Duration.seconds(5)).onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        nf.showConfirm();
    }

}
