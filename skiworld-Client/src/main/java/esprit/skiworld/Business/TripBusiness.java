package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Events;
import Entity.Trip;
import Service.EventEJBRemote;
import Service.TripEJBRemote;

public class TripBusiness {
	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ear/skiworld-ejb/TripEJB!Service.TripEJBRemote" ;
	private TripEJBRemote proxy;
	
	public void addTrip(Trip trip){
		try {
			ctx = new InitialContext();
			proxy = (TripEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.addTrip(trip);
		
	}
	public void updateTrip(Trip trip) {
		try {
			ctx = new InitialContext();
			proxy = (TripEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateTrip(trip);
	}
	public void deleteTrip(Trip trip) {
		try {
			ctx = new InitialContext();
			proxy = (TripEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.deleteTrip(trip);
		
	}
	public List<Trip> DisplayAllTrips(){
		try {
			ctx = new InitialContext();
			proxy = (TripEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return proxy.DisplayAll();
		
	}
	public Long Nbrskier(int id){
		try {
			ctx = new InitialContext();
			proxy = (TripEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return proxy.nbrSkier(id);
		
	}
	

}
