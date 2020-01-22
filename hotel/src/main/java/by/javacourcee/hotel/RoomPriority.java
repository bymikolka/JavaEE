package by.javacourcee.hotel;

public enum RoomPriority {
    		LOW(1),
            MEDIUM(2),
            HIGH(3);

	private int roomPriorityState;
	
    private RoomPriority(int roomPriority) {
		this.roomPriorityState = roomPriority;
	}

	public int getRoomPriority() {
		return roomPriorityState;
	}

	
}
