package se.kth.iv1201.storeRegisterSystem.model;

import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Sale {
    private final double SALES_TAX = 1.257;
    private double runningTotal;
    private double paidAmount = 0;
    private HashMap<ItemDTO, Integer> itemsList;
    private Discount discount;

    public Sale () {
        itemsList = new HashMap<>();
    }

    public void addItem(ItemDTO scannedItem, int quantity) {
        if(!itemsList.containsKey(scannedItem)) {
            this.itemsList.put(scannedItem, quantity);
        } else{
            this.itemsList.replace(scannedItem, this.itemsList.get(scannedItem) + quantity);
        }
        this.runningTotal += scannedItem.getPrice() * quantity;
    }

    public void requestDiscount(CustomerDTO customer, DiscountRulesRegistry rules) {
        discount = new Discount(customer, rules);
    }

    public boolean pay(double paidAmount) {
        if (paidAmount < runningTotal) {
            return false;
        } else {
            this.paidAmount = paidAmount;
            return true;
        }
    }

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
    }

    public void printReceipt(Printer printer) {
        Receipt receipt = new Receipt(this);
        printer.printReceipt(receipt);
    }

    public HashMap<ItemDTO, Integer> getItemList() {
        return itemsList;
    }

    public double getRunningTotal() {
        return runningTotal;
    }

    public double getDiscountAmount() {
        return this.discount.getAmount() * 100;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public double calculateChange() {
        return paidAmount - runningTotal;
    }
}
