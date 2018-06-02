package se.kth.iv1201.storeRegisterSystem.setup;

import se.kth.iv1201.storeRegisterSystem.controllers.Controller;
import se.kth.iv1201.storeRegisterSystem.integration.Printer;
import se.kth.iv1201.storeRegisterSystem.integration.RegistryCreator;
import se.kth.iv1201.storeRegisterSystem.view.View;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        RegistryCreator registryCreator = new RegistryCreator();
        Controller controller = new Controller(printer, registryCreator);
        View view = new View(controller);

        view.initView();
    }
}
