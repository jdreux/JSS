package net.jss.js.adapters;

public class AuthenticationAdapter{
	
	
	public static AuthenticationAdapter getInstance(){
		return new AuthenticationAdapter();
	}
	
	public boolean createUser(String username, String password, String role){
		
		return true;
	}
	
	public boolean authenticate(String username, String password){
		return true;
	}
	
	public boolean logout(){
		return true;
	}
	
}
