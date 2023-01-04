package com.booking.brownfield.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.Booking;
@Repository
public interface BookingDao extends CrudRepository<Booking, Integer>{
	
	public Booking findByBookingNo(long bookingNo);

}
