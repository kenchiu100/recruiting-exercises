import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryAllocator {
	// assume the list of warehouses is pre-sorted based on cost.
	//The first warehouse will be less expensive to ship from than the second warehouse.
	//This function will ship partially if there is not enough inventory
	public static List<Map<String, Map<String, Integer>>> findCheapestShipment(
			Map<String, Integer> order,
			List<InventoryDistribution> warehouses){
		List<Map<String, Map<String, Integer>>> shipments = new ArrayList<>();
		
		for(InventoryDistribution warehouse : warehouses){
			Map<String, Integer> inventory = warehouse.getInventory();
			String name = warehouse.getName();
			if(order.size() == 0) break;
			Map<String, Map<String, Integer>> warehouseOrder = new HashMap<>();
			
			for(String item : order.keySet()){
				int neededAmount = order.get(item);
				if(inventory.containsKey(item)){
					int invenAmount = inventory.get(item);
					if(invenAmount>=neededAmount){
						Map<String, Integer> orderAmount = new HashMap<>();
						orderAmount.put(item, neededAmount);
						warehouseOrder.put(name, orderAmount);
						order.remove(item); // update the order
					}else if(invenAmount >0){ // ship partially
						Map<String, Integer> orderAmount = new HashMap<>();
						orderAmount.put(item, invenAmount);	
						warehouseOrder.put(name, orderAmount);
						order.put(item, neededAmount - invenAmount); // update the order
					}
				}
			}
			if(warehouseOrder.size()>0){
				shipments.add(warehouseOrder);
			}
		}
		
		return shipments;
		
	}
}
