package se.kth.iv1201.storeRegisterSystem.view;

import se.kth.iv1201.storeRegisterSystem.controllers.Controller;
import se.kth.iv1201.storeRegisterSystem.exceptions.OperationFailedException;
import se.kth.iv1201.storeRegisterSystem.logger.Logger;
import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;
import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;

import java.util.Scanner;

public class View {
    private Controller controller;
    private Logger logger;
    private Scanner scan = new Scanner(System.in);

    public View(Controller controller) {
        this.logger = new Logger();
        this.controller = controller;
    }

    private void displayItemInfo(ItemDTO item) {
        System.out.println("Description: " + item.getDescription());
        System.out.println("Price: " + item.getPrice());
    }

    private void displayRunningTotal() {
        double runningTotal = controller.getRunningTotal();
        System.out.println("Running total: " + runningTotal);
    }

    private void scanItem() {
        System.out.println("Item Identifier: ");
        int itemId = scan.nextInt();
        System.out.println("Quantity: ");
        int quantity = scan.nextInt();
        try {
            ItemDTO item = controller.scanItem(itemId, quantity);
            displayItemInfo(item);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.logException(ex);
        }

        displayRunningTotal();
    }

    private void takePayment() {
        boolean acceptPayment = false;

        while (!acceptPayment) {
            System.out.println("Paid amount: ");
            double amount = scan.nextDouble();
            acceptPayment = controller.pay(amount);
            System.out.println("Change to give: " + controller.getChange());
        }

    }

    public void initView() {
        boolean startSale = true;

        while (startSale) {
            if (startSale) {
                POSloop();
            }
            System.out.println("Start new sale (y/n): ");
            startSale = scan.next().equals("y");
        }
    }

    private void POSloop() {
        boolean itemsLeft = true;

        controller.startSale();

        while (itemsLeft) {
            scanItem();

            System.out.println("1. Scan more items");
            System.out.println("2. Take payment");
            System.out.println("Select option: ");
            int option = scan.nextInt();

            if (option == 2) {
                itemsLeft = false;
            }
        }

        System.out.println("Discount requested (y/n): ");
        boolean discountRequested = scan.next().equals("y");

        if (discountRequested) {
            System.out.println("Customer identification: ");
            int customerId = scan.nextInt();
            try {
                controller.requestDiscount(new CustomerDTO("", customerId));
            } catch (OperationFailedException ex) {
                ex.printStackTrace();
                logger.logException(ex);
            }

            displayRunningTotal();
        }

        takePayment();
    }
}
