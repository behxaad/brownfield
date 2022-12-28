package com.booking.brownfield.dao;

import org.springframework.data.repository.CrudRepository;

import com.booking.brownfield.entity.Flight;

public interface FlightDao extends CrudRepository<Flight, Integer> {

}
