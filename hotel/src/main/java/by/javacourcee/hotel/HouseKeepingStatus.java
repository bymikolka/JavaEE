package by.javacourcee.hotel;

public enum HouseKeepingStatus {
    CLEAN(1, "Clean"),
    NOTCLEAN(2, "Not Clean"),
    INPROGRESS(3, "In progress");
    
	private int hkStatus;
	private String stateStatus;
	

    public String getStateStatus() {
		return stateStatus;
	}

    
	private HouseKeepingStatus(int hkstate, String state) {
		this.hkStatus = hkstate;
		this.stateStatus = state;
	}

	public int getHkStatus() {
		return hkStatus;
	}
    public static String getStateStatus(HouseKeepingStatus state) {
		return state.getStateStatus();
	}
	
}
