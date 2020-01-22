package by.javacourcee.hotel.entities;

import java.time.LocalDate;

public class CreditCard {

	private LocalDate expiresOn;

	private int CCV;

	private String creditCardId;

	public CreditCard() {
	}

	public LocalDate getExpiresOn() {
		return expiresOn;
	}

	public int getCCV() {
		return CCV;
	}

	public String getCreditCardId() {
		return creditCardId;
	}

	public CreditCard(String creditCardId, LocalDate expiresOn, int ccv) {
		this.creditCardId = creditCardId;
		this.expiresOn = expiresOn;
		this.CCV = ccv;
	}
}
