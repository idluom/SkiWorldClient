package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Product;
import Entity.Transport;
import Service.ProductEJBRemote;
import Service.TransportEJBRemote;

public class ProductBusiness {

	private InitialContext ctx = null;
	private final String jndiNameProduct = "/skiworld-ejb/ProductEJB!Service.ProductEJBRemote";
	private ProductEJBRemote proxy;

	public ProductBusiness() {

	}

	public boolean addProd(Product product) {
		try {
			ctx = new InitialContext();
			proxy = (ProductEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.addProduct(product);;
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteProd(Product product) {
		try {
			ctx = new InitialContext();
			proxy = (ProductEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.deleteProduct(product);;
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Product> getAllProd() {

		try {
			ctx = new InitialContext();
			proxy = (ProductEJBRemote) ctx.lookup(jndiNameProduct);
			return proxy.findAllProduct();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public boolean updateProd(Product product) {
		try {
			ctx = new InitialContext();
			proxy = (ProductEJBRemote) ctx.lookup(jndiNameProduct);
			proxy.updateProduct(product);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
