package model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


/**
 * The persistent class for the AUCTION database table.
 * 
 */
@Entity
@Table(name="AUCTION")

@NamedQueries ({
	@NamedQuery(name="AuctionEntityBean.findAll", query="SELECT a FROM AuctionEntityBean a"),
	@NamedQuery(name="AuctionEntityBean.findAllByAuctionid", query="SELECT a FROM AuctionEntityBean a WHERE a.auctionId=:auctionId"),
	@NamedQuery(name="AuctionEntityBean.itemsAuctioned", query="SELECT a FROM AuctionEntityBean a WHERE a.itemId = :itemId AND a.auctionStatus=0"),
	@NamedQuery(name="AuctionEntityBean.findAllAuctionActive", query="SELECT a FROM AuctionEntityBean a WHERE a.auctionStatus=0 ORDER BY a.endDate, a.endTime"),
	@NamedQuery(name="AuctionEntityBean.displayAllAuctions", query="SELECT a.auctionId, i.brand,i.model,i.bikeYear,i.category,a.startPrice,a.endDate, a.endTime from AuctionEntityBean a, ItemEntityBean i where a.itemId=i.itemId"),
	@NamedQuery(name="AuctionEntityBean.getAllAuctionsDetailsOfACategory", query="SELECT a.auctionId, i.brand,i.model,i.bikeYear,i.category,a.startPrice,a.endDate, a.endTime from AuctionEntityBean a, ItemEntityBean i WHERE a.itemId=i.itemId AND i.category = :category"),
	@NamedQuery(name="AuctionEntityBean.findItemId", query="SELECT a FROM AuctionEntityBean a WHERE a.auctionId =:auctionId"),
	

})	
public class AuctionEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AUCTION_ID", unique=true, nullable=false)
	private int auctionId;

	@Column(name="AUCTION_STATUS")
	private short auctionStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="END_DATE", nullable=false)
	private Date endDate;

	@Column(name="END_TIME", nullable=false)
	private Time endTime;

	@Column(name="ITEM_ID", nullable=false)
	private int itemId;

	@Temporal(TemporalType.DATE)
	@Column(name="START_DATE", nullable=false)
	private Date startDate;

	@Column(name="START_PRICE", precision=7, scale=2)
	private BigDecimal startPrice;

	@Column(name="START_TIME", nullable=false)
	private Time startTime;

	public AuctionEntityBean() {
	}

	public int getAuctionId() {
		return this.auctionId;
	}

	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}

	public short getAuctionStatus() {
		return this.auctionStatus;
	}

	public void setAuctionStatus(short auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getStartPrice() {
		return this.startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
		this.startPrice = startPrice;
	}

	public Time getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

}