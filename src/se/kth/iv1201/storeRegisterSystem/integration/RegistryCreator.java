package se.kth.iv1201.storeRegisterSystem.integration;

public class RegistryCreator {
    private InventoryRegistry inventoryRegistry;
    private DiscountRulesRegistry discountRulesRegistry;

    public RegistryCreator() {
        inventoryRegistry = new InventoryRegistry();
        discountRulesRegistry = new DiscountRulesRegistry();
    }

    public InventoryRegistry getInventoryRegistry() {
        return inventoryRegistry;
    }

    public DiscountRulesRegistry getDiscountRulesRegistry() {
        return discountRulesRegistry;
    }
}
