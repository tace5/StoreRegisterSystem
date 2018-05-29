package model;

class ItemQuantity {
    private ItemDTO item;
    private int quantity;

    ItemQuantity(ItemDTO item) {
        this.item = item;
    }

    void increaseQuantity(int quantity) {
        this.quantity = this.quantity++;
    }
}
