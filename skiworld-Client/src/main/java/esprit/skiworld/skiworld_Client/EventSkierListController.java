package esprit.skiworld.skiworld_Client;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Events;
import Entity.Skier;
import Service.CommentEJBRemote;
import Service.SkierEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

import javafx.scene.layout.AnchorPane;

import javafx.scene.image.ImageView;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class EventSkierListController implements Initializable {
	@FXML
	private AnchorPane Conteneur;
	@FXML
	private TableView<Skier> tableSkiers;
	@FXML
	private TableColumn<Skier, String> name;
	@FXML
	private TableColumn<Skier, String> lname;
	@FXML
	private TableColumn<Skier, Integer> pin;
	@FXML
	private ProgressBar ProgressLoading;
	@FXML
	private ImageView loading;
	
	ObservableList<Skier> champs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		try {
			InitialContext ctx = new InitialContext();
			SkierEJBRemote proxy=(SkierEJBRemote)ctx.lookup("/skiworld-ear/skiworld-ejb/SkierEJB!Service.SkierEJBRemote");
			champs=FXCollections.observableArrayList(proxy.findSkiersByEvent(EventsListController.event2));
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
		 
		 
		
		
		
		name.setCellValueFactory(new PropertyValueFactory<Skier, String>("name"));
		lname.setCellValueFactory(new PropertyValueFactory<Skier, String>("lastName"));
		pin.setCellValueFactory(new PropertyValueFactory<Skier, Integer>("pin"));
		tableSkiers.setItems(champs);
		

	}

}
