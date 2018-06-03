package se.kth.iv1201.storeRegisterSystem.controllers;

import se.kth.iv1201.storeRegisterSystem.exceptions.DatabaseFailureException;
import se.kth.iv1201.storeRegisterSystem.exceptions.ItemNotFoundException;
import se.kth.iv1201.storeRegisterSystem.exceptions.OperationFailedException;
import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.InventoryRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;
import se.kth.iv1201.storeRegisterSystem.integration.RegistryCreator;
import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;
import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;
import se.kth.iv1201.storeRegisterSystem.model.Sale;
import se.kth.iv1201.storeRegisterSystem.view.TotalRevenueView;

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
        currentSale.addSaleObserver(new TotalRevenueView());
    }

    /**
     * Adds an item to the current sale and updates the running total
     *
     * @param itemId
     * @param quantity
     * @return ItemDTO
     */
    public ItemDTO scanItem(int itemId, int quantity) throws OperationFailedException, ItemNotFoundException {
        try {
            ItemDTO item = inventoryRegistry.getItem(itemId);
            currentSale.addItem(item, quantity);
            currentSale.updateRunningTotal();
            return item;
        } catch (DatabaseFailureException ex) {
            throw new OperationFailedException("Could not scan item.", ex);
        }
    }

    /**
     * Returns the stock of a given item. For testing only
     *
     * @param itemId
     * @return int
     */
    public int getItemStock(int itemId) throws OperationFailedException, ItemNotFoundException {
        try {
            return inventoryRegistry.getStock(itemId);
        } catch (DatabaseFailureException ex) {
            throw new OperationFailedException("Could not get item stock", ex);
        }
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
    public void requestDiscount(CustomerDTO customer) throws OperationFailedException {
        try {
            currentSale.requestDiscount(customer, discountRulesRegistry);
        } catch (DatabaseFailureException ex) {
            throw new OperationFailedException("Failed to add discount.", ex.getCause());
        }
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
