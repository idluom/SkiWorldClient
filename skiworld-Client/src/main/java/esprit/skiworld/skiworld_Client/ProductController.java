package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.text.TabableView;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Entity.Comment;
import Entity.Product;
import Entity.Track;
import Entity.Transport;
import Service.TrackEJBRemote;
import Service.TransportEJB;
import Service.TransportEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import esprit.skiworld.Business.ProductBusiness;
import esprit.skiworld.Business.TransportBusiness;

public class ProductController implements Initializable{
	@FXML
	private TableColumn<Product, String> categoryProdColumn;
	@FXML
	private TableColumn <Product, String>nameProdColumn;
	@FXML
	private TableColumn <Product, String>typeProdColumn;
	@FXML
	private TableColumn<Product, Float> priceProdColumn;
	@FXML
	private JFXButton addButton;
	@FXML
	private JFXButton modifyButton;
	@FXML
	private JFXButton deleteButton;
	@FXML
	private TableView  <Product> prodTable; 
	@FXML
	private JFXTextField  searchTF;
	ObservableList<Product> prodOList;
	public static Product upProd;
	// Event Listener on JFXButton[#addButton].onAction
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources){
		searchTF.textProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				filterProduct((String) oldValue, (String) newValue);				
			}
		});

			
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		prodOList = FXCollections.observableArrayList(new ProductBusiness().getAllProd());
		categoryProdColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		nameProdColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
		priceProdColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		typeProdColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		prodTable.setItems(prodOList);
		
	}
	@FXML
	public void addProd(ActionEvent event) {
		try {
			MainApp.changeScene("/fxml/AddTransport.fxml", "Add Mean Of Transport");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
	}
	// Event Listener on JFXButton[#modifyButton].onAction
	@FXML
	public void modifyProd(ActionEvent event) {
		upProd = prodTable.getSelectionModel().getSelectedItem();
		try {
			MainApp.changeScene("/fxml/UpdateTransport.fxml", "Update Mean Of Transport");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on JFXButton[#deleteButton].onAction
	@FXML
	public void deleteProd(ActionEvent event) {
		Product pr = new Product();
		pr = prodTable.getSelectionModel().getSelectedItem();
		new ProductBusiness().deleteProd(pr);

	}
	private void filterProduct(String oldValue, String newValue) {
		ObservableList<Product> filteredList = FXCollections.observableArrayList();

		if (searchTF == null || (newValue.length() < oldValue.length()) || newValue == null) {
			
				ObservableList<Product> champs = FXCollections.observableArrayList(new ProductBusiness().getAllProd());
				prodTable.setItems(champs);

			

		} else {

			newValue = newValue.toUpperCase();

			for (Product product : prodTable.getItems()) {

				String filterArrival = product.getNameProduct();

				// String filterLastName = clubss.getPrice();

				if (filterArrival.toUpperCase().contains(newValue)) {

					filteredList.add(product);

				}

			}
			prodTable.setItems(filteredList);
	}
}
}