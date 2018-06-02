package se.kth.iv1201.storeRegisterSystem.view;

import se.kth.iv1201.storeRegisterSystem.controllers.Controller;
import se.kth.iv1201.storeRegisterSystem.model.CustomerDTO;
import se.kth.iv1201.storeRegisterSystem.model.ItemDTO;

import java.util.Scanner;

public class View {
    private Controller controller;
    private Scanner scan = new Scanner(System.in);

    public View(Controller controller) {
        this.controller = controller;
    }

    public void displayItemInfo(ItemDTO item) {
        System.out.println("Description: " + item.getDescription());
        System.out.println("Price: " + item.getPrice());
    }

    public void displayRunningTotal() {
        double runningTotal = controller.getRunningTotal();
        System.out.println("Running total: " + runningTotal);
    }

    private void scanItem() {
        System.out.println("Item Identifier: ");
        int itemId = scan.nextInt();
        System.out.println("Quantity: ");
        int quantity = scan.nextInt();

        ItemDTO item = controller.scanItem(itemId, quantity);
        if (item == null) {
            System.out.println("Item not found");
        } else {
            displayItemInfo(item);
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
            startSale = scan.nextLine().equals("y");
        }
    }

    public void POSloop() {
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
            controller.requestDiscount(new CustomerDTO("", customerId));

            displayRunningTotal();
        }

        takePayment();
    }
}
