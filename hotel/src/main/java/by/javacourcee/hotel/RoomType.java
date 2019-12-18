package by.javacourcee.hotel;

public enum RoomType {
    SINGLE(1, "Single"),
    DOUBLE(2, "Double"),
    TRIPLE(3, "Triple"),
    FAMILY(4, "Family");
	
	int code;
	public int getCode() {
		return code;
	}

	private String type;

	public String getRoomType() {
		return type;
	}

	private RoomType(int code, String type) {
		this.type = type;
		this.code = code;
	}

	 public static String getRoomType(RoomType type) {
			return type.getRoomType();
		}
		
	 public static int getCodeByType(RoomType type) {
			return type.getCode();
		}
	
}
