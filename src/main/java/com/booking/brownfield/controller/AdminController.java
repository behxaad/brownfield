package com.booking.brownfield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.brownfield.dto.FlightDto;
import com.booking.brownfield.service.AdminService;
import com.booking.brownfield.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public ResponseEntity<?> addFlight(@RequestBody FlightDto flightdto) {
		adminService.addFlight(flightdto);
		return new ResponseEntity<>("Flight Added Successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{flightId}")
	public ResponseEntity<?> deleteFlight(@PathVariable("flightId") int flightId) {
		adminService.deleteFlight(flightId);
		return new ResponseEntity<>("Flight Deleted", HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<?> gellAllFlights() {
		return new ResponseEntity<>(adminService.getAllFlights(), HttpStatus.FOUND);
	}

	@PutMapping("/modify")
	public ResponseEntity<?> modifyFlight(@RequestBody FlightDto flightdto) {
		adminService.modifyFlight(flightdto);
		return new ResponseEntity<>("Flight Details Updated", HttpStatus.OK);
	}

	@GetMapping("/totalBookingsRevenue")
	public ResponseEntity<?> totalrevenue() {
		return new ResponseEntity<>(adminService.totalRevenue(), HttpStatus.OK);
	}

	@GetMapping("/login/{userName}&{password}")
	public ResponseEntity<?> login(@PathVariable("userName") String userName, @PathVariable String password) {
		String loginMsg = "";
		if (userService.checkAdmin(userName, password)) {
			loginMsg = "Admin Logged In";
		}
		return new ResponseEntity<>(loginMsg, HttpStatus.ACCEPTED);

	}

}
