package se.kth.iv1201.storeRegisterSystem.view;

import se.kth.iv1201.storeRegisterSystem.model.Sale;
import se.kth.iv1201.storeRegisterSystem.model.SaleObserver;

import java.util.HashMap;
import java.util.Map;

public class TotalRevenueView implements SaleObserver {
    private Map<Sale, Double> saleTotals = new HashMap<>();

    public TotalRevenueView() {}

    @Override
    public void updateTotals(Sale sale) {
        addTotal(sale);
        printSaleTotals();
    }

    private void addTotal(Sale sale) {
        saleTotals.put(sale, sale.getRunningTotal());
    }

    private void printSaleTotals() {
        System.out.println("SALE TOTALS SINCE PROGRAM START");
        int index = saleTotals.size();

        for (Map.Entry<Sale, Double> entry : saleTotals.entrySet()) {
            System.out.println("Sale nr " + index + " total: " + entry.getValue());
            index--;
        }
    }
}
