package com.booking.brownfield.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.Passenger;
@Repository
public interface PassengerDao extends CrudRepository<Passenger, Integer>{
	
	public List<Passenger> findByBookingNo(long bookingNo);

}
