import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryAllocator {
/*	Assume the list of warehouses is pre-sorted based on cost.
	The first warehouse will be less expensive to ship from than the second warehouse.
	This function will ship partially if there is not enough inventory 
*/
	public static Map<String, Map<String, Integer>> findCheapestShipment(
			Map<String, Integer> order,
			List<InventoryDistribution> warehouses){
		Map<String, Map<String, Integer>> warehouseOrders = new HashMap<>();
		//	warehouses-> items -> amount
		// get the items that can be shipped without splitting
		for(InventoryDistribution warehouse : warehouses){
			Map<String, Integer> inventory = warehouse.getInventory();
			String name = warehouse.getName();
			if(order.size() == 0) break;
			for(String item : order.keySet()){
				int neededAmount = order.get(item);
				if(neededAmount<=0) continue;
				if(inventory.containsKey(item)){
					int invenAmount = inventory.get(item);		
					if(invenAmount>=neededAmount){
						addOrderToWarehouseOrders(warehouseOrders, name, item, neededAmount);
						order.put(item, 0); // update the order
					}
				}
			}
		}
		
		//get items that needs to be split
		for(InventoryDistribution warehouse : warehouses){
			Map<String, Integer> inventory = warehouse.getInventory();
			String name = warehouse.getName();
			if(order.size() == 0) break;
			for(String item : order.keySet()){
				int neededAmount = order.get(item);
				if(neededAmount<=0) continue;
				if(inventory.containsKey(item)){
					int invenAmount = inventory.get(item);
					if(invenAmount>=neededAmount){
						addOrderToWarehouseOrders(warehouseOrders, name, item, neededAmount);
						order.put(item, 0); // update the order
					}
					else if(invenAmount >0){ // ship partially						
						addOrderToWarehouseOrders(warehouseOrders, name, item, invenAmount);					
						order.put(item, neededAmount - invenAmount); // update the order
					}
				}
			}
		}
		return warehouseOrders;		
	}
	
	private static void addOrderToWarehouseOrders(Map<String, Map<String, Integer>> warehouseOrders, 
			String warehouse, String item, int amount){
		if(warehouseOrders.containsKey(warehouse)){
			Map<String, Integer> inventoryMap = warehouseOrders.get(warehouse);
			inventoryMap.put(item, amount);
		}else{
			Map<String, Integer> orderAmount = new HashMap<>();
			orderAmount.put(item, amount);
			warehouseOrders.put(warehouse, orderAmount);							
		}
	}
}
