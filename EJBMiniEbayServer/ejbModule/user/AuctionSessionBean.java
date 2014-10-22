package user;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.AuctionEntityBean;
import model.BidEntityBean;
import model.ItemEntityBean;

/**
 * Session Bean implementation class AuctionRegistration
 */
@Stateless
public class AuctionSessionBean implements AuctionSessionBeanRemote
{
	@PersistenceContext (name = "EJBMiniEbayServer") private EntityManager emgr;
    
	/**
     * Default constructor. 
     */
    public AuctionSessionBean()
    {
        
    }
    
    /**
	 * Insert Auction into database
	 */
    @Override
    public void storeAuction(short auction_status,float start_price, Date start_date,Time start_time,Date end_date,Time end_time,int itemId)
	{
		AuctionEntityBean auction = new AuctionEntityBean();
		
		auction.setAuctionStatus(auction_status);
		auction.setStartPrice(BigDecimal.valueOf(start_price));
		auction.setStartDate(start_date);
		auction.setStartTime(start_time);
		auction.setEndDate(end_date);
		auction.setEndTime(end_time);
		auction.setItemId(itemId);
		
		emgr.persist(auction);
		
	}//end method storeAuction
	
	
    /**
     * This method help to auto-populate the drop down for item of an user that are not auctioned in the createAuction.jsp
     */
	@Override
	public ArrayList<String> getAllItemsOfUser(String email)
	{
		ArrayList<String> items = new ArrayList<String>();
		System.out.println("The email: " +email);
		TypedQuery<ItemEntityBean> itemsOfUserQuery = emgr.createNamedQuery("Item.findItemsOfUser", ItemEntityBean.class);
		itemsOfUserQuery.setParameter("email", email);
		List<ItemEntityBean> itemsOfUserList = itemsOfUserQuery.getResultList();
		System.out.println("The array of  items: " +itemsOfUserList);
		System.out.println("The size of: itemsOfUserList: " +itemsOfUserList.size());
		
		if(itemsOfUserList.size()>0)
		{
			for (int i = 0; i < itemsOfUserList.size(); i++) 
			{
				TypedQuery<AuctionEntityBean> itemsAuctioned = emgr.createNamedQuery("AuctionEntityBean.itemsAuctioned", AuctionEntityBean.class);
				int itemId=itemsOfUserList.get(i).getItemId();
				System.out.println("The itemId:= "+itemId );
				itemsAuctioned.setParameter("itemId", itemId);
				System.out.println("After set Parameter  itemId:= "+itemId );
				System.out.println("ItemsAuctioned result:= "+itemsAuctioned.getResultList());
				List<AuctionEntityBean> itemAuctionned =  itemsAuctioned.getResultList();
				
				if(itemAuctionned.size()>0 && itemAuctionned.get(0).getAuctionStatus()!=0 && itemAuctionned.get(0).getAuctionStatus()!=2)
				{
					items.add(itemsOfUserList.get(i).getItemId()+"/"+itemsOfUserList.get(i).getBrand());
				}
				if(itemAuctionned.size()==0)
				{
					items.add(itemsOfUserList.get(i).getItemId()+"/"+itemsOfUserList.get(i).getBrand());
				}
				
			}//end for loop
			
		}//end condition list>0
		
		System.out.println("The array of available items: " +items);
		return items;
		
	}//end method getAllItemsOfUser(String email)
	
	
	
	/**
     * This method help to display active auctions
     */
	
	@Override
	public ArrayList<Map<String, String>> getAllAuctionsDetails()
	{
		ArrayList<Map<String, String>> allAuctions = new ArrayList<Map<String,String>>();
		TypedQuery<AuctionEntityBean> allAuctionActiveQuery = emgr.createNamedQuery("AuctionEntityBean.findAllAuctionActive", AuctionEntityBean.class);
		
		List<AuctionEntityBean> auctionList = allAuctionActiveQuery.getResultList();
		System.out.println( "The reults of the query to display all auctions: "+auctionList);
		for (int i = 0; i < auctionList.size(); i++) 
		{
			Map<String, String> auctionMap = new TreeMap<String, String>();
			//Fetch all the rows needed from auction			
					
			 final Time endTime= auctionList.get(i).getEndTime();
			 final Date endDate=  auctionList.get(i).getEndDate();
			 
				String time = auctionList.get(i).getEndTime().toString();
				String stringHours=time.substring(0, 2);
				String stringMinutes=time.substring(3, 5);
				String stringSeconds=time.substring(6, 8);
				
				int hours=Integer.valueOf(stringHours);
				int minutes=Integer.valueOf(stringMinutes);
				int seconds=Integer.valueOf(stringSeconds);
				System.out.println("The date without the added time"+auctionList.get(i).getEndDate() );
			 
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(endDate);
				System.out.println("The date after endCalendar.setTime() "+  endCalendar.getTime());
				endCalendar.set(Calendar.HOUR_OF_DAY, hours);
				endCalendar.set(Calendar.MINUTE, minutes);
				endCalendar.set(Calendar.SECOND, seconds);
				
				System.out.println("The Date with the added time "+ endCalendar.getTime());
				Calendar calendar = Calendar.getInstance();
				calendar.getTime();
				System.out.println("The current time: "+ calendar.getTime());
				
				long different = endCalendar.getTimeInMillis() - calendar.getTimeInMillis();
				
				 long secondsInMilli = 1000;
					long minutesInMilli = secondsInMilli * 60;
					long hoursInMilli = minutesInMilli * 60;
					long daysInMilli = hoursInMilli * 24;
			 
					long elapsedDays = different / daysInMilli;
					different = different % daysInMilli;
			 
					long elapsedHours = different / hoursInMilli;
					different = different % hoursInMilli;
			 
					long elapsedMinutes = different / minutesInMilli;
					different = different % minutesInMilli;
			 
					long elapsedSeconds = different / secondsInMilli;
				
					
					System.out.println("THE ELAPSE TIME "+elapsedDays+"Days " +elapsedHours +" hours " + elapsedMinutes+" minutes "+ elapsedSeconds +" seconds" );
					 		
					endCalendar.clear();
			
					System.out.println("The endCalendar after endCalendar.clear() "+ endCalendar.getTime());
					endCalendar.setTime(endCalendar.getTime());		
					System.out.println("The endCalendar after setTime(endCalendar.clear() ) "+ endCalendar.getTime());		
					
					//still available auction relative to time
					if(different>0)
					{
						// add itemId
						int auctionId = auctionList.get(i).getAuctionId();
						auctionMap.put("auctionId", Integer.toString(auctionId));
			 
						//add startPrice
						BigDecimal startPrice=  auctionList.get(i).getStartPrice();
						auctionMap.put("startPrice", startPrice.toString()); 
			 
						//Storing the days elapsed
						String daysTo=String.valueOf(elapsedDays);
						auctionMap.put("daysTo", daysTo);
			
						//Storing the days elapsed
						String hoursTo=String.valueOf(elapsedHours);
						auctionMap.put("hoursTo", hoursTo);
			 
						//Storing the days elapsed
						String minutesTo=String.valueOf(elapsedMinutes);
						auctionMap.put("minutesTo", minutesTo);
						//Storing the days elapsed
						String secondsTo=String.valueOf(elapsedSeconds);
						auctionMap.put("secondsTo", secondsTo);
			 
						ItemEntityBean item = emgr.find(ItemEntityBean.class, auctionList.get(i).getItemId());
						//Fetch all the rows needed from item
			
						//add itemCategory
						String category= item.getCategory();
						auctionMap.put("category", category);
			
						//add brand
						String brand= item.getBrand();
						auctionMap.put("brand", brand);
			
						//add bike year
						String bikeYear=item.getBikeYear().toString();
						auctionMap.put("bikeYear", bikeYear);
			
						//add model
						String model=item.getModel();
						auctionMap.put("model", model);
								
						//This status (0) of the auction indicate an ACTIVE AUCTION
						auctionList.get(i).setAuctionStatus((short) 0);
			 
						allAuctions.add(auctionMap);
			
					}
					//Expired Auction
					if(different<=0)
					{
						auctionList.get(i).setAuctionStatus((short) 1);
					}
			
		}//end loop
		
		System.out.println("All Auctions "+ allAuctions);
		return allAuctions;
		
	}//end method getAllAuctionsDetails()
	
	
	
	/**
	 * This method get all the auction for a given item category
	 */
	
	@Override
	public ArrayList<Map<String, String>> getAllAuctionsDetailsOfCategory( String byCategory)
	{
		ArrayList<Map<String, String>> allAuctions = new ArrayList<Map<String,String>>();
		TypedQuery<AuctionEntityBean> allAuctionActiveQuery = emgr.createNamedQuery("AuctionEntityBean.findAllAuctionActive", AuctionEntityBean.class);
		
		List<AuctionEntityBean> auctionList = allAuctionActiveQuery.getResultList();
		System.out.println( "The reults of the query to display all auctions: "+auctionList);
		for (int i = 0; i < auctionList.size(); i++) 
		{
			Map<String, String> auctionMap = new TreeMap<String, String>();
			//Fetch all the rows needed from auction
			
			 final Time endTime= auctionList.get(i).getEndTime();
			 final Date endDate=  auctionList.get(i).getEndDate();
			 		 
				String time = auctionList.get(i).getEndTime().toString();
				System.out.println("This is the column time NOT DATE: " +time);
				String stringHours=time.substring(0, 2);
				String stringMinutes=time.substring(3, 5);
				String stringSeconds=time.substring(6, 8);
				
				int hours=Integer.valueOf(stringHours);
				int minutes=Integer.valueOf(stringMinutes);
				int seconds=Integer.valueOf(stringSeconds);
				System.out.println("The date without the added time"+auctionList.get(i).getEndDate() );
							 
				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(endDate);
				System.out.println("The date after endCalendar.setTime() "+  endCalendar.getTime());
				endCalendar.set(Calendar.HOUR_OF_DAY, hours);
				endCalendar.set(Calendar.MINUTE, minutes);
				endCalendar.set(Calendar.SECOND, seconds);
				
				System.out.println("The Date with the added time "+ endCalendar.getTime());
				Calendar calendar = Calendar.getInstance();
				calendar.getTime();
				
				System.out.println("The current time: "+ calendar.getTime());
				
				long different = endCalendar.getTimeInMillis() - calendar.getTimeInMillis();
				
				 long secondsInMilli = 1000;
					long minutesInMilli = secondsInMilli * 60;
					long hoursInMilli = minutesInMilli * 60;
					long daysInMilli = hoursInMilli * 24;
			 
					long elapsedDays = different / daysInMilli;
					different = different % daysInMilli;
			 
					long elapsedHours = different / hoursInMilli;
					different = different % hoursInMilli;
			 
					long elapsedMinutes = different / minutesInMilli;
					different = different % minutesInMilli;
			 
					long elapsedSeconds = different / secondsInMilli;
				
					
					System.out.println("THE ELAPSE TIME "+elapsedDays+"Days " +elapsedHours +" hours " + elapsedMinutes+" minutes "+ elapsedSeconds +" seconds" );
					 		
					endCalendar.clear();
			
					System.out.println("The endCalendar after endCalendar.clear() "+ endCalendar.getTime());
					endCalendar.setTime(endCalendar.getTime());		
					System.out.println("The endCalendar after setTime(endCalendar.clear() ) "+ endCalendar.getTime());		
					
					
					ItemEntityBean item = emgr.find(ItemEntityBean.class, auctionList.get(i).getItemId());
			
					//still available auction relative to time
					if(different>0 && item.getCategory().equals(byCategory) )
					{			
			
						System.out.println("Inside if where category equal to " +item.getCategory());
			
						 // add itemId
						 int auctionId = auctionList.get(i).getAuctionId();
						 auctionMap.put("auctionId", Integer.toString(auctionId));
						 
						 //add startPrice
						 BigDecimal startPrice=  auctionList.get(i).getStartPrice();
						 auctionMap.put("startPrice", startPrice.toString()); 
						 
						//Storing the days elapsed
						 String daysTo=String.valueOf(elapsedDays);
						 auctionMap.put("daysTo", daysTo);
						
						//Storing the days elapsed
						 String hoursTo=String.valueOf(elapsedHours);
						 auctionMap.put("hoursTo", hoursTo);
						 
						//Storing the days elapsed
						 String minutesTo=String.valueOf(elapsedMinutes);
						 auctionMap.put("minutesTo", minutesTo);
						//Storing the days elapsed
						 String secondsTo=String.valueOf(elapsedSeconds);
						 auctionMap.put("secondsTo", secondsTo);
			 
						 //Fetch all the rows needed from item
			
						 //add itemCategory
						 String category= item.getCategory();
						 auctionMap.put("category", category);
			
						 //add brand
						 String brand= item.getBrand();
						 auctionMap.put("brand", brand);
			
						 //add bike year
						 String bikeYear=item.getBikeYear().toString();
						 auctionMap.put("bikeYear", bikeYear);
			
						 //add model
						 String model=item.getModel();
						 auctionMap.put("model", model);
			
						 //This status (0) of the auction indicate an ACTIVE AUCTION
						 auctionList.get(i).setAuctionStatus((short) 0);
			 
						 allAuctions.add(auctionMap);
			
					}//end condition auction On-going
					
					//Expired Auction
					if(different<=0)
					{
						auctionList.get(i).setAuctionStatus((short) 1);
					}
			
		}//end loop
		
		System.out.println("All Auctions "+ allAuctions);
		return allAuctions;
	
	}//end method getAllAuctionsDetailsOfCategory( String byCategory)

	
	/**
	 * This method get the itemId from the auctionId and the other needed values from the table Auction
	 */

	public  Map<String,Object> getAuctionAttributes(int auctionId)
	{
		Map<String, Object> auctionMap = new HashMap<String, Object>();
		TypedQuery<AuctionEntityBean> itemIdQuery = emgr.createNamedQuery("AuctionEntityBean.findItemId", AuctionEntityBean.class);
				
		itemIdQuery.setParameter("auctionId", auctionId);
		
		AuctionEntityBean aeb = itemIdQuery.getSingleResult();
		
		auctionMap.put("itemId", aeb.getItemId());
		auctionMap.put("startPrice", aeb.getStartPrice());
		auctionMap.put("endDate",aeb.getEndDate());
		auctionMap.put("endTime", aeb.getEndTime());
		
		int item=aeb.getItemId();
		
		System.out.println("The itemId="+item);
		return auctionMap;
	
	}//end method getAuctionAttributes(int auctionId)





	/**
	 * MThis method shows the auctions from a given user
	 */
	@Override
	public ArrayList<Map<String,String>> getAuctionHistory(String email)
	{

		BidEntityBean bid = new BidEntityBean();
		bid.getAuctionId(); // QUERY THE AUCTION
				
		TypedQuery<BidEntityBean> myHistoryQuery = emgr.createNamedQuery("Bid.myHistory", BidEntityBean.class);
		
		myHistoryQuery.setParameter("email", email);
		
		List<BidEntityBean> myList = myHistoryQuery.getResultList();
		
		System.out.println("myList " + myList);
		
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for (int i = 0; i < myList.size(); i++)
		{
			Map<String,String> temporaryMap = new HashMap<String,String>();
			String auctionId = Integer.toString(myList.get(i).getAuctionId());
			temporaryMap.put("auctionId", auctionId);
			temporaryMap.put("bidPrice", myList.get(i).getBidPrice().toString());
			
			AuctionEntityBean auction = emgr.find(AuctionEntityBean.class, Integer.parseInt(auctionId));
			
			String status=Short.toString(auction.getAuctionStatus());
			if(status.equals("0"))
			{
				temporaryMap.put("auctionStatus", "Ongoing");
			}
			if (status.equals("1"))
			{
				temporaryMap.put("auctionStatus", "Finished");
			}
			ItemEntityBean item = emgr.find(ItemEntityBean.class, auction.getItemId());
			
			temporaryMap.put("itemBrand", item.getBrand());
			
			System.out.println("The email of winner : " +myList.get(i).getEmail().toString());
			temporaryMap.put("emailOfWinner", myList.get(i).getEmail().toString());
;			
			list.add(temporaryMap);
			
		}//end for loop
		
		return list;
		
	}//end method getAuctionHistory(String email)

	
}//end class
