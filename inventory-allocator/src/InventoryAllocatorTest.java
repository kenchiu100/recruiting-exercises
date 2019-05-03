import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class InventoryAllocatorTest {

	@Test
	public void testfindCheapestShipment1() {
		//setting up inputs
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 1);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		List<InventoryDistribution> warehouses = new ArrayList<>();
		warehouses.add(warehouse1);
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",1);
		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Integer> orderAmount = new HashMap<>();
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		orderAmount.put("apple", 1);
		expectedShipments.put("owd", orderAmount);
		assertEquals(expectedShipments, shipments);
		
	}
	
	@Test
	public void testfindCheapestShipment2() {
		//setting up inputs
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 0);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		List<InventoryDistribution> warehouses = new ArrayList<>();
		warehouses.add(warehouse1);
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",1);
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		assertEquals(expectedShipments, shipments);
	}
	
	@Test
	public void testfindCheapestShipment3() {
		//setting up inputs
		List<InventoryDistribution> warehouses = new ArrayList<>();
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 5);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouses.add(warehouse1);
		Map<String, Integer> inventory2 = new HashMap<>();
		inventory2.put("apple", 5);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouses.add(warehouse2);		
		
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",10);
		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		Map<String, Integer> orderAmount1 = new HashMap<>();
		orderAmount1.put("apple", 5);
		expectedShipments.put("owd", orderAmount1);
		Map<String, Integer> orderAmount2 = new HashMap<>();
		orderAmount2.put("apple", 5);
		expectedShipments.put("dm", orderAmount2);
		assertEquals(expectedShipments, shipments);
	}
	
	@Test
	//prefer not splitting an item than getting items from cheaper warehouses
	public void testfindCheapestShipment4() {
		//setting up inputs
		List<InventoryDistribution> warehouses = new ArrayList<>();
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 4);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouses.add(warehouse1);
		Map<String, Integer> inventory2 = new HashMap<>();
		inventory2.put("apple", 4);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouses.add(warehouse2);		
		Map<String, Integer> inventory3 = new HashMap<>();
		inventory3.put("apple", 10);
		InventoryDistribution warehouse3 = new InventoryDistribution("ga", inventory3);
		warehouses.add(warehouse3);	
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",10);
		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		Map<String, Integer> orderAmount1 = new HashMap<>();
		orderAmount1.put("apple", 10);
		expectedShipments.put("ga", orderAmount1);

		assertEquals(expectedShipments, shipments);
	}
	
	@Test
	//prefer cheaper shipment when it doesn't need to split
	public void testfindCheapestShipment5() {
		//setting up inputs
		List<InventoryDistribution> warehouses = new ArrayList<>();
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 4);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouses.add(warehouse1);
		Map<String, Integer> inventory2 = new HashMap<>();
		inventory2.put("apple", 10);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouses.add(warehouse2);		
		Map<String, Integer> inventory3 = new HashMap<>();
		inventory3.put("apple", 10);
		InventoryDistribution warehouse3 = new InventoryDistribution("ga", inventory3);
		warehouses.add(warehouse3);	
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",10);
		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		Map<String, Integer> orderAmount1 = new HashMap<>();
		orderAmount1.put("apple", 10);
		expectedShipments.put("dm", orderAmount1);

		assertEquals(expectedShipments, shipments);
	}
	
	@Test
	//not getting more item than the order required
	public void testfindCheapestShipment6() {
		//setting up inputs
		List<InventoryDistribution> warehouses = new ArrayList<>();
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 4);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouses.add(warehouse1);
		Map<String, Integer> inventory3 = new HashMap<>();
		inventory3.put("apple", 14);
		InventoryDistribution warehouse3 = new InventoryDistribution("ga", inventory3);
		warehouses.add(warehouse3);	
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",10);
		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		Map<String, Integer> orderAmount1 = new HashMap<>();
		orderAmount1.put("apple", 10);
		expectedShipments.put("ga", orderAmount1);
		assertEquals(expectedShipments, shipments);
	}
	
	@Test
	//ship partially if not enough inventory across all warehouses
	public void testfindCheapestShipment7() {
		//setting up inputs
		List<InventoryDistribution> warehouses = new ArrayList<>();
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 4);
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouses.add(warehouse1);
		Map<String, Integer> inventory2 = new HashMap<>();
		inventory2.put("apple", 4);
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouses.add(warehouse2);		
		Map<String, Integer> inventory3 = new HashMap<>();
		inventory3.put("apple", 10);
		InventoryDistribution warehouse3 = new InventoryDistribution("ga", inventory3);
		warehouses.add(warehouse3);	
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",99);
		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		Map<String, Integer> orderAmount1 = new HashMap<>();
		orderAmount1.put("apple", 4);
		expectedShipments.put("owd", orderAmount1);
		Map<String, Integer> orderAmount2 = new HashMap<>();
		orderAmount2.put("apple", 4);
		expectedShipments.put("dm", orderAmount2);
		Map<String, Integer> orderAmount3 = new HashMap<>();
		orderAmount3.put("apple", 10);
		expectedShipments.put("ga", orderAmount3);

		assertEquals(expectedShipments, shipments);
	}
	
	@Test
	//some items have to split, while some shouldn't split
	public void testfindCheapestShipment8() {
		//setting up inputs
		List<InventoryDistribution> warehouses = new ArrayList<>();
		Map<String, Integer> inventory1 = new HashMap<>();
		inventory1.put("apple", 5);
		inventory1.put("orange", 5);
		inventory1.put("iphone", 0);
		inventory1.put("watermelon", 10);		
		InventoryDistribution warehouse1 = new InventoryDistribution("owd", inventory1);
		warehouses.add(warehouse1);
		Map<String, Integer> inventory2 = new HashMap<>();
		inventory2.put("apple", 5);
		inventory2.put("orange", 99);
		inventory2.put("iphone", 20);
		inventory2.put("watermelon", 8);		
		InventoryDistribution warehouse2 = new InventoryDistribution("dm", inventory2);
		warehouses.add(warehouse2);		
		Map<String, Integer> inventory3 = new HashMap<>();
		inventory3.put("apple", 15);
		inventory3.put("orange", 50);
		inventory3.put("iphone", 3);
		InventoryDistribution warehouse3 = new InventoryDistribution("ga", inventory3);
		warehouses.add(warehouse3);	
		Map<String, Integer> order = new HashMap<>();
		order.put("apple",20);
		order.put("orange",50);
		order.put("banana", 3);
		order.put("iphone", 35);
		order.put("watermelon", 18);

		
		Map<String, Map<String, Integer>> shipments = InventoryAllocator.findCheapestShipment(order, warehouses);
		
		//setting up expected output
		Map<String, Map<String, Integer>> expectedShipments = new HashMap<>();
		Map<String, Integer> orderAmount1 = new HashMap<>();
		orderAmount1.put("apple", 5);
		orderAmount1.put("watermelon", 10);
		expectedShipments.put("owd", orderAmount1);
		Map<String, Integer> orderAmount2 = new HashMap<>();
		orderAmount2.put("apple", 5);
		orderAmount2.put("orange", 50);
		orderAmount2.put("iphone", 20);
		orderAmount2.put("watermelon", 8);
		expectedShipments.put("dm", orderAmount2);
		Map<String, Integer> orderAmount3 = new HashMap<>();
		orderAmount3.put("apple", 10);
		orderAmount3.put("iphone", 3);
		expectedShipments.put("ga", orderAmount3);

		assertEquals(expectedShipments, shipments);
	}
	
	
}
