package se.kth.iv1201.storeRegisterSystem.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;
import se.kth.iv1201.storeRegisterSystem.model.Sale;

public class TestInventoryRegistry {
    private InventoryRegistry inventoryRegistry;
    private Sale sale;

    @Before
    public void setUp() throws Exception {
        RegistryCreator regCreator = new RegistryCreator();
        inventoryRegistry = regCreator.getInventoryRegistry();
        sale = new Sale();
    }

    @After
    public void tearDown() throws Exception {
        sale = null;
    }

    @Test
    public void testUpdateStock() throws Exception {
        ItemDTO item = inventoryRegistry.getItem(1);
        sale.addItem(item, 3);
        inventoryRegistry.updateStock(sale);
        int quantity = inventoryRegistry.getStock(1);
        assertEquals("Stock was not updated correctly", 7, quantity);
    }
}
