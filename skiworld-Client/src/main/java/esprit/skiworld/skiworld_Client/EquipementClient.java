package esprit.skiworld.skiworld_Client;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import Entity.Discount;
import Entity.Equipement;
import Entity.Inventory;
import Entity.Shop;
import Service.DiscountEJBRemote;
import Service.EquipementEJBRemote;
import Service.InventoryEJBRemote;
import Service.ShopEJBRemote;

public class EquipementClient {
	 @PersistenceContext(unitName = "skiworld-ejb")
	static
		EntityManager em = null;

	public static void main(String[] args) throws NamingException {
		InitialContext ctx = new InitialContext();
		EquipementEJBRemote proxy =(EquipementEJBRemote) ctx.lookup("/skiworld-ear/skiworld-ejb/EquipementEJB!Service.EquipementEJBRemote");
		InventoryEJBRemote proxy1 =(InventoryEJBRemote) ctx.lookup("/skiworld-ear/skiworld-ejb/InventoryEJB!Service.InventoryEJBRemote") ;
		DiscountEJBRemote proxy3 =(DiscountEJBRemote) ctx.lookup("/skiworld-ear/skiworld-ejb/DiscountEJB!Service.DiscountEJBRemote");
		ShopEJBRemote proxy4=(ShopEJBRemote) ctx.lookup("/skiworld-ear/skiworld-ejb/ShopEJB!Service.ShopEJBRemote");
		/*Equipement E = new Equipement() ;
		E.setCategory("B");
	    E.setName("skibord");
	    E.setPrice(55f);
	    E.setType("pro");
	    proxy.addEquipement(E);*/
	  
	  
	   /*Equipement E = proxy.findEquipementById(new Long( 2));
	   Inventory I = new Inventory();
	   I.setCapacity(new Long(455));
	   Shop S = new Shop();
	   Discount D = new Discount();
	   List <Equipement> l = new ArrayList <> ();
	   S.setInventory(I);
	   S.setName("lala");
	   D.setListEquipements(l);
	   S.setListEquipements(l);
	   I.setListEquipements(l);
	   I.setShop(S);
	   D.setPercentage(50f);
	   proxy4.addShop(S);
	   proxy3.addDiscount(D);
	   E.setPrice(66f);
	 
	   
	  
	   E.setDiscount(D);
	   E.setInventory(I);*/
	  // Shop S=proxy4.findShopById(new Long(3));
	   // E.setShop(S);
	   //Discount D =proxy3.findDiscountById(new Long(5));
	  // E.setDiscount(D);
	   
	   
	   
	   //proxy.updateEquipement(E);
	 /* Shop p = new Shop();
	  p.setName("kkk");
	  Inventory I = new Inventory();
	  I.setCapacity(new Long(70));
	  p.setInventory(I);
	  proxy4.addShop(p);*/
	  //Shop s = proxy4.findShopById(new Long(14)) ;
	  
	  //Inventory i = proxy1.findInventoryByID(new Long(17));
	 // Inventory i = s.getInventory();
	 // System.out.println(""+i.getId());
	 // Equipement E = new Equipement();
	 // E.setCategory("ski");
	  //E.setName("selmi");
	  //E.setPrice(69f);
	  //E.setCategory("cat");
	 // Inventory I = new Inventory();
	 // I.setCapacity(new Long(15));
	 // Shop S = new Shop();
	  //S.setName("Dali");
	 // S.setInventory(I);
	  //I.setShop(S);
	 /* Discount D = new  Discount() ;
	  D.setPercentage(50f);
	  try {
		D.setBeginning(new SimpleDateFormat("yyyy-MM-dd").parse("2007-01-01"));
		D.setEnd(new SimpleDateFormat("yyyy-MM-dd").parse("2007-02-01"));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	 
	  proxy4.addShop(S);
	  proxy3.addDiscount(D);*/
	  //proxy.addEquipement(E);
	 // Equipement E = proxy.findEquipementById(new Long(2));
	 // Shop s= proxy4.findShopById( new Long(14));
	  //Discount d =proxy3.findDiscountById(new Long(20));
	 //proxy.delete(E);
	  //proxy4.delete(s);
	 // proxy1.delete(s.getInventory());
	//proxy4.addShop(S);
	  //Shop s= proxy4.findShopById(new Long(25));
	 // System.out.println(s.getInventory().getCapacity());
	 // Inventory I = proxy1.findInventoryByID(s.getInventory().getId());
	 // proxy1.delete(I);
	  
	/*List<Equipement> l = proxy.displayAllShopEquipement();
	for(Equipement E : l)
	{
		System.out.println(E.getId());
	}*/
	//E.setPrice(965f);
	//proxy.delete(E);
		Discount D = new Discount();
		D=proxy3.findDiscountById(new Long(30));
		 System.out.println(D.getPercentage());

		 
		

	 }

}
