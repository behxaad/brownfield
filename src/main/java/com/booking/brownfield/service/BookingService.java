package com.booking.brownfield.service;

import java.util.List;

import com.booking.brownfield.dto.BookingDto;
import com.booking.brownfield.dto.PassengerDto;
import com.booking.brownfield.entity.Booking;
import com.booking.brownfield.entity.Passenger;

public interface BookingService {

	public String checkSeatAvailability(int flightId, int seatsRequired, String classType);

	public Long bookTicket(Booking booking, List<Passenger> passengers);

	public boolean cancelTicket(long bookingNo);

	public boolean updateSeat(int flightMasterId, int seat, String classType);

	public BookingDto getBookingInfo(long bookingNo);

	public List<PassengerDto> getPassengerInfo(long bookingNo);

}
