package com.booking.brownfield.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.Boarding;
 @Repository
public interface BoardingDao extends CrudRepository<Boarding, Integer> {

	public Optional<Boarding> findByBookingNo(long bookingNo);

}
