package by.javacourcee.hotel.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import by.javacourcee.hotel.entities.CreditCard;
import by.javacourcee.hotel.entities.Guest;
import by.javacourcee.hotel.interfaces.IRepository;

public class GuestsRepository implements IRepository {
	
	static List<Guest> guestsRepository = new ArrayList<>();
	
	
	public static List<Guest> getAllData() {
		return guestsRepository;
	}
	
	public static Guest getById(int id) {
		return guestsRepository.stream().filter(e -> e.getId() == id).findAny().orElse(null);
	}

	static {
		Faker faker = new Faker();
		
		guestsRepository.add(new Guest(1, faker.name().fullName(), faker.address().fullAddress(), faker.address().city(), faker.phoneNumber().phoneNumber(),
                new CreditCard(faker.finance().creditCard(), LocalDate.now().plusYears(3), 387)));
		guestsRepository.add(new Guest(2, faker.name().fullName(), faker.address().fullAddress(), faker.address().city(), faker.phoneNumber().phoneNumber(),
                new CreditCard(faker.finance().creditCard(), LocalDate.now().plusYears(2), 124)));
		guestsRepository.add(new Guest(3, faker.name().fullName(), faker.address().fullAddress(), faker.address().city(), faker.phoneNumber().phoneNumber(),
                new CreditCard(faker.finance().creditCard(), LocalDate.now().plusYears(6), 547)));
		guestsRepository.add(new Guest(4, faker.name().fullName(), faker.address().fullAddress(), faker.address().city(), faker.phoneNumber().phoneNumber(),
                new CreditCard(faker.finance().creditCard(), LocalDate.now().plusYears(4), 138)));
		guestsRepository.add(new Guest(5, faker.name().fullName(), faker.address().fullAddress(), faker.address().city(), faker.phoneNumber().phoneNumber(),
                new CreditCard(faker.finance().creditCard(), LocalDate.now().plusYears(1), 114)));
		guestsRepository.add(new Guest(6, faker.name().fullName(), faker.address().fullAddress(), faker.address().city(), faker.phoneNumber().phoneNumber(),
                new CreditCard(faker.finance().creditCard(), LocalDate.now().plusYears(3), 864)));
	}
	
}
