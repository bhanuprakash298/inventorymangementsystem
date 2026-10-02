// Category.java
public enum Category {
    LAPTOP("Laptops"),
    MOBILE("Mobile Phones"),
    ACCESSORY("Accessories"),
    HOME_APPLIANCE("Home Appliances");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}