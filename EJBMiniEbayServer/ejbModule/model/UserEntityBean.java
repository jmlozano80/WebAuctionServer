package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USER_AUCTION database table.
 * 
 */
@Entity
@Table(name="USER_AUCTION")
@NamedQueries ({
	@NamedQuery(name="AuctionEntityBean.findAll", query="SELECT a FROM AuctionEntityBean a"),																		
	@NamedQuery(name="AuctionEntityBean.findAuctionedCategories", query="Select DISTINCT i.category FROM ItemEntityBean i, AuctionEntityBean a WHERE a.itemId=i.itemId ")

})
public class UserEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EMAIL",unique=true, nullable=false, length=30)
	private String email;

	@Column(name="FIRST_NAME", nullable=false, length=20)
	private String firstName;

	@Column(name="LAST_NAME", nullable=false, length=20)
	private String lastName;

	@Column(name="PASSWORD",nullable=false, length=15)
	private String password;

	public UserEntityBean() {
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}