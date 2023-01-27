package com.booking.brownfield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.brownfield.dto.BoardingDto;
import com.booking.brownfield.service.BoardingService;

@RestController
@RequestMapping("/boarding")
public class BoardingController {

	@Autowired
	private BoardingService boardingService;

	@PostMapping("/checkIn")
	public ResponseEntity<?> checkIn(@RequestBody BoardingDto boardingDto) {
		boardingService.checkIn(boardingDto);

		return new ResponseEntity<>("CheckIn Done", HttpStatus.OK);
	}

	@GetMapping("/boardingCheck/{bookingNo}")

	public ResponseEntity<?> boardingStatus(@PathVariable("bookingNo") long bookingNo) {
		boardingService.boardingStatus(bookingNo);
		return new ResponseEntity<>("Passenger Boarded", HttpStatus.OK);
	}

	@GetMapping("/boardingPass/{bookingNo}")
	public ResponseEntity<?> boardingPass(@PathVariable("bookingNo") long bookingNo) {

		return new ResponseEntity<>(boardingService.boardingPass(bookingNo), HttpStatus.OK);
	}

}
