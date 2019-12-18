package by.javacourcee.hotel;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.github.javafaker.Faker;

import by.javacourcee.hotel.entities.Room;
import by.javacourcee.hotel.repository.RoomRepository;

public class Test {

	public static void main(String[] args) {
		
		Faker faker = new Faker();
		
		System.out.println(
		RoomRepository.getAllData().stream()
				.sorted(Comparator.comparing(Room::getCodeType))
				.collect(Collectors.groupingBy(Room::getType, Collectors.toSet()))
				);

	}

}
