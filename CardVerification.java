package AutomatedCheckout;

// Using the Luhn Algorithm
//https://www.geeksforgeeks.org/program-credit-card-number-validation/
public class CardVerification {
	private boolean valid = false;
	private String cardNumber = "";
	private String apsId;
	private int cardType;
	private double amountDue;

	// Private constants to be used to verify CC#
	private final String MASTERCARD_PREFIX = "5";
	private final String VISACARD_PREFIX = "4";
	private final String AMERICAN_EXPRESS_PREFIX = "37";
	private final String DISCOVERCARD_PREFIX = "6";
	private final String[] CARD_PREFIXES = { MASTERCARD_PREFIX, VISACARD_PREFIX, AMERICAN_EXPRESS_PREFIX,
			DISCOVERCARD_PREFIX };
	private final String[] creditCards = { "Mastercard", "Visa Card", "American Express", "Discover Card",
			"Credit Card" };
	
	// Choose 3 for credit card type and use the # 379354508162306
	public CardVerification(String apsId, String cardNumber, int cardType, double amountDue) {
		this.apsId = apsId;
		this.cardNumber = cardNumber;
		this.cardType = cardType;
		this.amountDue = amountDue;
		creditCardValidation();
	}

	/**
	 * Returns card validity
	 * 
	 * @return boolean valid
	 */
	public boolean getValidity() {
		return valid;
	}

	/**
	 * Determines whether card is valid or not
	 */
	private void creditCardValidation() {
		valid = (cardNumber.length() >= 13 && cardNumber.length() <= 16)
				&& (verifyCardPrefix() && ((sumOfEvenPlaces() + sumOfOddPlaces()) % 10 == 0));
	}

	/**
	 * Calculates sum of even places in CC#, by doubling each place
	 * 
	 * @return int sum
	 */
	private int sumOfEvenPlaces() {
		int sum = 0;
		for (int i = cardNumber.length() - 2; i >= 0; i -= 2) {
			int num = Integer.parseInt(cardNumber.charAt(i) + "") * 2;
			if (num > 9) {
				sum += ((num / 10) + num % 10);
			} else {
				sum += num;
			}
		}
		return sum;
	}

	/**
	 * Calculates the total sum of the odd places in the credit card
	 * 
	 * @return int sum
	 */
	private int sumOfOddPlaces() {
		int sum = 0;
		for (int i = cardNumber.length() - 1; i >= 0; i -= 2) {
			sum += Integer.parseInt(cardNumber.charAt(i) + "");
		}

		return sum;
	}

	/**
	 * Verifies if card prefix matches the preset values in the constant variables
	 * 
	 * @return
	 */
	private boolean verifyCardPrefix() {
		if (this.cardType == 4) {
			for (String prefix : CARD_PREFIXES) {
				int prefixLength = prefix.length();
				if (cardNumber.substring(0, prefixLength).equals(prefix)) {
					return true;
				}
			}
		} else {
			int prefixLength = CARD_PREFIXES[this.cardType].length();
			if (cardNumber.substring(0, prefixLength).equals(CARD_PREFIXES[this.cardType])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets credit card type
	 * 
	 * @return String
	 */
	public String getCardType() {
		return creditCards[this.cardType];
	}

	/**
	 * Returns CC# but only shows last 4 digits
	 * 
	 * @return String
	 */
	public String getCardNumber() {
		String card = "";
		for (int i = 0; i < cardNumber.length() - 4; i++) {
			card += "*";
		}
		return (card + cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
	}

	/**
	 * Gets APS ID
	 * 
	 * @return String apsId
	 */
	public String geApsId() {
		return this.apsId;
	}

	/**
	 * Gets amount due from the credit card
	 * 
	 * @return double
	 */
	public double getAmountDue() {
		return this.amountDue;
	}
}
