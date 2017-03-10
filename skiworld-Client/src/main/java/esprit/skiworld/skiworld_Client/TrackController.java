package esprit.skiworld.skiworld_Client;


import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;

import Service.TrackEJBRemote;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class TrackController implements Initializable {
	@FXML
	private TableView <Track>TableTrack;
	@FXML
	private TableColumn <Track,String>lengthCol;
	@FXML
	private TableColumn <Track,String> diffCol;
	@FXML
	private TableColumn <Track,String> priceCol;
	@FXML
	private TextField RechercheTF;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TrackEJBRemote proxy = (TrackEJBRemote) ctx.lookup("/skiworld-ejb/TrackEJB!Service.TrackEJBRemote");
			ObservableList<Track> champs = FXCollections.observableArrayList(proxy.findAll());
			System.out.println(champs);
			TableTrack.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
			
			priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
			diffCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
			
		
			TableTrack.setItems(champs);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
