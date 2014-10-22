package user;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.ejb.Remote;



@Remote
public interface AuctionSessionBeanRemote {
	public ArrayList<String> getAllItemsOfUser(String email);
	public ArrayList<Map<String, String>> getAllAuctionsDetails();
	public ArrayList<Map<String, String>> getAllAuctionsDetailsOfCategory( String category);
	public void storeAuction(short auction_status,float start_price, Date start_date,Time start_time,Date end_date,Time end_time,int itemId);
	public Map<String,Object> getAuctionAttributes(int auctionId);
	public ArrayList<Map<String,String>> getAuctionHistory(String email);
}