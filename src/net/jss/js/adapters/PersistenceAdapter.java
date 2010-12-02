package net.jss.js.adapters;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

 import net.jss.persistence.PersistenceProvider;
 import net.jss.persistence.JSSEntity;


public class PersistenceAdapter{
	PersistenceManager pm;
	
	private static PersistenceAdapter instance;
	
	public static PersistenceAdapter getInstance(){
		if(instance == null){
			instance = new PersistenceAdapter();
		}
		return instance;
	}
	
	private PersistenceAdapter(){
		pm = PersistenceProvider.getPersistenceManager();
	}
	
	public void persist(JSSEntity o){
		Transaction tx = pm.currentTransaction();
	    tx.begin();
	    pm.makePersistent(o);
	    tx.commit();
	}
	
	public void persistAll(JSSEntity[] array){
		Transaction tx = pm.currentTransaction();
	    tx.begin();
	    pm.makePersistentAll((Object [])array);
	    tx.commit();
	}
	
	public void remove(JSSEntity o){
		Transaction tx = pm.currentTransaction();
	    tx.begin();
	    pm.deletePersistent(o);
	    tx.commit();
	}
	
	public void removeAll(JSSEntity[] array){
		Transaction tx = pm.currentTransaction();
	    tx.begin();
	    pm.deletePersistentAll((Object [])array);
	    tx.commit();
	}
	
	public JSSEntity get(JSSEntity o){
		return this.get(o.getClass(), o.getId());
	}
	
	public JSSEntity get(Class<?> c, long id){
		Query query = pm.newQuery(c, "id == " + id);
		query.setUnique(true);
		return (JSSEntity)query.execute();
	}
	
	public JSSEntity getUniqueByField(Class<?> c, String fieldName, String fieldValue){
		System.out.println("Running query : "+fieldName+" == '" +fieldValue+"'");
		Query query = pm.newQuery(c, fieldName+" == '" +fieldValue+"'");
		query.setUnique(true);
		return (JSSEntity) query.execute();
	}
	
	public JSSEntity[] getByField(Class<?> c, String fieldName, String fieldValue){
		System.out.println("Running query : "+fieldName+" == " +fieldValue);
		Query query = pm.newQuery(c, fieldName+" == " +fieldValue);
		Collection<JSSEntity> o = (Collection<JSSEntity>)query.execute();
		return (JSSEntity[])o.toArray(new JSSEntity[o.size()]);
	}
	
	public JSSEntity[] getByQueryString(Class<?>c, String queryString){
		Query query = pm.newQuery(c, queryString);
		Collection<JSSEntity> o = (Collection<JSSEntity>)query.execute();
		return (JSSEntity[])o.toArray(new JSSEntity[o.size()]);
	}
	
	public JSSEntity[] getAll(Class<?> c){
		
		Query q = pm.newQuery(c);
		
		List<JSSEntity> execute = (List<JSSEntity>) q.execute();
		if(execute==null){
			return null;
		}
		return execute.toArray(new JSSEntity[execute.size()]);
	}

}
