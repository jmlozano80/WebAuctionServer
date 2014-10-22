package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;





import model.UserEntityBean;

/**
 * Session Bean implementation class UserRegistration
 */


 @Stateless
public class UserSessionBean implements UserSessionBeanRemote 
{
	@PersistenceContext (name = "EJBMiniEbayServer") private EntityManager emgr;
	

	String lastName;
	String firstName;
	String email;
	String password;
	boolean registered;
	//UserAuctionEntityBean newUser ;
    /**
     * Default constructor. 
     */
    public UserSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
  
/**
 * This method stores new users into the database
 */
	@Override
	public void storeUser(String email,String firstName,String lastName, String password) 
	{
		
		 	//boolean registered = false;
		 	
		 	 System.out.println("Open Connection from storeUser");
		 	
		 	
		 	 System.out.println("inside store user");
		 	UserEntityBean newUser = new UserEntityBean();
		 	newUser.setEmail(email);  
		 	newUser.setFirstName(firstName);
		 	newUser.setLastName(lastName); 
		 	newUser.setPassword(password);
		 			 	 
		 	 emgr.persist(newUser);
	} // End of registerUser

	/**
	 * This method is used to loin the user by checking the user's credentials.
	 * 
	 */

	@Override
	public boolean logingUser(String email, String password)
	{
		// TODO Auto-generated method stub
		boolean login = false;
		System.out.println("inside loginUser  login = "+login);
		UserEntityBean user = emgr.find(UserEntityBean.class, email);
		System.out.println("after user " +user);
		
		if(user!=null && user.getPassword().equals(password))
		{	System.out.println("user inside!=null user=" +user);
			login = true;
			
		} 
		System.out.println("boolean inside loginUserEJB= " +login);
		return login;
		
	}//end methodloginUser

	
	/**
	 * This method checks is there is already a user with the same email registered
	 */
	@Override
	public boolean emailExist(String email)
	{
		boolean exists=false;
		
		UserEntityBean user = emgr.find(UserEntityBean.class, email);
		System.out.println("Inside emailExist");
		if(user!=null)
		{	System.out.println("user Exist");
			return exists=true;
		}
		else
		System.out.println("user noExist");
		// TODO Auto-generated method stub
		return exists;
		
	}//end method emailExist

	/**
	 * This method retrieve the user first name
	 */
	@Override
	public String getFirstName(String email)
	{
		System.out.println("inside UserRegitration geFirstName");
		UserEntityBean user = emgr.find(UserEntityBean.class, email);
		String fName=user.getFirstName();
		// TODO Auto-generated method stub
		System.out.println("inside UserRegitration getFirstName, first name = " +fName);
		return fName;
		
	}//end method

	
	/**
	 * This method retrieve the user first name
	 */
	@Override
	public String getLastName(String email) {
		System.out.println("inside UserRegitration getLastName");
		UserEntityBean user = emgr.find(UserEntityBean.class, email);
		String lName=user.getLastName();
		// TODO Auto-generated method stub
		System.out.println("inside UserRegitration getLastName, last name = " +lName);
		return lName;
	}//end method
	
	
}//end class
