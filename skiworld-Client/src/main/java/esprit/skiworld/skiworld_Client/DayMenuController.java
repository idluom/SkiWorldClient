package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Entity.DayMenu;
import Entity.Product;
import esprit.skiworld.Business.DayMenuBusiness;
import esprit.skiworld.Business.ProductBusiness;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class DayMenuController implements Initializable {
	@FXML
	private TableView <Product> prodTable;
	@FXML
	private TableColumn <Product, String> categoryProdColumn;
	@FXML
	private TableColumn<Product, String> nameProdColumn;
	@FXML
	private TableColumn <Product, String>typeProdColumn;
	@FXML
	private TableColumn<Product, Float> priceProdColumn;
	@FXML
	private JFXButton addButton;
	@FXML
	private Label MenuLabel;
	ObservableList<Product> prodOList;
	DayMenu dm;
	// Event Listener on JFXButton[#addButton].onAction

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		dm = new DayMenuBusiness().findMenuDate(new Date());	
		prodOList = FXCollections.observableArrayList(dm.getDayMenuList());
		categoryProdColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		nameProdColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
		priceProdColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		typeProdColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		prodTable.setItems(prodOList);
		MenuLabel.setText("Menu du :  "+ dm.getDayMenuDate().toString());
	}
	@FXML
	public void deleteMenuAction() throws IOException{
		new DayMenuBusiness().deleteProd2(dm);
		MainApp.changeScene("/fxml/Product.fxml", "Product List");
		MainApp.s.close();
	}
	
}
