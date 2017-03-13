package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Room;
import Service.AdminEJBRemote;
import Service.RoomEJBRemote;

public class RoomBusiness {
	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ear/skiworld-ejb/RoomEJB!Service.RoomEJBRemote" ;
	private RoomEJBRemote proxy;
	public void addRoom(Room room){
		try {
			ctx = new InitialContext();
			proxy = (RoomEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.addRoom(room);
		
	}
	public void updateRoom(Room room) {
		try {
			ctx = new InitialContext();
			proxy = (RoomEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateRoom(room);
		
	}
	public void deleteRoom(Room room) {
		try {
			ctx = new InitialContext();
			proxy = (RoomEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.deleteRoom(room);
		
	}
	public Room findRoomById(Long id) {
		try {
			ctx = new InitialContext();
			proxy = (RoomEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findRoomById(id);
	
		
	}
	public List<Room> findAllRoom(){
		try {
			ctx = new InitialContext();
			proxy = (RoomEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		return proxy.findAllRoom();
		
	}
	

}
