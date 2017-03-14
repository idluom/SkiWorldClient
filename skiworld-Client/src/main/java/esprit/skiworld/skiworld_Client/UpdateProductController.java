package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Entity.Product;
import esprit.skiworld.Business.ProductBusiness;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class UpdateProductController implements Initializable{

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
		name.setText(ProductController.upProd.getNameProduct());
		category.setText(ProductController.upProd.getCategory());
		type.setText(ProductController.upProd.getType());
		price.setText(ProductController.upProd.getProductPrice().toString());
		
	}
	@FXML
	public void UpdateProduct(ActionEvent event) throws IOException  {	
		ProductController.upProd.setCategory(category.getText());
		ProductController.upProd.setNameProduct(name.getText());
		ProductController.upProd.setProductPrice(Float.parseFloat(price.getText()));
		ProductController.upProd.setType(type.getText());
		new ProductBusiness().updateProd(ProductController.upProd);
		MainApp.changeScene("/fxml/Product.fxml", "Product List");
		MainApp.s.close();
	}
}
