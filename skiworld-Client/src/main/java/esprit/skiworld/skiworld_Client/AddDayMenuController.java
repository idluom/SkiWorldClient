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

public class AddDayMenuController implements Initializable {
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
	private JFXButton AddMenu;
	@FXML
	private Label MenuLabel;
	ObservableList<Product> prodOList;
	// Event Listener on JFXButton[#addButton].onAction

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		prodTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//DayMenu dm = new DayMenuBusiness().findMenuDate(new Date());	
		prodOList = FXCollections.observableArrayList(new ProductBusiness().getAllProd());
		categoryProdColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		nameProdColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
		priceProdColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
		typeProdColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		prodTable.setItems(prodOList);
	//	MenuLabel.setText("Menu du :  "+ dm.getDayMenuDate().toString());
	}

	@FXML
	public void addMenuAction(ActionEvent event) throws IOException {
		List <Product> mp = new ArrayList<>();
		for (Product product : prodTable.getSelectionModel().getSelectedItems()) {
			mp.add(product);
		}
		System.out.println(mp);
		DayMenu ddm = new DayMenu();
		ddm.setDayMenuDate(new Date());
		ddm.affectProd(mp);
		new DayMenuBusiness().updMenu(ddm);
		MainApp.changeScene("/fxml/DayMenu.fxml", "Add Mean Of Transport");
	}
}
