package AutomatedCheckout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class APS {

	private ArrayList<Item> cart = new ArrayList<Item>();
	private final double tax = 1.06;
	private String apsId, customerName;
	private Transactions transaction = new Transactions();

	public APS(int id, String custName) {
		this.apsId = id + "";
		this.customerName = custName;
	}

	/**
	 * Calculates total costs (excluding any discounts)
	 * 
	 * @return double totalCost
	 */
	private double totalCost() {
		double totalCost = 0;
		for (Item a : cart) {
			totalCost += a.getTotalPrice();
		}
		return totalCost;
	}

	/**
	 * Calculates total costs (including discounts)
	 * 
	 * @return double totalCost
	 */
	private double discountedCost() {
		double totalCost = 0;
		for (Item a : cart) {
			totalCost += a.getDiscountedPrice();
		}
		return totalCost;
	}

	public void addToCart(Item a) {
		cart.add(a);
	}

	/**
	 * Prints the receipt
	 * 
	 * @param card CardVerification object
	 */
	private void printReceipt(CardVerification card, double percent) {
		System.out.println("\tReceipt ID: " + this.apsId);
		System.out.println("\tCashier: " + this.customerName + "\n");

		System.out.printf("%-11s%-17s%n", "Barcode", "Item Name & Price");
		System.out.println("---------------------------------------");

		double totalItems = 0;
	    for (Item a : cart) {
			System.out.print(a);
			totalItems += a.getQuantity();
		}
		System.out.println("---------------------------------------");

		double cost = discountedCost() * (1 - percent);

		System.out.println(String.format("%25s%9.2f", "Subtotal", cost));
		System.out.println(String.format("%25s%9.2f", "Tax 6.000%", cost * (this.tax - 1)));
		System.out.println(String.format("%25s%9.2f", "Total", cost * this.tax));
		System.out.println(String.format("%25s%9.2f", "Total Savings", (totalCost() - cost)));
		System.out.println(String.format("%25s%9.2f", "Total Items Sold", totalItems));

		System.out.println();
		System.out.println("\t" + card.getCardType() + ": " + card.getCardNumber());
		System.out.println();

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

		System.out.println("\t" + format.format(date));
		
		transaction.addTransaction(cart);

	}

	/**
	 * Creates a cart based on the items the user wants to buy
	 * 
	 * @param in Scanner object
	 */
	public void createCart(Scanner in) {
		String ans;

		Database data = new Database();

		do {
			System.out.print("Please enter item barcode (1 - 110436): ");
			int barcode = in.nextInt();
			
			while (barcode > 110436) {
				System.out.print("Invalid entry. Please enter item barcode (1 - 110436): ");
				barcode = in.nextInt();
			}

			System.out.print("Please enter item quantity (i.e # of pounds or # of items): ");
			double quantity = in.nextDouble();
			
			while (quantity <= 0 ) {
				System.out.print("Invalid quantity. Please enter item quantity (i.e # of pounds or # of items): ");
				quantity = in.nextDouble();
			}

			System.out.print("Does this item have a discount (y or n)? ");
			String discountAns = in.next().toLowerCase();

			if (discountAns.equals("y") || discountAns.equals("yes")) {
				System.out.print("Enter percent off: ");
				double discount = in.nextDouble();
				
				while (discount < 0 || discount > 100) {
					System.out.println("Error: Invalid percentage. Please try again.");
					System.out.print("Enter percent off: ");
					discount = in.nextDouble();
				}

				cart.add(new Item(data.getItemName(barcode), barcode, quantity, data.getItemPrice(barcode), discount));
			} else {
				cart.add(new Item(data.getItemName(barcode), barcode, quantity, data.getItemPrice(barcode), 0));
			}

			System.out.print("Would you like to enter another item (y or n)? ");
			ans = in.next().toLowerCase();
			
			if (!(ans.equals("y") || ans.equals("n") || ans.equals("yes") || ans.equals("no"))) {
				System.out.println("Invalid option, please try again.");
				System.out.print("Would you liketo enter another item (y or n)? ");
				ans = in.next().toLowerCase();
			}

			System.out.println();
		} while (!(ans.equals("n") || ans.equals("no")));

		System.out.print("Do you have any coupons that applies to the total price (y or n)? ");
		ans = in.next().toLowerCase();

		double percent;
		if (ans.equals("y") || ans.equals("yes")) {
			System.out.print("Enter percent off: ");
			percent = in.nextDouble(); 
					
			while (percent < 0 || percent > 100) {
				System.out.println("Error: Invalid percentage. Please try again.");
				System.out.print("Enter percent off: ");
				percent = in.nextDouble();
			}
			percent /= 100.0;
		} else {
			percent = 0;
		}

		System.out.println();

		checkOut(in, percent);
	}

	/*
	 * The method that completes the checkout if credit card is valid
	 */
	private void checkOut(Scanner in, double percent) {
		printCardTypes();
		System.out.print("Please enter which card you will be using: ");
		int cardType = in.nextInt();
		if (cardType < 1 || cardType > 5) {
			System.out.println("Invalid Choice... quitting program");
			System.exit(1);
		}

		System.out.print("Please enter credit card #: ");
		String ccNumber = in.next();

		CardVerification creditCard = new CardVerification(this.apsId, ccNumber, (cardType - 1), discountedCost());

		if (creditCard.getValidity()) {
			System.out.println("Successful Transaction!");
			System.out.println();
			printReceipt(creditCard, percent);
		} else {
			System.out.println("Invalid Credit Card.");
		}
	}

	/**
	 * Prints the different types of credit cards
	 */
	private void printCardTypes() {
		System.out.println("Mastercard [1]");
		System.out.println("Visa Card [2]");
		System.out.println("American Express [3]");
		System.out.println("Discover Card [4]");
		System.out.println("Not Sure [5]");
	}
}
