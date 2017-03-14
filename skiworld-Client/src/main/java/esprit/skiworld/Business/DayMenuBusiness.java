package esprit.skiworld.Business;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.DayMenu;
import Entity.Product;
import Entity.Transport;
import Service.DayMenuEJBRemote;
import Service.ProductEJBRemote;
import Service.TransportEJBRemote;

public class DayMenuBusiness {

	private InitialContext ctx = null;
	private final String jndiNameProduct = "/skiworld-ejb/DayMenuEJB!Service.DayMenuEJBRemote";
	private DayMenuEJBRemote proxy;

	public DayMenuBusiness() {

	}

	public boolean addMenu(DayMenu dayMenu) {
		try {
			ctx = new InitialContext();
			proxy = (DayMenuEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.addMenu(dayMenu);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updMenu(DayMenu dayMenu) {
		try {
			ctx = new InitialContext();
			proxy = (DayMenuEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.updateMenu(dayMenu);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteProd(DayMenu dayMenu) {
		try {
			ctx = new InitialContext();
			proxy = (DayMenuEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.deleteMenu(dayMenu);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public DayMenu findMenuID (Long idMenu){
		try {
			ctx = new InitialContext();
			proxy = (DayMenuEJBRemote) ctx.lookup(jndiNameProduct);
			DayMenu dm = proxy.findMenuById(idMenu);
			return dm;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public DayMenu findMenuDate (Date d){
		try {
			ctx = new InitialContext();
			proxy = (DayMenuEJBRemote) ctx.lookup(jndiNameProduct);
			DayMenu dm = proxy.findMenuByDate(d);
			return dm;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean deleteProd2(DayMenu dayMenu) {
		try {
			ctx = new InitialContext();
			proxy = (DayMenuEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.deleteMenu2(dayMenu);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
