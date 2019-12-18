package by.javacourcee.hotel.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import by.javacourcee.hotel.BookingStatus;

public class Booking {
    private int id;
    private List<Guest> guests;
    private String name;
    private int roomId;
    private LocalDate from;
    private LocalDate to;
    private double price;
    private double pricePerDay;
    private BookingStatus status; 


    public int getId() {
		return id;
	}


	public List<Guest> getGuests() {
		return guests;
	}


	public String getName() {
		return name;
	}


	public int getRoomId() {
		return roomId;
	}


	public LocalDate getFrom() {
		return from;
	}


	public LocalDate getTo() {
		return to;
	}


	public double getPrice() {
		return price;
	}


	public double getPricePerDay() {
		return pricePerDay;
	}


	public BookingStatus getStatus() {
		return status;
	}


	public Booking(int id, Guest guest, int roomId, LocalDate from, LocalDate to, double pricePerDay, BookingStatus status)
    {
        this.id = id;
        this.guests = new ArrayList<>();
        this.guests.add(guest);
        this.name = guest.getName();
        this.roomId = roomId;
        this.from = from;
        this.to = to;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

	public BookingStatus getBookingStatusAtDate(LocalDate date, Booking booking) {
		if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.NOSHOW) {
			return booking.getStatus();
		}

		if ((booking.getFrom().isBefore(date) || booking.getFrom().isEqual(date)) && booking.getTo().isEqual(date)
				&& this.getStatus() != BookingStatus.ACTUAL) {
			return BookingStatus.CHECKEDOUT;
		} else if ((booking.getFrom().isBefore(date) || booking.getFrom().isEqual(date))
				&& (booking.getTo().isAfter(date) || booking.getTo().isEqual(date))
				&& this.getStatus() != BookingStatus.RESERVATION) {
			return BookingStatus.ACTUAL;
		} else {
			if (booking.getFrom().isAfter(date)) {
				return BookingStatus.RESERVATION;
			}
		}

		return BookingStatus.RESERVATION;
	}
 }
	
