package esprit.skiworld.skiworld_Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.rowset.serial.SerialBlob;

import Entity.Bill;
import Entity.Equipement;
import Entity.Shop;
import Service.BillEJBRemote;
import Service.EquipementEJBRemote;
import Service.InventoryEJBRemote;
import Service.ShopEJBRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sun.misc.IOUtils;

public class AddShopController implements  Initializable {

	@FXML
    private TextField name;

    @FXML
    private TextField categorie;

    @FXML
    private TextField type;

    @FXML
    private TextField price;

    @FXML
    private TextField sq;

    @FXML
    private TextField tq;

    @FXML
    private Button add;
    @FXML
    private ImageView imgEquipement;

    @FXML
    private Button select;
    @FXML
    private Button bac;
    Blob image;
    
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
	@FXML
    void addeEquipement(ActionEvent event) {
	  Equipement E = new Equipement();
	  Bill B =new Bill();
	  InitialContext ctx = null;
		EquipementEJBRemote proxy = null;
		InventoryEJBRemote proxy1 = null ;
		ShopEJBRemote proxy2 = null ;
		BillEJBRemote proxy3 =null ;
		try {
			ctx = new InitialContext();
			String jndiName ="/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote";
			String jndiName1 ="/skiworld-ear/skiworld-ejb/InventoryEJB!Service.InventoryEJBRemote";
			String jndiName2 ="/skiworld-ear/skiworld-ejb/ShopEJB!Service.ShopEJBRemote";
			String jndiName3 ="/skiworld-ear/skiworld-ejb/BillEJB!Service.BillEJBRemote";
			proxy =(EquipementEJBRemote) ctx.lookup(jndiName);
			proxy1 =(InventoryEJBRemote) ctx.lookup(jndiName1);
			proxy2=(ShopEJBRemote)ctx.lookup(jndiName2);
			proxy3 =(BillEJBRemote)ctx.lookup(jndiName3);
			
			
		} catch (NamingException e) {}
		
		
	  if(name.getText().length()==0 || categorie.getText().length()==0||price.getText().length()==0||type.getText().length()==0||sq.getText().toString()==""||tq.getText().length()==0)
		{Alert al = new Alert(Alert.AlertType.WARNING);
		  al.setTitle(" error !! ");
          al.setHeaderText("check more ");
          al.showAndWait();
          return;
			
		}
		
		E.setName(name.getText());
		E.setCategory(categorie.getText());
		E.setPrice(Float.valueOf(price.getText()));
		E.setShopquantity(Float.valueOf(sq.getText()));
		E.setType(type.getText());
		E.setInventoryquantity(Float.valueOf(tq.getText()));
		Shop s = proxy2.findShopById(new Long(3));
		E.setShop(s);
		E.setInventory(s.getInventory());
		E.setPhoto(image);
		Date d = new Date();
		B.setDate(d);
		B.setEquipementName(name.getText());
		B.setCost(((E.getPrice()/100)*60)*(E.getInventoryquantity()+E.getShopquantity()));
		proxy3.addBill(B);
		 proxy.addEquipement(E);
		 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		 alert.setTitle(" Add ");
         alert.setHeaderText("Add successful");
         alert.showAndWait();
		
	 
		
		

    }
	
	@FXML
    void clickImage(ActionEvent event) throws SQLException, IOException {
		FileChooser fileChooser = new FileChooser();

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            fileChooser.setTitle("Open resource file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.jpg"));
            //Chapitre c = new Chapitre();
            if (selectedFile != null) {

                File path = selectedFile.getAbsoluteFile();
                byte[] b = IOUtils.readFully(new FileInputStream(path), -1, true);
                Blob ff = new SerialBlob(b);
                image = ff;
                Image i = new Image(selectedFile.toURI().toString());
                imgEquipement.setImage(i);
                System.out.println("hhhh");
            }
        }

    }
	  @FXML
	    void Back(ActionEvent event) {
		  try {
				MainApp.changeScene("/fxml/Shop.fxml", "AddShop");
			} catch (IOException e) {
				e.printStackTrace();
			}

	    }
	
	

}
