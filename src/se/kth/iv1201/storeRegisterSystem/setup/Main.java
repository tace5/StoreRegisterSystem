package se.kth.iv1201.storeRegisterSystem.setup;

import se.kth.iv1201.storeRegisterSystem.controllers.Controller;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;
import se.kth.iv1201.storeRegisterSystem.integration.RegistryCreator;
import se.kth.iv1201.storeRegisterSystem.view.TotalRevenueView;
import se.kth.iv1201.storeRegisterSystem.view.View;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        RegistryCreator registryCreator = new RegistryCreator();
        TotalRevenueView totalRevenueView = new TotalRevenueView();
        Controller controller = new Controller(printer, registryCreator, totalRevenueView);
        View view = new View(controller);

        view.initView();
    }
}
