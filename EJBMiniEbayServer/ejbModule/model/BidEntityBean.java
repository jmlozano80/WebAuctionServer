package model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the BID database table.
 * 
 */
@Entity
@Table(name="BID")
@NamedQueries ({
	@NamedQuery(name="Bid.findAll", query="SELECT b FROM BidEntityBean b"),
	@NamedQuery(name="Bid.maxBidForAuction", query=" SELECT b from BidEntityBean b where b.auctionId =:auctionId AND b.bidPrice=(Select Max(b.bidPrice) from BidEntityBean b where b.auctionId =:auctionId)"),
	@NamedQuery(name="Bid.myHistory", query="SELECT b from AuctionEntityBean a, BidEntityBean b, ItemEntityBean i WHERE a.auctionId=b.auctionId AND i.email=:email AND i.itemId=a.itemId AND b.bidPrice=(Select Max(b.bidPrice) from BidEntityBean b where b.auctionId =a.auctionId)")

	

})	

public class BidEntityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BID_ID", unique=true, nullable=false)
	private int bidId;

	@Column(name="AUCTION_ID", nullable=false)
	private int auctionId;

	@Column(name="BID_PRICE", precision=7, scale=2)
	private BigDecimal bidPrice;

	@Column(name="BID_TIME", nullable=false)
	private Timestamp bidTime;

	@Column(name="email",nullable=false, length=30)
	private String email;

	public BidEntityBean() {
	}

	public int getBidId() {
		return this.bidId;
	}

	public void setBidId(int bidId) {
		this.bidId = bidId;
	}

	public int getAuctionId() {
		return this.auctionId;
	}

	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}

	public BigDecimal getBidPrice() {
		return this.bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Timestamp getBidTime() {
		return this.bidTime;
	}

	public void setBidTime(Timestamp bidTime) {
		this.bidTime = bidTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}