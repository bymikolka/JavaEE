package by.javacourcee.hotel.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import by.javacourcee.hotel.HouseKeepingStatus;
import by.javacourcee.hotel.RoomPriority;
import by.javacourcee.hotel.RoomStatus;
import by.javacourcee.hotel.RoomType;

public class Room {
	public Room() {
		// Auto-generated constructor stub
	}
    private int id;
    private RoomStatus status;
    private RoomType type;
    private HouseKeepingStatus houseKeepingStatus;
    private int houseKeeperId;
    private boolean needsRepairs;
    private double pricePerDay;
    private RoomPriority priority;
    private LocalDateTime houseKeepingStart;
    private LocalDate houseKeepingEnd;
    private boolean visible;
    
    
    public Room(int id, RoomStatus status, RoomType type, HouseKeepingStatus houseKeepingStatus, boolean needsRepairs, double price)
    {
        this.id = id;
        this.status = status;
        this.type = type;
        this.houseKeepingStatus = houseKeepingStatus;
        this.needsRepairs = needsRepairs;
        this.priority = RoomPriority.LOW;
        this.pricePerDay = price;
        this.houseKeeperId = 0;
        this.houseKeepingStart = LocalDateTime.now();
        this.houseKeepingEnd = LocalDate.now();
        this.visible = true;
    }
    
    
	public RoomStatus getStatusByBooking(LocalDate date, List<Booking> bookings) {
		Booking booking = bookings.stream().filter(e -> e.getId() == this.id).findAny().orElse(null);
		if (booking == null) {
			return RoomStatus.AVAIBLE;
		}
			if ((booking.getFrom().isBefore(date) || booking.getFrom().isEqual(date))
					&& (booking.getTo().isAfter(date) || booking.getTo().isEqual(date)))
				switch (booking.getStatus()) {
				case ACTUAL:
					return RoomStatus.OCCUPIED;
				case NOSHOW:
					return RoomStatus.AVAIBLE;
				case CHECKEDOUT:
					return RoomStatus.CHECKOUT;
				case RESERVATION:
					return RoomStatus.RESERVED;
				default:
					return RoomStatus.AVAIBLE;
				}
			return RoomStatus.AVAIBLE;

	}
    
    public RoomStatus getStatusAtDate(LocalDate date, List<Booking> bookings)
	{

		Booking booking = bookings.stream().filter(e -> e.getId() == this.id).findAny().orElse(null);
		if (booking != null) {
			if ((booking.getFrom().isBefore(date) || booking.getFrom().isEqual(date))
					&& booking.getTo().isEqual(date)) {
				return RoomStatus.CHECKOUT;
			} else if ((booking.getFrom().isBefore(date) || booking.getFrom().isEqual(date))
					&& (booking.getTo().isAfter(date) || booking.getTo().isEqual(date))) {
				return RoomStatus.OCCUPIED;
			} else {
				if (booking.getFrom().isBefore(date)) {
					return RoomStatus.RESERVED;
				}
			}
		}
		return RoomStatus.AVAIBLE;
    }
    
	public int getId() {
		return id;
	}
	public RoomStatus getStatus() {
		return status;
	}
	public RoomType getType() {
		return type;
	}
	public int getCodeType() {
		return RoomType.getCodeByType(type);
	}

	public HouseKeepingStatus getHouseKeepingStatus() {
		return houseKeepingStatus;
	}
	public int getHouseKeeperId() {
		return houseKeeperId;
	}
	public boolean isNeedsRepairs() {
		return needsRepairs;
	}
	public double getPricePerDay() {
		return pricePerDay;
	}
	public RoomPriority getPriority() {
		return priority;
	}
	public LocalDateTime getHouseKeepingStart() {
		return houseKeepingStart;
	}
	public LocalDate getHouseKeepingEnd() {
		return houseKeepingEnd;
	}
	public boolean isVisible() {
		return visible;
	}


	@Override
	public String toString() {
		return "Room [id=" + id + ", status=" + status + ", type=" + type + ", houseKeepingStatus=" + houseKeepingStatus
				+ ", houseKeeperId=" + houseKeeperId + ", needsRepairs=" + needsRepairs + ", pricePerDay=" + pricePerDay
				+ ", priority=" + priority + ", houseKeepingStart=" + houseKeepingStart + ", houseKeepingEnd="
				+ houseKeepingEnd + ", visible=" + visible + "]";
	}
	
	
}
