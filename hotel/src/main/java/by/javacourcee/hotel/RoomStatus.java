package by.javacourcee.hotel;

public enum RoomStatus {
    RESERVED("Reserved"),
    OCCUPIED("Occupied"),
    AVAIBLE("Available"),
    CHECKOUT("Checked-Out");
	
	
	private String roomStatusState;
	
	private RoomStatus(String roomState) {
		this.roomStatusState = roomState;
	}
    
    public String getRoomStatus(RoomStatus roomStatus) {
		return roomStatus.roomStatusState;
	}
    
}
