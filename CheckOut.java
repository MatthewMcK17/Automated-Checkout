package AutomatedCheckout;

import java.util.Scanner;
import java.util.Random;

public class CheckOut {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Random rand = new Random();
		
		System.out.print("Please enter your name: ");
		String name = in.nextLine();
		System.out.println();
		
		APS aps = new APS(rand.nextInt(999999999), name);
		
		// Choose 3 for credit card type and use the # 379354508162306
		aps.createCart(in);
		
		in.close();
	}
}
