package by.javacourcee.hotel;

public enum BookingStatus {
    		RESERVATION(1, "Reservation"),
            ACTUAL(2, "Actual"),
            CANCELLED(3, "Cancelled"),
            CHECKEDOUT(4, "Checked out"),
            NOSHOW(5, "No-show");
	
	private int bookingStatusType;
	private String bookingStatusState;
	
	 private BookingStatus(int bookingStatus, String bookingStatusState) {
			this.bookingStatusType = bookingStatus;
			this.bookingStatusState = bookingStatusState;
		}

		public int getBookingStatusType() {
		return bookingStatusType;
	}

	public String getBookingStatusState() {
		return bookingStatusState;
	}

		public int getBookingStatus() {
			return bookingStatusType;
		}
	
}
