import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {
    private static final int LOW_STOCK_LIMIT = 5;
    private final Map<Integer, Product> products = new LinkedHashMap<>();

    public boolean addProduct(Product p) {
        if (products.containsKey(p.getProductId())) return false;
        products.put(p.getProductId(), p);
        return true;
    }

    public Product findById(int id) {
        return products.get(id);
    }

    private Product require(int id) {
        Product p = products.get(id);
        if (p == null) throw new IllegalArgumentException("No product found with ID " + id);
        return p;
    }

    public void updatePrice(int id, double newPrice) {
        require(id).updatePrice(newPrice);
    }

    public void addStock(int id, int qty) {
        require(id).addStock(qty);
    }

    public void sellProduct(int id, int qty, Bill bill) throws OutOfStockException {
        Product p = require(id);
        p.sellProduct(qty);
        bill.addItem(p, qty);
    }

    public boolean deleteProduct(int id) {
        return products.remove(id) != null;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> getLowStockProducts() {
        List<Product> low = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getStock() < LOW_STOCK_LIMIT) low.add(p);
        }
        return low;
    }
}
