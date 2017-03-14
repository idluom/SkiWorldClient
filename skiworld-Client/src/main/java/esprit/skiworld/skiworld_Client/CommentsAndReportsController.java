package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

import Entity.Comment;
import Entity.Report;
import esprit.skiworld.Business.CommentBusiness;
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.ReportBusiness;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
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
	private Task task;
	@FXML
	ImageView loading;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	private TabPane TableTrack;
	ObservableList<Comment> champs;
	ObservableList<Report> reports;
	
	/**
	 * This method initializes the controller class, this code will run as soon as the scene is opened.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TableTrack.setVisible(false);
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
		 * Double Click on the selected Item to open a new Stage.
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
				if (event.isSecondaryButtonDown() && event.getClickCount() == 1) {
					PopOver pop = createPopover(reportTable.getSelectionModel().getSelectedItem().getMailBody());
					pop.show(reportTable);
				}
			}
		});
		ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().unbind();
        task= Loading.load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
            	TableTrack.setVisible(true);
            	TranslateTransition tt = new TranslateTransition(Duration.seconds(2),TableTrack);
            	tt.setFromY(50);
            	tt.setToY(0);
            	tt.play();
            	loading.setVisible(false);
            }
        });
	}
	
	@SuppressWarnings("unchecked")
	@FXML
    public void refreshTables(ActionEvent event) throws NamingException {
		loading.setVisible(true);

        ProgressLoading.progressProperty().unbind();
		ProgressLoading.setProgress(0);
        task= Loading.load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
            	loading.setVisible(false);
            }
        });
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

	public PopOver createPopover(String text) {
		PopOver popover = new PopOver();
		popover.setDetachable(true);
		popover.setDetached(false);
		popover.setArrowIndent(10);
		popover.setArrowSize(10);
		popover.setContentNode(createForm(text));
		popover.setTitle("Hello");
		popover.setOpacity(.9);
		
		return popover;
	}
	
	public Node createForm(String text) {
		Pane root= new Pane();
		Label label = new Label(text);
		root.getChildren().add(label);
		return root;
	}
}
