package net.jss.js.adapters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

 import net.jss.js.ScriptCore;

public class JavascriptSupportAdapter{
	
	public static JavascriptSupportAdapter getInstance(){
		return new JavascriptSupportAdapter();
	}
	
	public void importServerScript(String scriptName) throws FileNotFoundException{
		ScriptCore sc = ScriptCore.getInstance();
		sc.executeScript(new FileReader(new File(ScriptCore.SERVER_SCRIPTS_PATH+scriptName)));
	}
	
	
	
	
}
