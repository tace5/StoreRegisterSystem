package se.kth.iv1201.storeRegisterSystem.controllers;

import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.InventoryRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;
import se.kth.iv1201.storeRegisterSystem.integration.RegistryCreator;
import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;
import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;
import se.kth.iv1201.storeRegisterSystem.model.Sale;

public class Controller {
    private Sale currentSale;
    private Printer printer;
    private InventoryRegistry inventoryRegistry;
    private DiscountRulesRegistry discountRulesRegistry;

    public Controller(Printer printer, RegistryCreator regCreator) {
        this.printer = printer;
        this.inventoryRegistry = regCreator.getInventoryRegistry();
        this.discountRulesRegistry = regCreator.getDiscountRulesRegistry();
    }

    public void startSale() {
        currentSale = new Sale();
    }

    public ItemDTO scanItem(int itemId, int quantity) {
        ItemDTO item = inventoryRegistry.getItem(itemId);
        if (item != null) {
            System.out.println(item.getDescription());
            currentSale.addItem(item, quantity);
            currentSale.updateRunningTotal();
        }

        return item;
    }

    public double getRunningTotal() {
        return currentSale.getRunningTotal();
    }

    public double getChange() {
        return currentSale.calculateChange();
    }

    public void requestDiscount(CustomerDTO customer) {
        currentSale.requestDiscount(customer, discountRulesRegistry);
        currentSale.updateRunningTotal();
    }

    public boolean pay(double paidAmount) {
        boolean acceptPayment = currentSale.pay(paidAmount);
        inventoryRegistry.updateStock(currentSale);
        currentSale.printReceipt(printer);

        return acceptPayment;
    }
}
