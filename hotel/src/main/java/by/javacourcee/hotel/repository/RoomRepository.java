package by.javacourcee.hotel.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import by.javacourcee.hotel.HouseKeepingStatus;
import by.javacourcee.hotel.RoomStatus;
import by.javacourcee.hotel.RoomType;
import by.javacourcee.hotel.entities.Room;
import by.javacourcee.hotel.interfaces.IRepository;

public class RoomRepository implements IRepository{
	
	static List<Room> roomRepository = new ArrayList<>();
	
	public static List<Room> getAllData() {
		return roomRepository;
	}
	
	public static Map<Integer, Set<Room>> getAllDataByType() {
		return getAllData().stream()
				.sorted(Comparator.comparing(Room::getCodeType).reversed())
				.collect(Collectors.groupingBy(Room::getCodeType, Collectors.toSet()));
	}
	
	public Room getById(int idRoom) {
		return roomRepository.stream().filter(e -> e.getId() == idRoom).findAny().orElse(null);
	}
	
	boolean roomExists(int idRoom){
		return (getById(idRoom)!=null);
	}
	static{
		roomRepository.add(new Room(101, RoomStatus.AVAIBLE, RoomType.SINGLE, HouseKeepingStatus.NOTCLEAN, true, 50));
		roomRepository.add(new Room(102, RoomStatus.OCCUPIED, RoomType.DOUBLE, HouseKeepingStatus.CLEAN, false, 100));
		roomRepository.add(new Room(103, RoomStatus.OCCUPIED, RoomType.SINGLE, HouseKeepingStatus.NOTCLEAN, true, 150));
		roomRepository.add(new Room(104, RoomStatus.OCCUPIED, RoomType.TRIPLE, HouseKeepingStatus.CLEAN, false, 150));
		roomRepository.add(new Room(105, RoomStatus.CHECKOUT, RoomType.SINGLE, HouseKeepingStatus.NOTCLEAN, false, 50));
		roomRepository.add(new Room(106, RoomStatus.RESERVED, RoomType.DOUBLE, HouseKeepingStatus.CLEAN, true, 100));
		roomRepository.add(new Room(107, RoomStatus.AVAIBLE, RoomType.DOUBLE, HouseKeepingStatus.CLEAN, false, 100));
		roomRepository.add(new Room(108, RoomStatus.RESERVED, RoomType.FAMILY, HouseKeepingStatus.CLEAN, false, 200));
		roomRepository.add(new Room(109, RoomStatus.AVAIBLE, RoomType.SINGLE, HouseKeepingStatus.CLEAN, false, 50));
		roomRepository.add(new Room(110, RoomStatus.RESERVED, RoomType.SINGLE, HouseKeepingStatus.NOTCLEAN, false, 50));
		roomRepository.add(new Room(111, RoomStatus.RESERVED, RoomType.SINGLE, HouseKeepingStatus.CLEAN, false, 150));
		roomRepository.add(new Room(112, RoomStatus.RESERVED, RoomType.TRIPLE, HouseKeepingStatus.CLEAN, false, 150));
		roomRepository.add(new Room(113, RoomStatus.RESERVED, RoomType.FAMILY, HouseKeepingStatus.CLEAN, false, 200));

		roomRepository.add(new Room(114, RoomStatus.AVAIBLE, RoomType.TRIPLE, HouseKeepingStatus.CLEAN, false, 200));
		roomRepository.add(new Room(115, RoomStatus.AVAIBLE, RoomType.TRIPLE, HouseKeepingStatus.CLEAN, false, 200));
	}
}
