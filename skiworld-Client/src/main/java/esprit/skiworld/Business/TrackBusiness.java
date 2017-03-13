package esprit.skiworld.Business;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;
import Service.TrackEJBRemote;

public class TrackBusiness {
	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ejb/TrackEJB!Service.TrackEJBRemote";
	private TrackEJBRemote proxy;

	public void addTrack(Track t) {
		try {
			ctx = new InitialContext();
			proxy = (TrackEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.addTrack(t);
	}

	public void deleteTrack(Track t) {
		try {
			ctx = new InitialContext();
			proxy = (TrackEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.deleteTrack(t);
	}

	public void updateTrack(Track t) {
		try {
			ctx = new InitialContext();
			proxy = (TrackEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateTrack(t);
	}

	public Track findTrackById(Long idTrack) {
		try {
			ctx = new InitialContext();
			proxy = (TrackEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findTrackById(idTrack);
	}

	public List<Track> findAll() {
		try {
			ctx = new InitialContext();
			proxy = (TrackEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findAll();
	}

	public Track findTrackByDiff(String diff) {
		try {
			ctx = new InitialContext();
			proxy = (TrackEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findTrackByDiff(diff);
	}
}
