package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Bill;

import Service.BillEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillController implements Initializable {
	@FXML
    private TableView<Bill> TabBill;
	@FXML
    private TableColumn<Bill,Date > date;

    @FXML
    private TableColumn<Bill, Float> cost;

    @FXML
    private TableColumn<Bill,String> eq;

    @FXML
    private Button ref;

    @FXML
    private Button bac;

    @FXML
    private TextField T;

    ObservableList<Bill> champs ;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		T.textProperty().addListener(new ChangeListener() {

			

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

             billFilter((String) oldValue, (String) newValue);

            }

        });
		
		
		
		
		
		InitialContext ctx = null;
		BillEJBRemote proxy = null;
		
		try {
			ctx = new InitialContext();
			String jndiName ="/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			proxy =(BillEJBRemote) ctx.lookup(jndiName);
			champs = FXCollections.observableArrayList(proxy.DisplayAllBills());	
			
		} catch (NamingException e) {}
		
		date.setCellValueFactory(new PropertyValueFactory<>("date") );
		cost.setCellValueFactory(new PropertyValueFactory<>("Cost") );
		eq.setCellValueFactory(new PropertyValueFactory<>("equipementName") );
		TabBill.setItems(champs);
		
		
		
	}
	   
	
	
	
	
	    @FXML
	    void Back(ActionEvent event) {
	    	try {
	 			MainApp.changeScene("/fxml/Menu.fxml", "AddShop");
	 		} catch (IOException e) {
	 			e.printStackTrace();
	 		}
	    }

	    @FXML
	    void Refresh(ActionEvent event) {
	    	champs.remove(champs);
	    	InitialContext ctx = null;
			BillEJBRemote proxy = null;
			
			try {
				ctx = new InitialContext();
				String jndiName ="/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
				proxy =(BillEJBRemote) ctx.lookup(jndiName);
					
				
			} catch (NamingException e) {}
			champs = FXCollections.observableArrayList(proxy.DisplayAllBills());
			TabBill.setItems(champs);

	    }
	    
	    
	    private void billFilter(String oldValue, String newValue)
		{ ObservableList<Bill> filterList =FXCollections.observableArrayList();
		   if (T == null || (newValue.length() < oldValue.length()) || newValue == null) {

	            TabBill.setItems(champs);
	           

	        } 
		   else {

	            newValue = newValue.toUpperCase();

	            for (Bill E : TabBill.getItems()) {

	                String filterFirstName = E.getEquipementName();

	                

	                if (filterFirstName.toUpperCase().contains(newValue)) {

	                    filterList.add(E);

	                }

	            }
	            TabBill.setItems(filterList);

	        }
		
	}
		
	

}
