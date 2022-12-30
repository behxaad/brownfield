package com.booking.brownfield.service;

import java.util.Date;
import java.util.List;

import com.booking.brownfield.dto.FlightDto;

public interface FlightService {
	
	public List<FlightDto> searchFlight(String departure, String arrival, Date date );
	public double getFare(int flightId, String classType);
	public FlightDto getFlight(int flightId);
	public List<FlightDto> getAllFlights();

}
