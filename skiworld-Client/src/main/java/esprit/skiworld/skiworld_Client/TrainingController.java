package esprit.skiworld.skiworld_Client;




import java.awt.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;
import Entity.Training;
import Service.TrainingEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
	 private void filterMembreList(String oldValue, String newValue){
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
//	
//	  @FXML
//	    private void addTraining(ActionEvent event) {
//	       
//	    }
//	  @FXML
//	    private void deleteTraining(ActionEvent event) {
//	       
//	    }
//	  @FXML
//	    private void editTraining(ActionEvent event) {
//	       
//	    }
	  

}
