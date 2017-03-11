package esprit.skiworld.skiworld_Client;





import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Track;
import Service.TrackEJBRemote;

public class TrackClient {

	public static void main(String[] args) throws NamingException {
		InitialContext ctx = new InitialContext();
		TrackEJBRemote proxy = (TrackEJBRemote) ctx.lookup("/skiworld-ejb/TrackEJB!Service.TrackEJBRemote");
		Track t = new Track("hard",100,500);
		Track t2 ;
		//proxy.addTrack(t);
		//t.setDifficulty("hard");
		//proxy.addTrack(t);
		t2=proxy.findTrackById(1);
		//t2.setDifficulty("easy");
		//proxy.updateTrack(t2);
		
		
		List<Track> liste = proxy.findAll();
		for (Track track : liste) {
			System.out.println(track);
		}
		//t2=proxy.findTrackById(1);
		//System.out.println(t2);
		//t2=proxy.findTrackByDiff("easy");
		//System.out.println(t2);
		//proxy.deleteTrack(t2);
		//t2=proxy.findTrackById(2);
		//t2.setDifficulty("hard");
		//proxy.updateTrack(t2);
	}

}
