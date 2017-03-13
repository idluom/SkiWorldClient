package esprit.skiworld.skiworld_Client;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Training;
import Service.TrainingEJBRemote;

public class TrainingClient {

	public static void main(String[] args) throws NamingException {
		InitialContext ctx = new InitialContext();
		TrainingEJBRemote proxy = (TrainingEJBRemote) ctx                                     
				.lookup("/skiworld-ejb/TrainingEJB!Service.TrainingEJBRemote");
		Training t = new Training();
		//t.setLevel("okkk");
		SimpleDateFormat formater =null;
		formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String d1 = "2017-03-23 12:59:00";
		Date d = null;
		try {
			d=formater.parse(d1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(d);
		//t.setBegeningDate(d);
		//proxy.addTraining(t);
		//System.out.println(formater.format(now));
		//System.out.println(formater.format(now));
		//t.setBegeningDate(formater);
		//proxy.addTraining(t);
		//Training t2;
		//t2=proxy.findTrainingById(1);
		//System.out.println(t2);
		//t2.setNumber(10);
		//proxy.updateTraing(t2);
		List <Training> liste = proxy.findAllTrainingByLevel("Hard",d);
		for (Training training : liste) {
			System.out.println(training);		
		}
		
	}

}
