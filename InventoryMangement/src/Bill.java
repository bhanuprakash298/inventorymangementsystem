import java.util.ArrayList;
import java.util.List;

public class Bill {
    private static int counter = 1000;
    private final int billNo;
    private final List<String> lines = new ArrayList<>();
    private double total;

    public Bill() {
        this.billNo = ++counter;
    }

    public void addItem(Product p, int qty) {
        double amount = p.getPrice() * qty;
        lines.add(String.format("%-20s x%-3d @ %10.2f = %12.2f", p.getProductName(), qty, p.getPrice(), amount));
        total += amount;
    }

    public boolean isEmpty() { return lines.isEmpty(); }

    public double getTotal() { return total; }

    public void print() {
        System.out.println("\n========== BILL #" + billNo + " ==========");
        lines.forEach(System.out::println);
        System.out.println("------------------------------------------------------");
        System.out.printf("%-42s %12.2f%n", "GRAND TOTAL (Rs.)", total);
        System.out.println("======================================================");
    }
}
