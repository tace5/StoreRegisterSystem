package se.kth.iv1201.storeRegisterSystem.exceptions;

public class ItemNotFoundException extends Exception {
    /**
     * This exception is thrown when an item can't be found in the inventory database
     *
     * @param itemId
     */
    public ItemNotFoundException(int itemId) {
        super("Item with id \"" + itemId + "\" couldn't be found.");
    }
}
