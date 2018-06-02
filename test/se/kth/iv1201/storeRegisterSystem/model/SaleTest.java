package se.kth.iv1201.storeRegisterSystem.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.InventoryRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.RegistryCreator;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaleTest {
    private InventoryRegistry inventoryRegistry;
    private DiscountRulesRegistry discountRulesRegistry;
    private Sale sale;

    @Before
    public void setUp() throws Exception {
        RegistryCreator regCreator = new RegistryCreator();
        inventoryRegistry = regCreator.getInventoryRegistry();
        discountRulesRegistry = regCreator.getDiscountRulesRegistry();
        sale = new Sale();
    }

    @After
    public void tearDown() throws Exception {
        inventoryRegistry = null;
        discountRulesRegistry = null;
        sale = null;
    }

    @Test
    public void testAddItem() throws Exception {
        ItemDTO testItem = inventoryRegistry.getItem(1);
        sale.addItem(testItem, 1);
        Map<ItemDTO, Integer> items = sale.getItemList();
        assertTrue("Item has not been added", items.containsKey(testItem));
        assertEquals("Item does not have the correct quantity", 1, (int) items.get(testItem));
        int size = items.size();
        assertEquals("There are more than one item in the basket", 1, size);
    }

    @Test
    public void testRequestDiscount() throws Exception {
        ItemDTO testItem = inventoryRegistry.getItem(1);
        sale.addItem(testItem, 1);
        sale.requestDiscount(new CustomerDTO("", 1234), discountRulesRegistry);

        double expectedAmountToPay = 11.992;
        double amountToPay = sale.getRunningTotal();
        assertEquals("Discount has not been applied correctly", expectedAmountToPay, amountToPay, 0.001);
    }
}
