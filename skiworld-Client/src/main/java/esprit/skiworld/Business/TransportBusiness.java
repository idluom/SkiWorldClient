package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Transport;
import Service.TransportEJBRemote;

public class TransportBusiness {

	private InitialContext ctx = null;
	private final String jndiNameTransport = "/skiworld-ear/skiworld-ejb" + "/TransportEJB!Service.TransportEJBRemote";
	private TransportEJBRemote proxy;

	public TransportBusiness() {

	}

	public boolean addMeanTransport(Transport transport) {
		try {
			ctx = new InitialContext();
			proxy = (TransportEJBRemote) ctx.lookup(jndiNameTransport);
			proxy.addMeanTransport(transport);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteMeanTransport(Transport transport) {
		try {
			ctx = new InitialContext();
			proxy = (TransportEJBRemote) ctx.lookup(jndiNameTransport);
			proxy.deleteMeanTransport(transport);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Transport> getAllMeanTransport() {

		try {
			ctx = new InitialContext();
			proxy = (TransportEJBRemote) ctx.lookup(jndiNameTransport);
			return proxy.displayMeanTransport();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateTransport(Transport transport) {
		try {
			ctx = new InitialContext();
			proxy = (TransportEJBRemote) ctx.lookup(jndiNameTransport);
			proxy.updateMeanTransport(transport);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
