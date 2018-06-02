package se.kth.iv1201.storeRegisterSystem.model;

import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;

public class Discount {
    private double amount;

    public Discount(CustomerDTO customer, DiscountRulesRegistry rules) {
        amount = rules.checkDiscountAmount(customer);
    }

    public double getAmount() {
        return amount;
    }
}
