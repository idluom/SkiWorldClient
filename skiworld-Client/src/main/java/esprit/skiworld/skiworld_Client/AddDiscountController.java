package esprit.skiworld.skiworld_Client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Discount;
import Service.DiscountEJBRemote;
import Service.EquipementEJBRemote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class AddDiscountController implements Initializable{
	  @FXML
	    private DatePicker debut;

	    @FXML
	    private DatePicker fin;

	    @FXML
	    private ChoiceBox<String> percentage;

	@FXML
    private Button add;

    @FXML
    private Button bac;

    ObservableList<String> percent = FXCollections.observableArrayList("0","10","20","30","40","50","60","70","80");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		percentage.setValue("0");
		percentage.setItems(percent);
		
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
	    void addDiscount(ActionEvent event) {
	    	if(debut.getValue()==null || fin.getValue()== null  )
	    	{
	    		 Alert alert = new Alert(Alert.AlertType.WARNING);
				 alert.setTitle(" error ");
		         alert.setHeaderText("Check Fields");
		         alert.showAndWait();
		         
		         return ;
	    	}
	    	Discount D = new Discount();
	    	D.setBeginning(java.sql.Date.valueOf(debut.getValue()));
	        D.setEnd(java.sql.Date.valueOf(fin.getValue()));
	        D.setPercentage( Float.valueOf(percentage.getSelectionModel().getSelectedItem()));
	        InitialContext ctx = null;
	        DiscountEJBRemote proxy=null ;
	        try {
				ctx = new InitialContext();
				String jndiName ="/skiworld-ear/skiworld-ejb/DiscountEJB!Service.DiscountEJBRemote";
				proxy =(DiscountEJBRemote) ctx.lookup(jndiName);
			} catch (NamingException e) {}
	          
	           
	         Date date = new Date();
	         if(D.getBeginning().before(date) ==true  || D.getEnd() .before(D.getBeginning())==true) 
	         {
	        	 Alert alert = new Alert(Alert.AlertType.WARNING);
				 alert.setTitle(" error ");
		         alert.setHeaderText("Invalid Date");
		         alert.showAndWait();
		         System.out.println(date);
		         return ;
	         }
	         
	        
			proxy.addDiscount(D);
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			 alert.setTitle(" Add ");
	         alert.setHeaderText("Add successful");
	         alert.showAndWait();
			
			
	    }


	
}
