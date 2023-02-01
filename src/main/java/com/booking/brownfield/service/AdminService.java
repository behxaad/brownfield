package com.booking.brownfield.service;

import java.util.List;

import com.booking.brownfield.dto.FlightDto;

public interface AdminService {

	public boolean addFlight(FlightDto flightdto);

	public List<FlightDto> getAllFlights();

	public boolean deleteFlight(int flightId);

	public boolean modifyFlight(FlightDto flightdto);
	
	public long totalRevenue();
}
