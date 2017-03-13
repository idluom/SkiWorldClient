package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Discount;
import Entity.Equipement;

import Service.DiscountEJBRemote;
import Service.EquipementEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AffectDiscountController implements Initializable {

	@FXML
	private TableView<Equipement> TabShop;
	@FXML
	private TableColumn<Equipement, String> name;

	@FXML
	private TableColumn<Equipement, String> category;

	@FXML
	private TableColumn<Equipement, String> type;

	@FXML
	private TableColumn<Equipement, Float> price;

	@FXML
	private ChoiceBox<String> choix;

	@FXML
	private Button affd;

	@FXML
	private Button Bac;

	@FXML
	private Button ref;

	@FXML
	private Button old;

	@FXML
	private TextField T;
	ObservableList<String> percent = FXCollections.observableArrayList("0", "10", "20", "30", "40", "50", "60", "70",
			"80");
	ObservableList<Equipement> champs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choix.setValue("0");
		choix.setItems(percent);

		InitialContext ctx = null;
		EquipementEJBRemote proxy = null;

		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			proxy = (EquipementEJBRemote) ctx.lookup(jndiName);
			champs = FXCollections.observableArrayList(proxy.displayAllShopEquipement());

		} catch (NamingException e) {
		}

		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		type.setCellValueFactory(new PropertyValueFactory<>("type"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		TabShop.setItems(champs);

	}

	@FXML
	void Refrech(ActionEvent event) {
		champs.remove(champs);

		InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			proxy = (EquipementEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
		}

		champs = FXCollections.observableArrayList(proxy.displayAllShopEquipement());

		TabShop.setItems(champs);

	}

	@FXML
	void discount(ActionEvent event) {
		Alert al = new Alert(Alert.AlertType.WARNING);
		Alert z = new Alert(Alert.AlertType.WARNING);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		Equipement E = TabShop.getSelectionModel().getSelectedItem();
		if (E == null) {
			z.setTitle(" erreur !! ");
			z.setHeaderText("Check ");
			z.showAndWait();
			return;
		}

		InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
		DiscountEJBRemote proxy1 = null;
		try {
			ctx = new InitialContext();
			// proxy1 =(DiscountEJBRemote)
			// ctx.lookup("/skiworld-ear/skiworld-ejb/DiscountEJB!Service.DiscountEJBRemote");
			String jndiName1 = "/skiworld-ear/skiworld-ejb/DiscountEJB!Service.DiscountEJBRemote";
			proxy1 = (DiscountEJBRemote) ctx.lookup(jndiName1);

		} catch (NamingException e1) {
		}
		System.out.println(choix.getValue());
		Discount D = proxy1.findByPercentage(Float.valueOf(choix.getValue()));
		// System.out.println(D.getPercentage());
		if (D == null) {
			al.setTitle(" erreur !! ");
			al.setHeaderText("Check your choice");
			al.showAndWait();
			return;
		} else {
			System.out.println(D.getPercentage());

			E.setDiscount(D);
			E.setPrice((E.getPrice() / 100) * (100 - D.getPercentage()));
			try {
				ctx = new InitialContext();
				String jndiName = "/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
				proxy = (EquipementEJBRemote) ctx.lookup(jndiName);
			} catch (NamingException e) {
			}
			proxy.updateEquipement(E);
			alert.setTitle(" Add ");
			alert.setHeaderText("Add successful");
			alert.showAndWait();

		}

	}

	@FXML
	void oldPrice(ActionEvent event) {
		InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
		Equipement E = TabShop.getSelectionModel().getSelectedItem();
		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			proxy = (EquipementEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
		}

		if (E.getDiscount() == null) {
			T.setText("" + E.getPrice());

		} else {
			Float S = ((100 * E.getPrice()) / (100 - E.getDiscount().getPercentage()));

			T.setText("" + S);

		}

	}

}
