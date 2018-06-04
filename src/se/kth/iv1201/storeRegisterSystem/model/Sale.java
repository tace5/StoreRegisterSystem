package se.kth.iv1201.storeRegisterSystem.model;

import se.kth.iv1201.storeRegisterSystem.exceptions.DatabaseFailureException;
import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sale {
    private final double SALES_TAX = 1.257;
    private double runningTotal;
    private double paidAmount = 0;
    private HashMap<ItemDTO, Integer> itemsList;
    private Discount discount;
    private List<SaleObserver> saleObservers = new ArrayList<>();

    public Sale () {
        itemsList = new HashMap<>();
    }

    /**
     * Adds an item to the sale
     *
     * @param scannedItem ItemDTO
     * @param quantity quantity
     */
    public void addItem(ItemDTO scannedItem, int quantity) {
        if(!itemsList.containsKey(scannedItem)) {
            this.itemsList.put(scannedItem, quantity);
        } else{
            this.itemsList.replace(scannedItem, this.itemsList.get(scannedItem) + quantity);
        }
        this.runningTotal += scannedItem.getPrice() * quantity;
    }

    /**
     * Adds a discount to the sale
     *
     * @param customer CustomerDTO
     * @param rules DiscountRulesRegistry
     */
    public void requestDiscount(CustomerDTO customer, DiscountRulesRegistry rules) throws DatabaseFailureException {
        discount = new Discount(customer, rules);
    }

    /**
     * Updates the paidAmount field and checks if the payment is enough. Returns true if it is and false otherwise.
     *
     * @param paidAmount
     * @return boolean
     */
    public boolean pay(double paidAmount) {
        if (paidAmount < runningTotal) {
            return false;
        } else {
            this.paidAmount = paidAmount;
            return true;
        }
    }

    /**
     * Updates the running total by going through the items list and checking the price and quantity of each item
     */
    public void updateRunningTotal() {
        double updatedTotal = 0;

        for (Map.Entry<ItemDTO, Integer> entry : itemsList.entrySet()) {
            ItemDTO item = entry.getKey();

            updatedTotal += item.getPrice() * entry.getValue();
        }

        updatedTotal *= SALES_TAX;
        if (discount != null) {
            updatedTotal -= updatedTotal * discount.getAmount();
        }

        runningTotal = updatedTotal;
        updateView();
    }

    /**
     * Updates the total revenue view with new totals
     */
    public void updateView() {
        for (SaleObserver obs : saleObservers){
            obs.updateTotals(this);
        }
    }

    /**
     * Adds a view to the observers of this class
     *
     * @param obs SaleObserver
     */
    public void addSaleObserver(SaleObserver obs){
        saleObservers.add(obs);
    }

    /**
     * Prints a receipt for the sale
     *
     * @param printer Printer
     */
    public void printReceipt(Printer printer) {
        Receipt receipt = new Receipt(this);
        printer.printReceipt(receipt);
    }

    /**
     * Returns the item list
     *
     * @return HashMap<ItemDTO, Integer>
     */
    public HashMap<ItemDTO, Integer> getItemList() {
        return itemsList;
    }

    /**
     * Returns the running total
     *
     * @return double
     */
    public double getRunningTotal() {
        return runningTotal;
    }

    /**
     * Returns the discount amount
     *
     * @return double
     */
    public double getDiscountAmount() {
        if (this.discount != null)
            return this.discount.getAmount() * 100;
        else
            return 0;
    }

    /**
     * Returns the paid amount
     *
     * @return double
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * Calculates the amount of change to be given
     *
     * @return double
     */
    public double calculateChange() {
        return paidAmount - runningTotal;
    }
}
