package user;

import java.util.ArrayList;
import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface ItemSessionBeanRemote {

	boolean storeItem( String brand, String model, String category,String bike_year, String condition, String email);
	public ArrayList<String> getAuctionedCategories();
	public Map<String,Object> getItemAttributes(int itemId);
}
