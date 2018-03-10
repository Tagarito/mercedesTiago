package com.mercedes.tiago.mercedesproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mercedes.tiago.mercedesproject.dto.RootDTO;
import org.joda.time.DateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class MercedesProjectApplication implements CommandLineRunner {

	private final ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(MercedesProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//		objectMapper.registerModule(new JodaModule())
		byte[] initFile = Files.readAllBytes(Paths.get("src/main/resources/init.json"));
		RootDTO rootDTO = objectMapper.readValue(initFile, RootDTO.class);
		System.out.println(rootDTO);
		DateTime dateTime = new DateTime("2018-02-26T08:42:46.291");
		System.out.println(dateTime);
	}
}
