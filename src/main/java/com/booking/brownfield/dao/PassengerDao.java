package com.booking.brownfield.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.booking.brownfield.entity.Passenger;

public interface PassengerDao extends CrudRepository<Passenger, Integer>{
	
	public List<Passenger> findByBookingNo(long bookingNo);

}
