package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.ItemEntityBean;
//import model.UserAuctionEntityBean;

/**
 * Session Bean implementation class ItemRegistration
 */
@Stateless
//@LocalBean
public class ItemSessionBean implements ItemSessionBeanRemote 
{
	@PersistenceContext (name = "EJBMiniEbayServer") private EntityManager emgr;
    
	boolean registered;
	
	/**
     * Default constructor. 
     */
    public ItemSessionBean() {
        // TODO Auto-generated constructor stub
    }
    
    
    /**
     * This method helps the user to store an item into the database
     */
    @Override
	public boolean storeItem(String brand, String model,String category, String bike_year, String condition,String email)
    {
    	registered = false;
		System.out.println("inside store Item");
		ItemEntityBean newItem = new ItemEntityBean();
		 		
		 	newItem.setBrand(brand);
		 	newItem.setModel(model);
		 	newItem.setBikeYear(bike_year);
		 	newItem.setCategory(category);
		 	newItem.setCondition(condition);
		 	newItem.setEmail(email); 
		 		 	 	 
		 	 emgr.persist(newItem);
		 	 
		 	 registered = true;
		 	
		 return registered;
	
    } // end method storeItem


    /**
     * This method helps to auto-populate the drop down categories of the auctions.jsp
     */
	@Override
	public ArrayList<String> getAuctionedCategories()
	{
		ArrayList<String> categories = new ArrayList<String>();
		System.out.println("Inside Method getAuctionedCategories in AuctionRegistration ");
		
		TypedQuery<ItemEntityBean> findActiveAuctionedCategories = emgr.createNamedQuery("Item.findAuctionedCategories", ItemEntityBean.class);
		
		List<ItemEntityBean> categoriesList = findActiveAuctionedCategories.getResultList();
		
		System.out.println("After List" + categoriesList );
		System.out.println("Category List size " + categoriesList.size() );
		
		if(categoriesList.size()>0)
		{
			for (int i = 0; i < categoriesList.size(); i++) 
			{
				String temporary=""+categoriesList.get(i);
				System.out.println("List Iem" + temporary);
				categories.add(temporary);
			}
		}
		
		System.out.println("categories Auctioned11111"+ categories);
		return categories;
		
	}//end method getAuctionedCategories()	
	
	
	/**
	 * This method give me the values of an item for a given itemId
	 */
	
	public Map<String,Object> getItemAttributes(int itemId)
	{
		Map<String, Object> itemMap = new HashMap<String, Object>();
		TypedQuery<ItemEntityBean> findItemQuery = emgr.createNamedQuery("Item.findItemValuesFromItemId", ItemEntityBean.class);
		findItemQuery.setParameter("itemId", itemId);
		//getting the values of a given itemId
		
		ItemEntityBean item = findItemQuery.getSingleResult();
		itemMap.put("bikeYear", item.getBikeYear());
		itemMap.put("brand", item.getBrand());
		itemMap.put("category", item.getCategory());
		itemMap.put("model",item.getModel());
		itemMap.put("condition",item.getCondition());
		
		return itemMap;
		
	}//end method getItemAttributes(int itemId)

    
}//end class
