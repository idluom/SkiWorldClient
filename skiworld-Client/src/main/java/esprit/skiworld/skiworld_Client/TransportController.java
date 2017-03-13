package esprit.skiworld.skiworld_Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.List;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.text.TabableView;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Entity.Comment;
import Entity.Track;
import Entity.Transport;
import Service.TrackEJBRemote;
import Service.TransportEJB;
import Service.TransportEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import esprit.skiworld.Business.TransportBusiness;

public class TransportController implements Initializable{
	@FXML
	private TableColumn<Transport, String> depPlaceColumn;
	@FXML
	private TableColumn <Transport, Date>depDateColumn;
	@FXML
	private TableColumn <Transport, String>arrPlaceColumn;
	@FXML
	private TableColumn<Transport, Date> arrDateColumn;
	@FXML
	private TableColumn<Transport, Integer> nPlacesColumn;
	@FXML
	private TableColumn <Transport, Float>costColumn;
	@FXML
	private TableColumn <Transport, String>typeColumn;
	@FXML
	private JFXButton addButton;
	@FXML
	private JFXButton modifyButton;
	@FXML
	private JFXButton deleteButton;
	@FXML
	private TableView  <Transport> transTable; 
	@FXML
	private JFXTextField  searchTF;
	private PopOver historyPopOver;
	ObservableList<Transport> transportOList;
	ObservableList<String> trans;
	public static Transport upTrans;
	// Event Listener on JFXButton[#addButton].onAction
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources){
		searchTF.textProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				filterTransport((String) oldValue, (String) newValue);				
			}
		});

			
		transTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		transportOList = FXCollections.observableArrayList(new TransportBusiness().getAllMeanTransport());
		depPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("departurePlace"));
		depDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
		arrPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalPlace"));
		arrDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		nPlacesColumn.setCellValueFactory(new PropertyValueFactory<>("numberPlaces"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		costColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		transTable.setItems(transportOList);
		
	}
	@FXML
	public void addTrans(ActionEvent event) {
		try {
			MainApp.changeScene("/fxml/AddTransport.fxml", "Add Mean Of Transport");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		historyPopOver = new PopOver();
//	    historyPopOver.setDetachable(false);
//	    historyPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
//	    ArrayList<String> t = new ArrayList<String>();
//	    t.add("Bilel");
//	  //  historyPopOver.setOnHidden((event) -> sendMsgHistoryBtn.setSelected(false));
//	    ListView<String> list = new ListView<>();
//	    list.setMaxHeight(150);
//	    list.setMaxWidth(300);
//	    list.getStyleClass().add("pop_over_list");
//	    trans = FXCollections.observableArrayList(t);
//	    list.setItems(trans);
//	  //  list.setCellFactory(listView -> new SendHistoryCellFactory(sendMsgList, sendMsgTextField, historyPopOver));
//	    historyPopOver.setContentNode(list);
//	    historyPopOver.show(list);
//	    historyPopOver.set
	}
	// Event Listener on JFXButton[#modifyButton].onAction
	@FXML
	public void modifyTrans(ActionEvent event) {
		upTrans = transTable.getSelectionModel().getSelectedItem();
		try {
			MainApp.changeScene("/fxml/UpdateTransport.fxml", "Update Mean Of Transport");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Event Listener on JFXButton[#deleteButton].onAction
	@FXML
	public void deleteTrans(ActionEvent event) {
		Transport tr = new Transport();
		tr = transTable.getSelectionModel().getSelectedItem();
		new TransportBusiness().deleteMeanTransport(tr);

	}
	private void filterTransport(String oldValue, String newValue) {
		ObservableList<Transport> filteredList = FXCollections.observableArrayList();

		if (searchTF == null || (newValue.length() < oldValue.length()) || newValue == null) {
			
				ObservableList<Transport> champs = FXCollections.observableArrayList(new TransportBusiness().getAllMeanTransport());
				transTable.setItems(champs);

			

		} else {

			newValue = newValue.toUpperCase();

			for (Transport transport : transTable.getItems()) {

				String filterArrival = transport.getArrivalPlace();

				// String filterLastName = clubss.getPrice();

				if (filterArrival.toUpperCase().contains(newValue)) {

					filteredList.add(transport);

				}

			}
			transTable.setItems(filteredList);
	}
}
}
