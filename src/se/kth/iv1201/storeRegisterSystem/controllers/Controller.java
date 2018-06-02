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

    /**
     * @param printer Printer
     * @param regCreator RegistryCreator
     */
    public Controller(Printer printer, RegistryCreator regCreator) {
        this.printer = printer;
        this.inventoryRegistry = regCreator.getInventoryRegistry();
        this.discountRulesRegistry = regCreator.getDiscountRulesRegistry();
    }

    /**
     * Initializes a sale
     */
    public void startSale() {
        currentSale = new Sale();
    }

    /**
     * Adds an item to the current sale and updates the running total
     *
     * @param itemId
     * @param quantity
     * @return ItemDTO
     */
    public ItemDTO scanItem(int itemId, int quantity) {
        ItemDTO item = inventoryRegistry.getItem(itemId);
        if (item != null) {
            System.out.println(item.getDescription());
            currentSale.addItem(item, quantity);
            currentSale.updateRunningTotal();
        }

        return item;
    }

    /**
     * Returns the stock of a given item. For testing only
     *
     * @param itemId
     * @return int
     */
    public int getItemStock(int itemId) {
        return inventoryRegistry.getStock(itemId);
    }

    /**
     * Returns the running total of the current sale
     *
     * @return double
     */
    public double getRunningTotal() {
        return currentSale.getRunningTotal();
    }

    /**
     * Returns the amount of change to be given to the customer
     *
     * @return double
     */
    public double getChange() {
        return currentSale.calculateChange();
    }

    /**
     * Add a discount to the current sale
     *
     * @param customer CustomerDTO
     */
    public void requestDiscount(CustomerDTO customer) {
        currentSale.requestDiscount(customer, discountRulesRegistry);
        currentSale.updateRunningTotal();
    }

    /**
     * Receive payment from the customer and return true if the payment is enough. Otherwise return false
     *
     * @param paidAmount
     * @return boolean
     */
    public boolean pay(double paidAmount) {
        boolean acceptPayment = currentSale.pay(paidAmount);
        inventoryRegistry.updateStock(currentSale);
        currentSale.printReceipt(printer);

        return acceptPayment;
    }
}
