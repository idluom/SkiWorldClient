package esprit.skiworld.Business;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Hotel;
import Service.HotelEJBRemote;

public class HotelBusiness {
	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ear/skiworld-ejb/HotelEJB!Service.HotelEJBRemote" ;
	private HotelEJBRemote proxy;
	
	public void updateHotel(Hotel hotel){
		try {
			ctx = new InitialContext();
			proxy = (HotelEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateHotel(hotel);
	}
	public Hotel findHotelById(Long id){
		try {
			ctx = new InitialContext();
			proxy = (HotelEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findHotelById(id);
	}
}
