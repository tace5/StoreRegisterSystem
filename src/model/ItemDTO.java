package model;

public class ItemDTO {
    private int identifier;
    private int price;
    private String description;

    public ItemDTO(int identifier, int price, String description) {
        this.identifier = identifier;
        this.price = price;
        this.description = description;
    }
}
