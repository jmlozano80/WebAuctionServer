package user;

import java.math.BigDecimal;

import javax.ejb.Remote;

@Remote
public interface BidSessionBeanRemote {

	public BigDecimal getMaxBid(int auctionId);
	public boolean storeBid(int auctionId, String email ,BigDecimal newBid);
	
}
