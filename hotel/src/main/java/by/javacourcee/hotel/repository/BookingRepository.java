package by.javacourcee.hotel.repository;

import java.util.ArrayList;
import java.util.List;

import by.javacourcee.hotel.entities.Booking;
import by.javacourcee.hotel.interfaces.IRepository;

public class BookingRepository implements IRepository {

	static List<Booking> bookingRepository = new ArrayList<Booking>();
	
	
	
	public Booking getById(int id) {
		return bookingRepository.stream().filter(e -> e.getId() == id).findAny().orElse(null);
	}

	public static List<Booking> getAllData() {
		
		return bookingRepository;
	}

	
}
