package se.kth.iv1201.storeRegisterSystem.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Receipt {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private Date time;
    private Sale sale;

    Receipt(Sale sale) {
         time = new Date();
         this.sale = sale;
    }

    /**
     * Returns a string for the receipt text
     *
     * @return String
     */
    public String createReceiptString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sale\n");
        sb.append(dateFormat.format(time) + "\n");
        sb.append("Items:\n");
        
        for (Map.Entry<ItemDTO, Integer> entry : sale.getItemList().entrySet()) {
            ItemDTO item = entry.getKey();

            sb.append(item.getDescription() + " " + entry.getValue() + "x\n");
        }

        sb.append("Total: " + sale.getRunningTotal() + "\n");
        sb.append("Discount: -" + (sale.getRunningTotal() * sale.getDiscountAmount()) + "%");
        sb.append("Paid: " + sale.getPaidAmount() + "\n");
        sb.append("Change: " + (sale.getPaidAmount() - sale.getRunningTotal()));

        return sb.toString();
    }
}
