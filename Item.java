package AutomatedCheckout;

public class Item {
	private String name;
	private double unitPrice, totalPrice, discountedPrice, quantity;
	private int barcode;

	public Item(String name, int barcode, double quantity, double price, double discount) {
		this.name = name;
		this.barcode = barcode;
		this.unitPrice = price;
		this.quantity = quantity;
		this.totalPrice = unitPrice * quantity;
		this.discountedPrice = this.totalPrice * (1 - (discount / 100));
	}

	/**
	 * Gets total price of the item
	 * 
	 * @return double
	 */
	public double getTotalPrice() {
		return this.totalPrice;
	}

	/**
	 * Gets unit price of the item
	 * 
	 * @return double
	 */
	public double getUnitPrice() {
		return this.unitPrice;
	}

	/**
	 * Gets the discounted price of item
	 * 
	 * @return double
	 */
	public double getDiscountedPrice() {
		return this.discountedPrice;
	}

	/**
	 * Overridden toString method
	 */
	public String toString() {
		return String.format("%-10s %-15s  $%1.2f\n", this.barcode, this.name, this.totalPrice);
	}

	/**
	 * Gets the quantity of the item
	 * 
	 * @return double
	 */
	public double getQuantity() {
		return this.quantity;
	}
}
