package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Report;
import Service.ReportEJBRemote;

public class ReportBusiness {
	private InitialContext ctx = null;
	private final String jndiNameReport = "/skiworld-ear/skiworld-ejb/ReportEJB!Service.ReportEJBRemote" ;
	private ReportEJBRemote proxy;
	
	public ReportBusiness() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Report> getAllReports() {
		try {
			ctx = new InitialContext();
			proxy = (ReportEJBRemote) ctx.lookup(jndiNameReport);
			return proxy.findAllReports();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
