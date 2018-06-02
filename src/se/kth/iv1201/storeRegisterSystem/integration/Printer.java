package se.kth.iv1201.storeRegisterSystem.integration;

import se.kth.iv1201.storeRegisterSystem.model.Receipt;

public class Printer {
    public Printer() {}

    /**
     * Prints the customers receipt
     *
     * @param receipt
     */
    public void printReceipt(Receipt receipt) {
        String receiptText = receipt.createReceiptString();

        // Print receipt
    }
}
