package se.kth.iv1201.storeRegisterSystem.integration;

import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;

import java.util.HashMap;

public class DiscountRulesRegistry {

    private HashMap<Integer, Double> discountDatabase;

    public DiscountRulesRegistry() {
        discountDatabase = new HashMap<>();

        // Dummy data
        discountDatabase.put(1234, 0.2);
        discountDatabase.put(1235, 0.1);
        discountDatabase.put(1236, 0.1);
        discountDatabase.put(1237, 0.0);
        discountDatabase.put(1238, 0.0);
        discountDatabase.put(1239, 0.25);
        discountDatabase.put(1240, 0.1);
    }

    public double checkDiscountAmount(CustomerDTO customer) {
        int customerId = customer.getCustomerId();
        if (discountDatabase.get(customerId) != null) {
            return discountDatabase.get(customerId);
        }
        
        return 0;
    }
}
