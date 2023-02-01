package com.booking.brownfield.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.brownfield.service.FlightService;

@RestController
@RequestMapping("/flight")
public class FlightController {

	@Autowired
	private FlightService flightService;

	@GetMapping("/search/{departure}&{arrival}&{date}")
	public ResponseEntity<?> searchFlight(@PathVariable("departure") String departure,
			@PathVariable("arrival") String arrival,
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

		return new ResponseEntity<>(flightService.searchFlight(departure, arrival, date), HttpStatus.FOUND);

	}

	@GetMapping("/fare/{flightId}&{classType}")
	public ResponseEntity<?> getFare(@PathVariable("flightId") int flightId,
			@PathVariable("classType") String classType) {

		return new ResponseEntity<>(flightService.getFare(flightId, classType), HttpStatus.FOUND);
	}

	@GetMapping("/{flightId}")
	public ResponseEntity<?> getFlight(@PathVariable("flightId") int flightId) {
		return new ResponseEntity<>(flightService.getFlight(flightId), HttpStatus.FOUND);
	}

//	@GetMapping  //WE ARE NOT DISPLAYING IT TO USER. AS IT MAKES DATA VULNERABLE
//	public ResponseEntity<?> getAllFlights() {
//		return new ResponseEntity<>(flightService.getAllFlights(), HttpStatus.OK);
//	}

}
