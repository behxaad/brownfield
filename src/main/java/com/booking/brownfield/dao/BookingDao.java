package com.booking.brownfield.dao;

import org.springframework.data.repository.CrudRepository;

import com.booking.brownfield.entity.Booking;

public interface BookingDao extends CrudRepository<Booking, Integer>{
	
	public Booking findByBookingNo(long bookingNo);

}
