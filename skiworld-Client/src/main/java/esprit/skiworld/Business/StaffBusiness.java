package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Admin;
import Entity.HotelManager;
import Entity.Member;
import Entity.RestaurantOwner;
import Entity.ShopOwner;
import Service.AdminEJBRemote;

public class StaffBusiness {
	private InitialContext ctx = null;
	private final String jndiNameAdmin = "/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote" ;
	private AdminEJBRemote proxy;
	
	public StaffBusiness() {
		
	}
	
	public void addMember(Member member, String role) throws NamingException {
		ctx = new InitialContext();
		proxy = (AdminEJBRemote) ctx.lookup(jndiNameAdmin);
		if (role == "Admin"){
			Admin admin = new Admin();
			admin.setAddress(member.getAddress());
			admin.setBirthDate(member.getBirthDate());
			admin.setCin(member.getCin());
			admin.setFirstName(member.getFirstName());
			admin.setLastName(member.getLastName());
			admin.setLogin(member.getLogin());
			admin.setMail(member.getMail());
			admin.setNumTel(member.getNumTel());
			admin.setPassword(member.getPassword());
			proxy.addAdmin(admin);
		} else if (role == "Hotel Manager") {
			HotelManager admin = new HotelManager();
			admin.setAddress(member.getAddress());
			admin.setBirthDate(member.getBirthDate());
			admin.setCin(member.getCin());
			admin.setFirstName(member.getFirstName());
			admin.setLastName(member.getLastName());
			admin.setLogin(member.getLogin());
			admin.setMail(member.getMail());
			admin.setNumTel(member.getNumTel());
			admin.setPassword(member.getPassword());
			proxy.addAdmin(admin);
		} else if (role == "Shop Owner") {
			ShopOwner admin = new ShopOwner();
			admin.setAddress(member.getAddress());
			admin.setBirthDate(member.getBirthDate());
			admin.setCin(member.getCin());
			admin.setFirstName(member.getFirstName());
			admin.setLastName(member.getLastName());
			admin.setLogin(member.getLogin());
			admin.setMail(member.getMail());
			admin.setNumTel(member.getNumTel());
			admin.setPassword(member.getPassword());
			proxy.addAdmin(admin);
		} else if (role == "Restaurant Owner") {
			RestaurantOwner admin = new RestaurantOwner();
			admin.setAddress(member.getAddress());
			admin.setBirthDate(member.getBirthDate());
			admin.setCin(member.getCin());
			admin.setFirstName(member.getFirstName());
			admin.setLastName(member.getLastName());
			admin.setLogin(member.getLogin());
			admin.setMail(member.getMail());
			admin.setNumTel(member.getNumTel());
			admin.setPassword(member.getPassword());
			proxy.addAdmin(admin);
		}
			
	}
	
	public List<Member> getAllMembers() throws NamingException {
		ctx = new InitialContext();
		proxy = (AdminEJBRemote) ctx.lookup(jndiNameAdmin);
		return proxy.displayAll();
	}
}
