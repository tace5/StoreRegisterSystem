package se.kth.iv1201.storeRegisterSystem.integration;

public class RegistryCreator {
    private InventoryRegistry inventoryRegistry;
    private DiscountRulesRegistry discountRulesRegistry;

    public RegistryCreator() {
        inventoryRegistry = new InventoryRegistry();
        discountRulesRegistry = new DiscountRulesRegistry();
    }

    /**
     * Returns the inventoryRegistry instance
     *
     * @return InventoryRegistry
     */
    public InventoryRegistry getInventoryRegistry() {
        return inventoryRegistry;
    }

    /**
     * Returns the discountRulesRegistry instance
     *
     * @return DiscountRulesRegistry
     */
    public DiscountRulesRegistry getDiscountRulesRegistry() {
        return discountRulesRegistry;
    }
}
