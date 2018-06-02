package se.kth.iv1201.storeRegisterSystem.model;

public class ItemDTO {
    private int identifier;
    private double price;
    private String description;

    public ItemDTO(int identifier, double price, String description) {
        this.identifier = identifier;
        this.price = price;
        this.description = description;
    }

    /**
     * Returns the item identifier
     *
     * @return int
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Returns the items price
     *
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the item description
     *
     * @return double
     */
    public String getDescription() {
        return description;
    }
}
