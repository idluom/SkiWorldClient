package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;
import Entity.Training;
import Service.TrackEJBRemote;
import Service.TrainingEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class TrackController implements Initializable {

	MainApp application;
	@FXML
	private TableView<Track> TableTrack;
	@FXML
	private TableColumn<Track, String> lengthCol;
	@FXML
	private TableColumn<Track, String> diffCol;
	@FXML
	private TableColumn<Track, String> priceCol;
	@FXML
	private TextField RechercheTF;

	public static Track comp;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		RechercheTF.textProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {

				filterMembreList((String) oldValue, (String) newValue);
			}

		});
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

	@FXML
	private void DeleteTrack() {
		comp = TableTrack.getSelectionModel().getSelectedItem();
		if (comp == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("No Track Selected");
			alert.showAndWait();
		}
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TrackEJBRemote proxy;
		try {
			proxy = (TrackEJBRemote) ctx.lookup("/skiworld-ejb/TrackEJB!Service.TrackEJBRemote");
			comp = TableTrack.getSelectionModel().getSelectedItem();

			proxy.deleteTrack(comp);
			int selectedIndex = TableTrack.getSelectionModel().getSelectedIndex();
			TableTrack.getItems().remove(selectedIndex);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private void Add() {
		try {
			MainApp.changeScene("/fxml/AjoutTrack.fxml", "Add Track");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void Edit() {

		comp = TableTrack.getSelectionModel().getSelectedItem();
		if (comp == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("No Track Selected");
			alert.showAndWait();
		}
		System.out.println("ok; " + comp);
		try {
			MainApp.changeScene("/fxml/UpdateTrack.fxml", "Update Track");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void filterMembreList(String oldValue, String newValue) {
		ObservableList<Track> filteredList = FXCollections.observableArrayList();

		if (RechercheTF == null || (newValue.length() < oldValue.length()) || newValue == null) {
			InitialContext ctx = null;
			try {
				ctx = new InitialContext();
			} catch (NamingException e) {

				e.printStackTrace();
			}
			TrackEJBRemote proxy;
			try {
				proxy = (TrackEJBRemote) ctx.lookup("/skiworld-ejb/TrackEJB!Service.TrackEJBRemote");
				ObservableList<Track> champs = FXCollections.observableArrayList(proxy.findAll());
				TableTrack.setItems(champs);

			} catch (NamingException e) {

				e.printStackTrace();
			}

		} else {

			newValue = newValue.toUpperCase();

			for (Track tracks : TableTrack.getItems()) {

				String filterFirstName = tracks.getDifficulty();

				// String filterLastName = clubss.getPrice();

				if (filterFirstName.toUpperCase().contains(newValue)) {

					filteredList.add(tracks);

				}

			}
			TableTrack.setItems(filteredList);

		}

	}
}
