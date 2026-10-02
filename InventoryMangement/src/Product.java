public class Product {
    private final int productId;
    private String productName;
    private Category category;
    private double price;
    private int stock;

    public Product(int productId, String productName, Category category, double price, int stock) {
        if (price <= 0) throw new IllegalArgumentException("Price must be greater than zero.");
        if (stock < 0) throw new IllegalArgumentException("Stock cannot be negative.");
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getProductId()        { return productId; }
    public String getProductName()   { return productName; }
    public Category getCategory()    { return category; }
    public double getPrice()         { return price; }
    public int getStock()            { return stock; }

    public void updatePrice(double newPrice) {
        if (newPrice <= 0) throw new IllegalArgumentException("Price must be greater than zero.");
        this.price = newPrice;
    }

    public void addStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity to add must be positive.");
        stock += quantity;
    }

    public void sellProduct(int quantity) throws OutOfStockException {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity to sell must be positive.");
        if (stock == 0) throw new OutOfStockException(productName + " is out of stock.");
        if (quantity > stock)
            throw new OutOfStockException("Only " + stock + " unit(s) of " + productName + " available.");
        stock -= quantity;
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-15s | %10.2f | %5d |",
                productId, productName, category.getLabel(), price, stock);
    }
}