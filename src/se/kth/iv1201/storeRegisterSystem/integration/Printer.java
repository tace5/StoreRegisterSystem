package se.kth.iv1201.storeRegisterSystem.integration;

import se.kth.iv1201.storeRegisterSystem.model.Receipt;

public class Printer {
    public Printer() {}

    public void printReceipt(Receipt receipt) {
        String receiptText = receipt.createReceiptString();

        // Print receipt
    }
}
