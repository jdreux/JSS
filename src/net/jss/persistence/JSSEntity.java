package net.jss.persistence;

import com.google.gson.Gson;

public abstract class JSSEntity {

	public abstract long getId();
	
	public String getJSONString(){
		Gson gson = new Gson();
		String json = gson.toJson(this);
		System.out.println("Returning json string "+json+" for object "+this);
		return json;
	}
	
	public boolean isJSSEntity(){
		return true;
	}

}
