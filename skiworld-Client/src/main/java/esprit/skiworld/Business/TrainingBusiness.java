package esprit.skiworld.Business;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import Entity.Training;
import Service.TrackEJBRemote;
import Service.TrainingEJBRemote;

public class TrainingBusiness {
	private InitialContext ctx = null;
	private final String jndiName = "/skiworld-ejb/TrainingEJB!Service.TrainingEJBRemote";
	private TrainingEJBRemote proxy;

	public void addTraining(Training t){
		try {
			ctx = new InitialContext();
			proxy = (TrainingEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.addTraining(t);
	}

	public void deleteTraining(Training t){
		try {
			ctx = new InitialContext();
			proxy = (TrainingEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.deleteTraining(t);
	}

	public void updateTraing(Training t){
		try {
			ctx = new InitialContext();
			proxy = (TrainingEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		proxy.updateTraing(t);
	}

	public Training findTrainingById(long id){
		try {
			ctx = new InitialContext();
			proxy = (TrainingEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findTrainingById(id);
	}

	public List<Training> findAllTraining(){
		try {
			ctx = new InitialContext();
			proxy = (TrainingEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findAllTraining();
	}

	public List<Training> findAllTrainingByLevel(String level, Date Bd){
		try {
			ctx = new InitialContext();
			proxy = (TrainingEJBRemote) ctx.lookup(jndiName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return proxy.findAllTrainingByLevel(level, Bd);
	}
}
