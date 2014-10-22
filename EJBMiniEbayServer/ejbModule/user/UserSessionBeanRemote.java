package user;

import javax.ejb.Remote;

@Remote
public interface UserSessionBeanRemote {
	//void connect();
	//void openConnection();
	void storeUser(String email,String lastName,String firstName, String password);
	boolean logingUser(String email, String password);
	boolean emailExist(String email);
	String getFirstName(String email);
	String getLastName(String email);
	
}
