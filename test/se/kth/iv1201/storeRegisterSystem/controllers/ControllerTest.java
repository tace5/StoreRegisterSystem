package se.kth.iv1201.storeRegisterSystem.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.InventoryRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;
import se.kth.iv1201.storeRegisterSystem.integration.RegistryCreator;
import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;
import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;

public class ControllerTest {
    private Printer printer;
    private DiscountRulesRegistry discountRulesRegistry;
    private InventoryRegistry inventoryRegistry;
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        printer = new Printer();
        RegistryCreator regCreator = new RegistryCreator();
        discountRulesRegistry = regCreator.getDiscountRulesRegistry();
        inventoryRegistry = regCreator.getInventoryRegistry();
        controller = new Controller(printer, regCreator);
        controller.startSale();
    }

    @After
    public void tearDown() throws Exception {
        printer = null;
        discountRulesRegistry = null;
        inventoryRegistry = null;
        controller = null;
    }

    @Test
    public void testScanItem() throws Exception {
        ItemDTO expectedItem = inventoryRegistry.getItem(1);

        ItemDTO item = controller.scanItem(1, 2);
        assertEquals("Doesn't return the correct item", expectedItem, item);
    }

    @Test
    public void testRequestDiscount() throws Exception {
        controller.scanItem(1, 2);
        controller.scanItem(2, 3);
        controller.scanItem(3, 1);

        double DISCOUNT = 15;

        double totalBeforeDiscount = controller.getRunningTotal();
        double expetedTotalAfterDiscount = totalBeforeDiscount * ((100 - DISCOUNT) / 100);

        controller.requestDiscount(new CustomerDTO("", 1));

        double totalAfterDiscount = controller.getRunningTotal();

        assertEquals("Discount is not applied correctly", expetedTotalAfterDiscount, totalAfterDiscount, 0.001);
    }

    @Test
    public void testPay() throws Exception {
        controller.scanItem(1, 4);
        controller.scanItem(2, 2);
        controller.scanItem(3, 1);

        double BIG_PAYMENT = 500;
        controller.pay(BIG_PAYMENT);

        int item1Stock = controller.getItemStock(1);
        int item2Stock = controller.getItemStock(2);
        int item3Stock = controller.getItemStock(3);
        assertEquals("Stock has not been removed correctly", 6, item1Stock);
        assertEquals("Stock has not been removed correctly", 8, item2Stock);
        assertEquals("Stock has not been removed correctly", 9, item3Stock);

        double change = controller.getChange();
        assertEquals("Stock has not been removed correctly", 370.07, change, 0.001);
    }
}
