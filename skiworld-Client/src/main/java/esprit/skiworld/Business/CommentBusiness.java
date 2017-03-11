package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Comment;
import Service.CommentEJBRemote;

public class CommentBusiness {
	private InitialContext ctx = null;
	private final String jndiNameComment = "/skiworld-ear/skiworld-ejb/CommentEJB!Service.CommentEJBRemote" ;
	private CommentEJBRemote proxy;
	
	public CommentBusiness() {
		
	}
	
	public List<Comment> getAllComments() {
		try {
			ctx = new InitialContext();
			proxy = (CommentEJBRemote) ctx.lookup(jndiNameComment);
			return proxy.findAllComments();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteComment(Comment comment) {
		try {
			ctx = new InitialContext();
			proxy = (CommentEJBRemote) ctx.lookup(jndiNameComment);
			proxy.deleteComment(comment);
			return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
}
