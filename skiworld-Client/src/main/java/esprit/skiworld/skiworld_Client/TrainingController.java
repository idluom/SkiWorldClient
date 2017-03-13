package esprit.skiworld.skiworld_Client;

import java.io.IOException;

import java.net.URL;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;
import Entity.Training;

import Service.TrainingEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrainingController implements Initializable {

	@FXML
	private TableColumn<Training, String> BDCol;
	@FXML
	private TableColumn<Training, String> EDCol;
	@FXML
	private TableColumn<Training, String> levelCol;
	@FXML
	private TableColumn<Training, String> priceCol;
	@FXML
	private TableColumn<Training, String> NumberCol;
	@FXML
	private Button addTraining;
	@FXML
	private Button deleteTraining;
	@FXML
	private Button editTraining;
	@FXML
	private TextField RechercheTF;
	@FXML
	private TableView<Training> TableTrack = null;
	
	List<Track> clubs = new ArrayList<>();
	public static Training comp;
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RechercheTF.textProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {

				try {
					filterMembreList((String) oldValue, (String) newValue);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		InitialContext ctx = null;

		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TrainingEJBRemote proxy = null;
		try {
			proxy = (TrainingEJBRemote) ctx.lookup("/skiworld-ejb/TrainingEJB!Service.TrainingEJBRemote");
		} catch (NamingException e) {

			e.printStackTrace();
		}
		ObservableList<Training> champs = FXCollections.observableArrayList(proxy.findAllTraining());
		System.out.println(champs);

		TableTrack.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		BDCol.setCellValueFactory(new PropertyValueFactory<>("begeningDate"));
		EDCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		levelCol.setCellValueFactory(new PropertyValueFactory<>("level"));
		NumberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		TableTrack.setItems(champs);

	}

	private void filterMembreList(String oldValue, String newValue) throws ParseException {
		ObservableList<Training> filteredList = FXCollections.observableArrayList();

		if (RechercheTF == null || (newValue.length() < oldValue.length()) || newValue == null) {
			InitialContext ctx = null;
			try {
				ctx = new InitialContext();
			} catch (NamingException e) {

				e.printStackTrace();
			}
			TrainingEJBRemote proxy;
			try {
				proxy = (TrainingEJBRemote) ctx.lookup("/skiworld-ejb/TrainingEJB!Service.TrainingEJBRemote");
				ObservableList<Training> champs = FXCollections.observableArrayList(proxy.findAllTraining());
				TableTrack.setItems(champs);

			} catch (NamingException e) {

				e.printStackTrace();
			}

		} else {

			newValue = newValue.toUpperCase();

			for (Training trainings : TableTrack.getItems()) {

				String filterFirstName = trainings.getLevel();

				// String filterLastName = clubss.getPrice();

				if (filterFirstName.toUpperCase().contains(newValue)) {

					filteredList.add(trainings);

				}

			}
			TableTrack.setItems(filteredList);
			System.out.println("fff");
		}
	}
	
	@FXML
	private void Delete() {
		comp = TableTrack.getSelectionModel().getSelectedItem();
		if (comp == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("No Training Selected");
			alert.showAndWait();
		}
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TrainingEJBRemote proxy;
		try {
			proxy = (TrainingEJBRemote) ctx.lookup("/skiworld-ejb/TrainingEJB!Service.TrainingEJBRemote");
			comp = TableTrack.getSelectionModel().getSelectedItem();
			

			proxy.deleteTraining(comp);
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
			MainApp.changeScene("/fxml/AjoutTraining.fxml", "Add Training");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	private void Update() {

		comp = TableTrack.getSelectionModel().getSelectedItem();
		System.out.println(comp);
		if (comp == null) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("SELECTED");
			alert.setHeaderText("No Training Selected");
			alert.showAndWait();
		}
		
		try {
			MainApp.changeScene("/fxml/UpdateTraining.fxml", "Update Training");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
