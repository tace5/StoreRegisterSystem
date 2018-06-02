package se.kth.iv1201.storeRegisterSystem.integration;

import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;
import se.kth.iv1201.storeRegisterSystem.model.Sale;

import java.util.HashMap;
import java.util.Map;

public class InventoryRegistry {
    private Map<Integer, ItemStock<ItemDTO, Integer>> inventoryDatabase;

    /**
     * Creates instance of object and initiates a "Database" of items, each with 10 in stock
     */
    public InventoryRegistry() {
        inventoryDatabase = new HashMap<>();
        inventoryDatabase.put(1, new ItemStock<>(new ItemDTO(1, 14.99, "Sour cream"), 10));
        inventoryDatabase.put(2, new ItemStock<>(new ItemDTO(2, 29.99, "Tortillas"), 10));
        inventoryDatabase.put(3, new ItemStock<>(new ItemDTO(3, 9.99, "Salsa"), 10));
        inventoryDatabase.put(4, new ItemStock<>(new ItemDTO(4, 49.99, "Ground beef"), 10));
        inventoryDatabase.put(5, new ItemStock<>(new ItemDTO(5, 4.99, "Spice mix"), 10));
        inventoryDatabase.put(6, new ItemStock<>(new ItemDTO(6, 9.99, "Corn"), 10));
    }

    /**
     * Returns the ItemDTO of the item asked for
     *
     * @param itemId int
     * @return ItemDTO
     */
    public ItemDTO getItem(int itemId) {
        if (inventoryDatabase.get(itemId) != null) {
            return this.inventoryDatabase.get(itemId).getItem();
        }

        return null;
    }

    /**
     * Updates the stock based on the items in the current Sale
     *
     * @param sale Sale
     * @return boolean
     */
    public void updateStock(Sale sale) {
        for (Map.Entry<ItemDTO, Integer> entry : sale.getItemList().entrySet()) {
            int itemId = entry.getKey().getIdentifier();
            ItemStock<ItemDTO, Integer> item = inventoryDatabase.get(itemId);;

            item.setStock(item.getStock() - entry.getValue());
        }
    }

    private class ItemStock<Item, Stock> {
        private Item item;
        private Stock stock;

        ItemStock(Item item, Stock stock){
            this.item = item;
            this.stock = stock;
        }

        Item getItem(){ return item; }

        Stock getStock(){ return stock; }

        void setStock(Stock stock){
            this.stock = stock;
        }
    }
}
