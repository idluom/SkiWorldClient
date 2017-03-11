package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import org.controlsfx.control.Notifications;

import Entity.Comment;
import Entity.Report;
import esprit.skiworld.Business.CommentBusiness;
import esprit.skiworld.Business.ReportBusiness;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class CommentsAndReportsController implements Initializable {
	
	public static Stage s = new Stage();
	public static Report selected;
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
	
	/**
	 * This method initializes the controller class, this code will run as soon as the scene is opened.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/**
		 * Comments Table Filling.
		 */
		commentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		champs = FXCollections.observableArrayList(new CommentBusiness().getAllComments());
		
		alias.setCellValueFactory(new PropertyValueFactory<>("userAlias"));
		comments.setCellValueFactory(new PropertyValueFactory<>("comment"));
		dates.setCellValueFactory(new PropertyValueFactory<>("dateComment"));
		commentTable.setItems(champs);
		
		/**
		 * Reports Table Filling.
		 */
		reportTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		reports = FXCollections.observableArrayList(new ReportBusiness().getAllReports());
		
		mail.setCellValueFactory(new PropertyValueFactory<>("senderMail"));
		reportObject.setCellValueFactory(new PropertyValueFactory<>("mailObject"));
		reportDate.setCellValueFactory(new PropertyValueFactory<>("dateReport"));
		reportTable.setItems(reports);
		
		/**
		 * Double Click on the selected Item to open new Stage.
		 */
		reportTable.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					try {
						selected = reportTable.getSelectionModel().getSelectedItem();
						selectedReport();
					} catch (IOException e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Error !");
						alert.setHeaderText("Something Went Wrong");
						alert.show();
					}
				}
			}
		});
	}
	
	@FXML
    public void refreshTables(ActionEvent event) throws NamingException {
		
		champs = FXCollections.observableArrayList(new CommentBusiness().getAllComments());
		reports = FXCollections.observableArrayList(new ReportBusiness().getAllReports());
		reportTable.setItems(reports);
		commentTable.setItems(champs);
    }
	
	@FXML
	public void removeComment(ActionEvent event) throws NamingException {
		Comment cmnt = new Comment();
		cmnt = commentTable.getSelectionModel().getSelectedItem();
		if(new CommentBusiness().deleteComment(cmnt)) {
			Notifications notBuilder = Notifications.create().darkStyle().hideAfter(Duration.seconds(5)).
					title("Action Completed").text("The comment was successfuly deleted");
			notBuilder.showConfirm();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error !");
			alert.setHeaderText("Something Went Wrong");
			alert.show();
		}
		refreshTables(event);
	}
	
	public void selectedReport() throws IOException {
		Parent root = FXMLLoader.load(CommentsAndReportsController.class.getResource("/fxml/SelectedReport.fxml"));
		s.setScene(new Scene(root));
		s.show();
	}

}
