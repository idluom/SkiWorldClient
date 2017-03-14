package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Admin;
import Entity.Member;
import Entity.Trip;
import Service.AdminEJBRemote;
import Service.TripEJBRemote;

public class AdminBusiness {

	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ear/skiworld-ejb/AdminEJB!Service.AdminEJBRemote" ;
	private AdminEJBRemote proxy;
	
	public void addAdmin(Member membre){
		try {
			ctx = new InitialContext();
			proxy = (AdminEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.addAdmin(membre);
		
	}
	
	public void updateAdmin(Member membre) {
		try {
			ctx = new InitialContext();
			proxy = (AdminEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateAdmin(membre);
	}
	public void deleteAdmin(int a) {
		try {
			ctx = new InitialContext();
			proxy = (AdminEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.deleteAdmin(a);
		
	}

public void fetchUserName(String n) {
	try {
		ctx = new InitialContext();
		proxy = (AdminEJBRemote) ctx.lookup(jndiName);
	} catch (NamingException e) {
		e.printStackTrace();
	}
	proxy.fetchUsername(n);
	
}
public List<Member> DisplayAllMember(){
	try {
		ctx = new InitialContext();
		proxy = (AdminEJBRemote) ctx.lookup(jndiName);
	} catch (NamingException e) {
		e.printStackTrace();
	}
	
	return proxy.displayAll();
	
}


}

