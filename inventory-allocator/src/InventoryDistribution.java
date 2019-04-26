import java.util.Map;

public class InventoryDistribution {
	private String name;
	private Map<String, Integer> inventory;
	public InventoryDistribution(String name, Map<String, Integer> inventory){
		this.name = name;
		this.inventory = inventory;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Map<String, Integer> getInventory(){
		return this.inventory;
	}
}
