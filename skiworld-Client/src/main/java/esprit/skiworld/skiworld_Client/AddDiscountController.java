package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Discount;
import Service.DiscountEJBRemote;
import esprit.skiworld.Business.Loading;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddDiscountController implements Initializable {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane Conteneur;
	@FXML
	private DatePicker debut;

	@FXML
	private DatePicker fin;

	@FXML
	private ChoiceBox<String> percentage;

	@FXML
	private Button add;

	@FXML
	private Button bac;

	ObservableList<String> percent = FXCollections.observableArrayList("0", "10", "20", "30", "40", "50", "60", "70",
			"80");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Conteneur.setVisible(false);
		percentage.setValue("0");
		percentage.setItems(percent);
		ProgressLoading.setProgress(0);
		ProgressLoading.progressProperty().unbind();
		task = Loading.load();
		ProgressLoading.progressProperty().bind(task.progressProperty());
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				Conteneur.setVisible(true);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(1), Conteneur);
				tt.setFromY(50);
				tt.setToY(0);
				tt.play();
				loading.setVisible(false);
			}
		});

	}

	@FXML
	void addDiscount(ActionEvent event) {
		if (debut.getValue() == null || fin.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" error ");
			alert.setHeaderText("Check Fields");
			alert.showAndWait();
			return;
		}
		Discount D = new Discount();
		D.setBeginning(java.sql.Date.valueOf(debut.getValue()));
		D.setEnd(java.sql.Date.valueOf(fin.getValue()));
		D.setPercentage(Float.valueOf(percentage.getSelectionModel().getSelectedItem()));
		InitialContext ctx = null;
		DiscountEJBRemote proxy = null;
		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/DiscountEJB!Service.DiscountEJBRemote";
			proxy = (DiscountEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
		}

		Date date = new Date();
		if (D.getBeginning().before(date) == true || D.getEnd().before(D.getBeginning()) == true) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(" error ");
			alert.setHeaderText("Invalid Date");
			alert.showAndWait();
			System.out.println(date);
			return;
		}
		
		proxy.addDiscount(D);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(" Add ");
		alert.setHeaderText("Add successful");
		alert.showAndWait();

	}

}
