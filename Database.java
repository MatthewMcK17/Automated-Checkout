package AutomatedCheckout;

import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

//Data obtained from http://www.grocery.com/open-grocery-database-project/ 
//I created my own prices though
public class Database {
	private ArrayList<String[]> data = new ArrayList<>();

	public Database() {
		createDatabase();
	}
	/**
	 * Reads from CSV file and stores it onto an ArrayList data structure
	 */
	private void createDatabase() {
		BufferedReader inFile = null;
		String line;
		try {
			inFile = new BufferedReader(new FileReader("Grocery_UPC_Database.csv"));
			
			while ((line = inFile.readLine()) != null) {
				data.add(line.split(","));
			}
			
			inFile.close();
		} catch (IOException a) {
			System.out.println(a.getLocalizedMessage());
		}
	}
	public String getItemName(int barcode) {
		return data.get(barcode)[4];
	}
	public double getItemPrice(int barcode) {
		return Double.parseDouble(data.get(barcode)[5]);
	}
}
