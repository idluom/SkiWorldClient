package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Bill;
import Entity.Equipement;
import Service.BillEJBRemote;
import Service.EquipementEJBRemote;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

public class ShopController  implements  Initializable {
	@FXML
	private TableView<Equipement> TabShop;
	@FXML
	private TableColumn<Equipement, String> name;
	@FXML
	private TableColumn<Equipement,String> Categorie;
	@FXML
	private TableColumn<Equipement,String> type;
	@FXML
	private TableColumn<Equipement,Float> price;
	@FXML
	private TableColumn<Equipement,Float> shopq;
	
	@FXML
	private TableView<Equipement> TabInventory;
	@FXML
	private TableColumn<Equipement,Float> invq1;
	
	@FXML
	private TableColumn<Equipement,String> Name1;
	@FXML
	private TableColumn<Equipement,String> categorie1;
	@FXML
	private TableColumn<Equipement,String> type1;
	@FXML
	private TableColumn<Equipement,Float> price1;
	@FXML
	private TextField text ;
	@FXML
	private Button del ;
	@FXML
	private Button ad;
	@FXML
    private Button move;
    @FXML
    private TextField Tq;
	private static Equipement S ;
	private Button act ;
	ObservableList<Equipement> champs ;
	ObservableList<Equipement> champs1 ;
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 
		text.textProperty().addListener(new ChangeListener() {

		

	            @Override
	            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

	             equipementFilter((String) oldValue, (String) newValue);

	            }

	        });
		
		
	
		
			InitialContext ctx = null;
			EquipementEJBRemote proxy = null;
			
			try {
				ctx = new InitialContext();
				String jndiName ="/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
				proxy =(EquipementEJBRemote) ctx.lookup(jndiName);
				champs = FXCollections.observableArrayList(proxy.displayAllShopEquipement());
				champs1= FXCollections.observableArrayList(proxy.displayAllInventoryEquipement());
				
			} catch (NamingException e) {}
			name.setCellValueFactory(new PropertyValueFactory<>("name") );
		    Categorie.setCellValueFactory(new PropertyValueFactory<>("category"));  
		    type.setCellValueFactory(new PropertyValueFactory<>("type"));
		    price.setCellValueFactory(new PropertyValueFactory<>("price"));
		    shopq.setCellValueFactory(new PropertyValueFactory<>("shopquantity"));
		    
		    TabShop.setItems(champs);
		    
		    Name1.setCellValueFactory(new PropertyValueFactory<>("name"));
		    categorie1.setCellValueFactory(new PropertyValueFactory<>("category"));
		    type1.setCellValueFactory(new PropertyValueFactory<>("type"));
		    price1.setCellValueFactory(new PropertyValueFactory<>("price"));
		    invq1.setCellValueFactory(new PropertyValueFactory<>("inventoryquantity"));
		    TabInventory.setItems(champs1);
		    
		    
		    
	}
	
	
   private void equipementFilter(String oldValue, String newValue)
		{ ObservableList<Equipement> filterList =FXCollections.observableArrayList();
		   if (text == null || (newValue.length() < oldValue.length()) || newValue == null) {

	            TabShop.setItems(champs);
	            TabInventory.setItems(champs1);

	        } 
		   else {

	            newValue = newValue.toUpperCase();

	            for (Equipement E : TabShop.getItems()) {

	                String filterFirstName = E.getName();

	                

	                if (filterFirstName.toUpperCase().contains(newValue)) {

	                    filterList.add(E);

	                }

	            }
	            TabShop.setItems(filterList);

	        }
		
	}
	
   @FXML
   void supprimer(ActionEvent event) {
	   
	   Alert al = new Alert(Alert.AlertType.WARNING);
	   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	  
       if (TabInventory.getSelectionModel().getSelectedItem() == null) {
           al.setTitle(" erreur !! ");
           al.setHeaderText("pick an Equipement from inventory");
           al.showAndWait();
           return;
       }
       else
       {
    	   
	   InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
		BillEJBRemote proxy1 = null ;
		try {
			ctx = new InitialContext();
			String jndiName ="/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			proxy =(EquipementEJBRemote) ctx.lookup(jndiName);
			String jndiName1 ="/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			proxy1 =(BillEJBRemote) ctx.lookup(jndiName1);
		    
		       } catch (NamingException e) {}
		
		//Equipement E = new Equipement();
		Equipement E =TabInventory.getSelectionModel().getSelectedItem() ;
		List<Bill> l = proxy1.FindByEquipementName(E.getName());
		proxy.delete(E);
		TabInventory.refresh();
		for(Bill B : l)
		{ proxy1.delete(B);  }
		
	    alert.setTitle(" delete ");
        alert.setHeaderText("delete successful");
        alert.showAndWait();
       }
   }

   @FXML
   void ajout(ActionEvent event) {
	   try {
			MainApp.changeScene("/fxml/AddShop.fxml", "AddShop");
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
   }
   
   @FXML
   void InventoryUpdate(MouseEvent event) throws IOException {

	   if(event.getClickCount()==2){
		   ShopController.setE(TabInventory.getSelectionModel().getSelectedItem());
		   MainApp.changeScene("/fxml/UpdateShop.fxml", "UpdteShop");
	   }
   }
   
   @FXML
   void refresh(ActionEvent event) {
    champs.remove(champs);
    champs1.remove(champs1);
    InitialContext ctx = null;
	EquipementEJBRemote proxy = null;
	try {
		ctx = new InitialContext();
		String jndiName ="/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
		proxy =(EquipementEJBRemote) ctx.lookup(jndiName);
	} catch (NamingException e) {}

    champs = FXCollections.observableArrayList(proxy.displayAllShopEquipement());
	champs1= FXCollections.observableArrayList(proxy.displayAllInventoryEquipement());
	TabShop.setItems(champs);
	TabInventory.setItems(champs1);
	
    
   }
   @FXML
   void moveToShop(ActionEvent event) {
	   if(Tq.getText().length()==0)
	   { Alert al = new Alert(Alert.AlertType.WARNING);
	    al.setTitle(" erreur !! ");
	    al.setHeaderText(" Check the quantity");
	    al.showAndWait();
	   return ;
		   
	   }
	   if (TabInventory.getSelectionModel().getSelectedItem() == null) {
		   Alert al = new Alert(Alert.AlertType.WARNING);
		   al.setTitle(" erreur !! ");
           al.setHeaderText("pick an Equipement from inventory");
           al.showAndWait();
           return;
       }
	   
	   InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
    Equipement E =TabInventory.getSelectionModel().getSelectedItem() ;
    if (E.getInventoryquantity() < Float.valueOf(Tq.getText())  )
    { Alert al = new Alert(Alert.AlertType.WARNING);
    al.setTitle(" erreur !! ");
    al.setHeaderText(" Quantity unavailable");
    al.showAndWait();
   return ;
    	
    }
    else{
     E.setShopquantity(E.getShopquantity()+Float.valueOf(Tq.getText()));
     E.setInventoryquantity(E.getInventoryquantity()-Float.valueOf(Tq.getText()));
     try {
 		ctx = new InitialContext();
 		String jndiName ="/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
 		proxy =(EquipementEJBRemote) ctx.lookup(jndiName);
 	} catch (NamingException e) {}
     proxy.updateEquipement(E);
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     alert.setTitle(" Add to Shop ");
     alert.setHeaderText("Add successful");
     alert.showAndWait();
    }
   
   
   
   }
   
   

public static Equipement getE() {
	return S;
}


public static void setE(Equipement e) {
	S = e;
}
   
   
	

}
