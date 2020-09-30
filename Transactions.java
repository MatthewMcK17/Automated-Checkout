package AutomatedCheckout;

import java.util.ArrayList;

//Stores transactions
public class Transactions {
	private ArrayList<ArrayList<Item>> savedTransaction = new ArrayList<ArrayList<Item>>();
	
	public void addTransaction(ArrayList<Item> a) {
		savedTransaction.add(a);
	}
	
	public ArrayList<ArrayList<Item>> getTransactions() {
		return savedTransaction;
	}
}
