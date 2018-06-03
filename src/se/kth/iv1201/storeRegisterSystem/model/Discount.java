package se.kth.iv1201.storeRegisterSystem.model;

import se.kth.iv1201.storeRegisterSystem.exceptions.DatabaseFailureException;
import se.kth.iv1201.storeRegisterSystem.integration.DiscountRulesRegistry;

public class Discount {
    private double amount;

    public Discount(CustomerDTO customer, DiscountRulesRegistry rules) throws DatabaseFailureException {
        amount = rules.checkDiscountAmount(customer);
    }

    /**
     * Returns the discount amount
     *
     * @return double
     */
    public double getAmount() {
        return amount;
    }
}
