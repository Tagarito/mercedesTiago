package com.mercedes.tiago.mercedesproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.mercedes.tiago.mercedesproject.dto.DealerDTO;
import com.mercedes.tiago.mercedesproject.dto.RootDTO;
import com.mercedes.tiago.mercedesproject.service.BookingService;
import com.mercedes.tiago.mercedesproject.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class MercedesProjectApplication implements CommandLineRunner {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private DealerService dealerService;

	@Autowired
	private BookingService bookingService;

	public static void main(String[] args) {
		SpringApplication.run(MercedesProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		//objectMapper.findAndRegisterModules();
		if(args.length==0){
			return;
		}
		String filename = args[0];
		System.out.println(filename);
		if(filename == null || filename.contains("/")){
			return;
		}
		objectMapper.registerModule(new JodaModule());
		byte[] initFile = Files.readAllBytes(Paths.get("src/main/resources/"+filename));
		RootDTO rootDTO = objectMapper.readValue(initFile, RootDTO.class);
		dealerService.addDealers(rootDTO.getDealers());
		bookingService.addBookings(rootDTO.getBookings());
//		System.out.println(rootDTO.getDealers());
//		System.out.println(rootDTO.getBookings());
//		System.out.println(rootDTO.getBookings().size());
		System.out.println("Invalid Days: " + BookingService.invalidDay);
		System.out.println("Possible Hours: "+BookingService.possibleHours);
		System.out.println("Invalid Hours: "+BookingService.invalidHours);
		System.out.println("Duplicated day/hour: "+BookingService.duplicateTimes);
	}
}
