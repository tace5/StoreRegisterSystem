package se.kth.iv1201.storeRegisterSystem.integration;

import se.kth.iv1201.storeRegisterSystem.exceptions.DatabaseFailureException;
import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;

import java.util.HashMap;

public class DiscountRulesRegistry {
    private HashMap<Integer, Double> discountDatabase;

    /**
     * Creates new instance of DiscountRulesRegistry and initializes it with dummy data
     */
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

    /**
     * Checks how much discount a customer is eligible for
     *
     * @param customer CustomerDTO
     * @return double
     */
    public double checkDiscountAmount(CustomerDTO customer) throws DatabaseFailureException {
        int customerId = customer.getCustomerId();
        if (discountDatabase.get(customerId) != null) {
            return discountDatabase.get(customerId);
        } else if (customerId == 666) {
            throw new DatabaseFailureException();
        }
        
        return 0;
    }
}
