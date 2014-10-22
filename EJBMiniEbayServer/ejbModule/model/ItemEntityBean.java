package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ITEM database table.
 * 
 */
@Entity
@Table(name="ITEM")
@NamedQueries ({
	@NamedQuery(name="Item.findAll", query="SELECT i FROM ItemEntityBean i"),
	@NamedQuery(name="Item.findItemsOfUser", query="SELECT i FROM ItemEntityBean i WHERE i.email = :email"),
	@NamedQuery(name="Item.findItemValuesFromItemId", query="SELECT i FROM ItemEntityBean i WHERE i.itemId = :itemId"),
	@NamedQuery(name="Item.findItemsAuctioned", query="SELECT i FROM ItemEntityBean i WHERE i.itemId = :itemId"),
	@NamedQuery(name="Item.findAuctionedCategories", query="Select DISTINCT i.category FROM ItemEntityBean i, AuctionEntityBean a WHERE a.itemId=i.itemId AND a.auctionStatus=0 ")

})
public class ItemEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ITEM_ID", unique=true, nullable=false)
	private int itemId;

	@Column(name="BIKE_YEAR", nullable=false, length=4)
	private String bikeYear;

	@Column(name="BRAND",nullable=false, length=20)
	private String brand;

	@Column(name="CATEGORY",nullable=false, length=20)
	private String category;

	@Column(name="CONDITION",nullable=false, length=20)
	private String condition;

	@Column(name="EMAIL",nullable=false, length=30)
	private String email;

	@Column(nullable=false, length=20)
	private String model;

	public ItemEntityBean() {
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getBikeYear() {
		return this.bikeYear;
	}

	public void setBikeYear(String bikeYear) {
		this.bikeYear = bikeYear;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}