package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Entity.Product;
import esprit.skiworld.Business.ProductBusiness;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.util.Duration;

public class AddProductController implements Initializable {

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
		// TODO Auto-generated method stub

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
