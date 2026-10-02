// Main.java
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final InventoryService service = new InventoryService();

    public static void main(String[] args) {
        seedData();
        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option: ");
            try {
                handle(choice);
            } catch (OutOfStockException e) {
                System.out.println("Sale failed: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
        System.out.println("Thank you for using the Inventory System!");
    }

    private static void handle(int choice) throws OutOfStockException {
        switch (choice) {
            case 1 -> addProduct();
            case 2 -> {
                Product p = service.findById(readInt("Product ID: "));
                System.out.println(p == null ? "Product not found." : header() + "\n" + p);
            }
            case 3 -> {
                service.updatePrice(readInt("Product ID: "), readDouble("New price: "));
                System.out.println("Price updated.");
            }
            case 4 -> {
                service.addStock(readInt("Product ID: "), readInt("Quantity to add: "));
                System.out.println("Stock updated.");
            }
            case 5 -> sellProducts();
            case 6 -> display(service.getAllProducts());
            case 7 -> display(service.getLowStockProducts());
            case 8 -> System.out.println(service.deleteProduct(readInt("Product ID: "))
                    ? "Product deleted." : "Product not found.");
            case 0 -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void addProduct() {
        int id = readInt("Product ID: ");
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; i++) System.out.println((i + 1) + ". " + cats[i].getLabel());
        int c = readInt("Category number: ");
        if (c < 1 || c > cats.length) throw new IllegalArgumentException("Invalid category.");
        double price = readDouble("Price: ");
        int stock = readInt("Initial stock: ");
        System.out.println(service.addProduct(new Product(id, name, cats[c - 1], price, stock))
                ? "Product added." : "A product with this ID already exists.");
    }

    private static void sellProducts() throws OutOfStockException {
        Bill bill = new Bill();
        String more;
        do {
            try {
                service.sellProduct(readInt("Product ID: "), readInt("Quantity: "), bill);
            } catch (OutOfStockException | IllegalArgumentException e) {
                System.out.println("Skipped: " + e.getMessage());
            }
            System.out.print("Add another item? (y/n): ");
            more = sc.nextLine().trim();
        } while (more.equalsIgnoreCase("y"));
        if (bill.isEmpty()) System.out.println("No items sold.");
        else bill.print();
    }

    private static void display(List<Product> list) {
        if (list.isEmpty()) { System.out.println("Nothing to show."); return; }
        System.out.println(header());
        list.forEach(System.out::println);
    }

    private static String header() {
        return String.format("| %-4s | %-20s | %-15s | %10s | %5s |", "ID", "Name", "Category", "Price", "Stock");
    }

    private static void printMenu() {
        System.out.println("\n===== ELECTRONICS SHOP INVENTORY =====");
        System.out.println("1. Add product        2. Search by ID");
        System.out.println("3. Update price       4. Add stock");
        System.out.println("5. Sell / Bill        6. Show all products");
        System.out.println("7. Low stock (<5)     8. Delete product");
        System.out.println("0. Exit");
    }

    private static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter a valid whole number."); }
        }
    }

    private static double readDouble(String msg) {
        while (true) {
            System.out.print(msg);
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Enter a valid number."); }
        }
    }

    private static void seedData() {
        service.addProduct(new Product(101, "Dell Inspiron 15", Category.LAPTOP, 55000, 8));
        service.addProduct(new Product(102, "Redmi Note 13", Category.MOBILE, 17999, 3));
        service.addProduct(new Product(103, "Logitech Keyboard", Category.ACCESSORY, 1499, 12));
        service.addProduct(new Product(104, "Mixer Grinder", Category.HOME_APPLIANCE, 3200, 0));
    }
}