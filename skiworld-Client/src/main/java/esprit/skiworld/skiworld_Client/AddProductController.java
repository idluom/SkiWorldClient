package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Entity.Product;
import esprit.skiworld.Business.Loading;
import esprit.skiworld.Business.ProductBusiness;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AddProductController implements Initializable {
	private Task<?> task;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	ImageView loading;
	@FXML 
	private AnchorPane Conteneur;
	@FXML
	JFXTextField name;
	@FXML
	JFXTextField category;
	@FXML
	JFXTextField price;
	@FXML
	JFXTextField type;
	@FXML
	JFXButton AddButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Conteneur.setVisible(false);
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
	public void AddProduct(ActionEvent event) throws IOException {
		Product pr = new Product();
		pr.setCategory(category.getText());
		pr.setNameProduct(name.getText());
		pr.setProductPrice(Float.parseFloat(price.getText()));
		pr.setType(type.getText());
		if (new ProductBusiness().addProd(pr)) {
			Notifications notif = Notifications.create().darkStyle().hideAfter(Duration.seconds(5))
					.title("Confirmation").text("Successfully Added");
			notif.showConfirm();
			MainApp.changeScene("/fxml/Product.fxml", "Product List");
			MainApp.s.close();
		}
	}
}
