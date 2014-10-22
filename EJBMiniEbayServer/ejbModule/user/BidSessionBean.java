package user;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.AuctionEntityBean;
import model.BidEntityBean;

/**
 * Session Bean implementation class BidSessionBean
 */
@Stateless
public class BidSessionBean implements BidSessionBeanRemote
{
	@PersistenceContext (name = "EJBMiniEbayServer") private EntityManager emgr;

    /**
     * Default constructor. 
     */
    public BidSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * This method help me to store a bid into the database 
     */
       @Override
      public boolean storeBid(int auctionId, String email ,BigDecimal newBid)
      {
    	   boolean bidAccepted = false;
        	
        	AuctionEntityBean auction = emgr.find(AuctionEntityBean.class, auctionId);
        	
        	Date endDate = auction.getEndDate();
        	Time endTime = auction.getEndTime();
        	
        	Calendar endCalendar = Calendar.getInstance();
        	endCalendar.setTime(endDate);
        	
        	String[] timeSplitted = endTime.toString().split(":");
        	
        	endCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSplitted[0]));
        	endCalendar.set(Calendar.MINUTE, Integer.parseInt(timeSplitted[1]));
        	endCalendar.set(Calendar.SECOND, Integer.parseInt(timeSplitted[2]));
        	
        	Calendar currentCalendar = Calendar.getInstance();
        	
        	// Check if the auction expired
        	if (currentCalendar.after(endCalendar))
        	{
        		// Expire the auction
        		
        		//  STATUS 1 == expired & won by somebody
        		TypedQuery<BidEntityBean> bidQueryMax = emgr.createNamedQuery("Bid.maxBidForAuction", BidEntityBean.class);
        		try 
        		{
        			BidEntityBean bid = bidQueryMax.getSingleResult();
        			auction.setAuctionStatus((short)1);
        			emgr.merge(auction);
        		} 
        		catch (Exception e)
        		{
        			//  STATUS 2 == expired & nobody won
        			auction.setAuctionStatus((short)2);
        			emgr.merge(auction);
        			
        		}
        	}//end condition expired
        	else
        	{
        		 //store a bid
      		 	 BidEntityBean bid = new BidEntityBean();
      		 
      		 	
      		 	 bid.setAuctionId(auctionId);
      		 	 bid.setEmail(email);
      		 	 bid.setBidPrice(newBid);
      		 	
      		 	 java.util.Date date= new java.util.Date();
      		 	 Timestamp bidTime =new Timestamp(date.getTime());
      		 	 bid.setBidTime(bidTime);
      		 		 	
      		 	emgr.persist(bid);
      		 	emgr.flush();
      		 	bidAccepted = true;
        	}
        	
      		return bidAccepted;
      
      }//end method    
    
    
    
    
    /**
     * This method helps to get the highest current bid for a given auctionId
     */
    
    public BigDecimal getMaxBid(int auctionId)
    {
    	BigDecimal maxBid= new BigDecimal(0);
    	TypedQuery<BidEntityBean> maxBidQuery = emgr.createNamedQuery("Bid.maxBidForAuction", BidEntityBean.class);
		
    	System.out.println("The auctionId from bidSessionBean is : "+auctionId);
    	maxBidQuery.setParameter("auctionId", auctionId);
		
    	System.out.println("Just after maxBidQuery.setParameter(auctionId");
    	try
    	{
    		BidEntityBean bid =maxBidQuery.getSingleResult();
    			
    		maxBid= bid.getBidPrice();
    		System.out.println("The max bid from bidSessionBean is : "+maxBid);
    	    	
		
    	}
    	catch (Exception e) 
    	{ 
    		System.out.println("BLOODY EXCEPTION " + e.getMessage());
    		e.printStackTrace();
    		
    	}	
       	
    	return maxBid;
    	
    }//end method getMaxBid(int auctionId)
  
    
    
}//end class
