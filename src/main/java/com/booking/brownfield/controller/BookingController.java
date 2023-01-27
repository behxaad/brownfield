package com.booking.brownfield.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/seatAvailability/{flightId}&{seatsRequired}&{classType}")
	public ResponseEntity<?> checkSeatAvailability(@PathVariable("flightId") int flightId,
			@PathVariable("seatsRequired") int seatsRequired, @PathVariable("classType") String classType) {
		String msg = bookingService.checkSeatAvailability(flightId, seatsRequired, classType);
		return new ResponseEntity<>(msg, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> bookTicket(@RequestBody CreateBooking booking) {
		long bookingNo = bookingService.bookTicket(booking.getBooking(), booking.getPassengers());
		return new ResponseEntity<>("Ticket Booked Successfully, Booking No: " + bookingNo, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{bookingNo}")
	public ResponseEntity<?> cancelTicket(@PathVariable("bookingNo") long bookingNo) {
		bookingService.cancelTicket(bookingNo);
		return new ResponseEntity<>("Booking Cancelled Successfully", HttpStatus.OK);
	}

	@GetMapping("/passengerInfo/{bookingNo}")
	public ResponseEntity<?> getPassengerInfo(@PathVariable("bookingNo") long bookingNo) {
		return new ResponseEntity<>(bookingService.getPassengerInfo(bookingNo), HttpStatus.OK);
	}

	@GetMapping("/bookingInfo/{bookingNo}")
	public ResponseEntity<?> getBookingInfo(@PathVariable("bookingNo") long bookingNo) {
		return new ResponseEntity<>(bookingService.getBookingInfo(bookingNo), HttpStatus.OK);
	}

}
