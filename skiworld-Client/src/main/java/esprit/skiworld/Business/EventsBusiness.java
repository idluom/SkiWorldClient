package esprit.skiworld.Business;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Events;
import Service.EventEJBRemote;

public class EventsBusiness {
	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ejb/EventEJB!Service.EventEJBRemote" ;
	private EventEJBRemote proxy;
	public void addEvent(Events event){
		try {
			ctx = new InitialContext();
			proxy = (EventEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.addEvent(event);
		
	}
	public void updateEvent(Events event) {
		try {
			ctx = new InitialContext();
			proxy = (EventEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateEvent(event);
		
	}
	public void deleteEvent(Events event) {
		try {
			ctx = new InitialContext();
			proxy = (EventEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.deleteEvent(event);
		
	}
	public Events findEventById(Long id) {
		try {
			ctx = new InitialContext();
			proxy = (EventEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findEventById(id);
	
		
	}
	public List<Events> findAllEvent(){
		try {
			ctx = new InitialContext();
			proxy = (EventEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return proxy.findAllEvent();
		
	}
	public boolean findEventByDate(Date date){
		try {
			ctx = new InitialContext();
			proxy = (EventEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return proxy.findEventByDate(date);
	}
}
