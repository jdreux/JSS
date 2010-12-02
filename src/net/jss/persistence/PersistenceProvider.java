package net.jss.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.JDOEnhancer;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.datanucleus.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.store.rdbms.SchemaTool;

public class PersistenceProvider {

	private static PersistenceManager pm;

	/*
	 * Enhances the classes and runs the schema tool.
	 */
	public static void createPersistenceManager() {
		
		Set<String> classes = new HashSet<String>();

		// TODO: package loader to enhance all model classes
		
		if(classes.size()>0){
			enhanceModels(classes);	
		}
		
		// Get the persistence manager.
		JDOPersistenceManagerFactory pmf = (JDOPersistenceManagerFactory)JDOHelper
				.getPersistenceManagerFactory("datanucleus.properties");

		// Now run Schema tool
		SchemaTool schematool = new SchemaTool();
		try {
			schematool.validateSchema(pmf, classes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Opening persistence manager.");

		pm = pmf.getPersistenceManager();
	}
	
	private static void enhanceModels(Set<String> classes){
		JDOEnhancer enhancer = JDOHelper.getEnhancer();
		enhancer.setClassLoader(PersistenceProvider.class.getClassLoader());
		enhancer.setVerbose(true);

		// Enhance all the models.
		for (String className : classes) {
			enhancer.addClasses(className);
		}

		enhancer.enhance();

	}

	public static PersistenceManager getPersistenceManager() {
		System.out.println("Returning a PM");
		return pm;
	}

	public static void closePersistenceManager() {
		if (pm != null) {
			System.out.println("Closing PM");
			pm.close();
		} else {
			System.out.println("Could not close PM");
		}
	}
	
	public static void main(String[] args){
		createPersistenceManager();
		closePersistenceManager();
	}
}
