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

    public int getIdentifier() {
        return identifier;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
