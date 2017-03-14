package esprit.skiworld.skiworld_Client;

import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jfoenix.controls.JFXButton;

import Entity.Member;
import Service.AdminEJBRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelectAdminController implements Initializable {

	public static Stage stage = new Stage();
	public static int type = -1;
	MainApp mainapp;
	@FXML
	private JFXButton adminId;

	@FXML
	private JFXButton shopkeeperId;

	@FXML
	private JFXButton restaurantId;

	@FXML
	private JFXButton hotelId;

	InitialContext ctx;

	@FXML
	void AdminAction(ActionEvent event) throws IOException, NamingException {

		SelectAdminController.setType(0);
		int ok = 0;
		ctx = new InitialContext();
		AdminEJBRemote admin = (AdminEJBRemote) ctx
				.lookup("/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote");
		Member a = new Member();
		try {
			a = admin.getAdmin();
		} catch (Exception e) {
			ok = 1;
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Not Found");
			alert.setHeaderText("You should Add Admin !!");
			alert.showAndWait();

			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/AddNewStaff.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		}
		if (ok == 0) {
			Parent root = FXMLLoader.load(AdminController.class.getResource("/fxml/Admin.fxml"));
			stage.setScene(new Scene(root));
			stage.showAndWait();
			// popupUpdate();
		}

	}

	@FXML
	void HotelOwnerAction(ActionEvent event) throws NamingException, IOException {
		SelectAdminController.setType(1);
		int ok = 0;
		ctx = new InitialContext();
		AdminEJBRemote admin = (AdminEJBRemote) ctx
				.lookup("/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote");
		Member a = new Member();
		try {
			a = admin.getHotelManager();
		} catch (Exception e) {
			ok = 1;
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Not Found");
			alert.setHeaderText("You should Add Hotel Owner !!");
			alert.showAndWait();
			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/AddNewStaff.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		}
		if (ok == 0) {
			Parent root = FXMLLoader.load(AdminController.class.getResource("/fxml/Admin.fxml"));
			stage.setScene(new Scene(root));

			stage.showAndWait();
			// popupUpdate();
		}
	}

	@FXML
	void RestaurantAction(ActionEvent event) throws NamingException, IOException {
		SelectAdminController.setType(2);
		int ok = 0;
		ctx = new InitialContext();
		AdminEJBRemote admin = (AdminEJBRemote) ctx
				.lookup("/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote");
		Member a = new Member();
		try {
			a = admin.getRestaurantOwner();
		} catch (Exception e) {
			ok = 1;
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Not Found");
			alert.setHeaderText("You should Add Restaurant Owner !!");
			alert.showAndWait();
			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/AddNewStaff.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		}
		if (ok == 0) {
			Parent root = FXMLLoader.load(AdminController.class.getResource("/fxml/Admin.fxml"));
			stage.setScene(new Scene(root));

			stage.showAndWait();
			// popupUpdate();
		}
	}

	@FXML
	void ShopkeeperAction(ActionEvent event) throws NamingException, IOException {
		SelectAdminController.setType(3);
		int ok = 0;
		ctx = new InitialContext();
		AdminEJBRemote admin = (AdminEJBRemote) ctx
				.lookup("/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote");
		Member a = new Member();
		try {
			a = admin.getShopOwner();
		} catch (Exception e) {
			ok = 1;
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Not Found");
			alert.setHeaderText("You should Add Shop Owner !!");
			alert.showAndWait();
			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/AddNewStaff.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.showAndWait();
		}
		if (ok == 0) {
			Parent root = FXMLLoader.load(AdminController.class.getResource("/fxml/Admin.fxml"));
			stage.setScene(new Scene(root));

			stage.showAndWait();
			// popupUpdate();
		}
	}

	public static void setType(int t) {
		type = t;
	}

	public static int getType() {
		return type;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
