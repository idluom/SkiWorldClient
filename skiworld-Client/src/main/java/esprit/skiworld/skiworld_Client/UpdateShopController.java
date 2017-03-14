package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Bill;
import Entity.Equipement;
import Service.BillEJBRemote;
import Service.EquipementEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateShopController implements Initializable {

	@FXML
	private TextField name;

	@FXML
	private TextField category;

	@FXML
	private TextField type;

	@FXML
	private TextField price;

	@FXML
	private TextField sq;

	@FXML
	private TextField iq;

	@FXML
	private Button up;

	@FXML
	private Button bac;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		price.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.matches("\\d*")) {
					price.setText(newValue);
				}
				else price.setText(oldValue);
			}
		});
		
		sq.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.matches("\\d*")) {
					sq.setText(newValue);
				}
				else sq.setText(oldValue);
			}
		});
		
		iq.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(newValue.matches("\\d*")) {
					iq.setText(newValue);
				}
				else iq.setText(oldValue);
			}
		});
		
		name.setText(ShopController.getE().getName());
		category.setText(ShopController.getE().getCategory());
		type.setText(ShopController.getE().getType());
		price.setText(ShopController.getE().getPrice().toString());
		sq.setText(ShopController.getE().getShopquantity().toString());
		iq.setText(ShopController.getE().getInventoryquantity().toString());

	}

	@FXML
	void modifier(ActionEvent event) {

		if (name.getText().length() == 0 || category.getText().length() == 0 || price.getText().length() == 0
				|| sq.getText().length() == 0 || iq.getText().length() == 0) {
			Alert al = new Alert(Alert.AlertType.WARNING);
			al.setTitle(" erreur !! ");
			al.setHeaderText(" check the field");
			al.showAndWait();
			return;

		}
		Equipement E = ShopController.getE();
		Float f = ShopController.getE().getInventoryquantity();
		E.setName(name.getText());
		E.setCategory(category.getText());
		E.setType(type.getText());
		E.setPrice(Float.valueOf(price.getText()));
		E.setShopquantity(Float.valueOf(sq.getText()));
		E.setInventoryquantity(Float.valueOf(iq.getText()));
		InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
		BillEJBRemote proxy1 = null;
		try {
			ctx = new InitialContext();
			String jndiName = "/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			String jndiName1 = "/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			proxy = (EquipementEJBRemote) ctx.lookup(jndiName);
			proxy1 = (BillEJBRemote) ctx.lookup(jndiName1);
		} catch (NamingException s) {
		}
		if (E.getInventoryquantity() > f) {
			Bill b = new Bill();
			b.setDate(new Date());
			b.setEquipementName(E.getName());
			b.setCost(((E.getPrice() / 100) * 60) * (E.getInventoryquantity() + E.getShopquantity()));
			proxy1.addBill(b);
		}

		proxy.updateEquipement(E);

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(" update");
		alert.setHeaderText("update successful");
		alert.showAndWait();
		ShopController.stage.close();

	}

}
