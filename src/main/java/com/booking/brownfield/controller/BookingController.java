package com.booking.brownfield.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.brownfield.entity.CreateBooking;
import com.booking.brownfield.service.BookingService;

@RestController
@RequestMapping("booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@GetMapping("/seatAvailability/{flightId}&{seatsRequired}&{date}&{classType}")
	public ResponseEntity<?> checkSeatAvailability(@PathVariable("flightId") int flightId,
			@PathVariable("seatsRequired") int seatsRequired,
			@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
			@PathVariable("classType") String classType) {
		String msg = bookingService.checkSeatAvailability(flightId, seatsRequired, date, classType);
		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> bookTicket(@RequestBody CreateBooking booking) {
		bookingService.bookTicket(booking.getBooking(), booking.getPassengers());
		return new ResponseEntity<>("Ticket Booked Successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{bookingId}")
	public ResponseEntity<?> cancelTicket(@PathVariable("bookingId") int bookingId) {
		bookingService.cancelTicket(bookingId);
		return new ResponseEntity<>("Booking Cancelled Successfully", HttpStatus.OK);
	}

	@GetMapping("/passengerInfo/{bookingNo}")
	public ResponseEntity<?> getPassengerInfo(@PathVariable("bookingNo") long bookingNo) {
		return new ResponseEntity<>(bookingService.getPassengerInfo(bookingNo), HttpStatus.FOUND);
	}

	@GetMapping("/bookingInfo/{bookingNo}")
	public ResponseEntity<?> getBookingInfo(@PathVariable("bookingNo") long bookingNo) {
		return new ResponseEntity<>(bookingService.getBookingInfo(bookingNo), HttpStatus.FOUND);
	}

}
