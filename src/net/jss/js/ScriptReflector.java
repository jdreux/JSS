package net.jss.js;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContext;

 import net.jss.controller.FrontController;


public class ScriptReflector {
	
	private static final String REFLECTION_SCRIPT_PATH = "WEB-INF/server-scripts/jss-reflection.js";
	
	ScriptEngine engine;
	
	public ScriptReflector(String script) throws ScriptException{
		this.engine = new ScriptEngineManager().getEngineByName("JavaScript");
		this.engine.eval(script);
	}
	
	public ScriptReflector(Reader fr) throws ScriptException{
		this.engine = new ScriptEngineManager().getEngineByName("JavaScript");
		this.engine.eval(fr);
	}
	
	public String[] getDefinedMethods() throws FileNotFoundException{
		 try {
			 ServletContext sc = FrontController.context.getServletContext();
			Reader reader = new FileReader(new File(sc.getRealPath(REFLECTION_SCRIPT_PATH)));
			return this.engine.eval(reader).toString().split(","); 
		} catch (ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}
}