package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuHotelController implements Initializable {
	@FXML
	private AnchorPane root;
	public static AnchorPane rootPane;
	@FXML
	private JFXDrawer PaneDrawer;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXDrawer SideDrawer;
	@FXML
	private VBox MenuVbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			rootPane = (AnchorPane) FXMLLoader.load(getClass().getResource(MainPageController.resource));
			PaneDrawer.setContent(rootPane);
			PaneDrawer.setDefaultDrawerSize(0);
			PaneDrawer.toFront();
			hamburger.toFront();
			PaneDrawer.open();
		} catch (IOException ex) {
		}
		rootPane = root;
		SideDrawer.setSidePane(MenuVbox);
		SideDrawer.setDefaultDrawerSize(200);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

			if (SideDrawer.isShown()) {
				SideDrawer.close();
				SideDrawer.toBack();
			} else {
				SideDrawer.open();
				SideDrawer.toFront();
			}
		});
	}

	@FXML
	public void home(ActionEvent event) {
		try {
			rootPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
			PaneDrawer.setContent(rootPane);
			PaneDrawer.setDefaultDrawerSize(0);
			PaneDrawer.toFront();
			hamburger.toFront();
			SideDrawer.close();
			PaneDrawer.open();
		} catch (IOException ex) {
		}
	}

	@FXML
	public void rooms(ActionEvent event) {
		try {
			rootPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/RoomList.fxml"));
			PaneDrawer.setContent(rootPane);
			PaneDrawer.setDefaultDrawerSize(0);
			PaneDrawer.toFront();
			hamburger.toFront();
			SideDrawer.close();
			PaneDrawer.open();
		} catch (IOException ex) {
		}
	}

	@FXML
	public void events(ActionEvent event) {
		try {
			rootPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/EventsList.fxml"));
			PaneDrawer.setContent(rootPane);
			PaneDrawer.setDefaultDrawerSize(0);
			PaneDrawer.toFront();
			hamburger.toFront();
			SideDrawer.close();
			PaneDrawer.open();
		} catch (IOException ex) {
		}
	}

	@FXML
	public void updateInfos(ActionEvent event) {
		try {
			rootPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
			PaneDrawer.setContent(rootPane);
			PaneDrawer.setDefaultDrawerSize(0);
			PaneDrawer.toFront();
			hamburger.toFront();
			SideDrawer.close();
			PaneDrawer.open();
		} catch (IOException ex) {
		}
	}

	@FXML
	public void logout(ActionEvent event) {
		MainApp.s = new Stage(StageStyle.UNDECORATED);
		try {
			Parent root = FXMLLoader.load(MainApp.class.getResource("/fxml/Authentication.fxml"));
			Scene scene = new Scene(root);
			MainApp.s.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainApp.s.show();
		MainApp.s1.close();
	}
}
