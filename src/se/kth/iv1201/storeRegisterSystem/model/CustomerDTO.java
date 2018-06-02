package se.kth.iv1201.storeRegisterSystem.model;

public class CustomerDTO {
    private String name;
    private int customerId;

    public CustomerDTO(String name, int customerId) {
        this.name = name;
        this.customerId = customerId;
    }

    /**
     * Returns the customer id
     *
     * @return int
     */
    public int getCustomerId() {
        return customerId;
    }
}
