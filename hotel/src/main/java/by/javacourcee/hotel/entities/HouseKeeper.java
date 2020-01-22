package by.javacourcee.hotel.entities;

public class HouseKeeper {
	 private int id;
     private String name;

     public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public HouseKeeper(int id, String name)
     {
         this.id = id;
         this.name = name;
     }
}
