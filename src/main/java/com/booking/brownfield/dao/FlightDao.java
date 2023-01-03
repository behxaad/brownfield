package com.booking.brownfield.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.Flight;

@Repository
public interface FlightDao extends CrudRepository<Flight, Integer> {

	@Query(value = "SELECT * FROM Flight f WHERE f.departure_id=?1 AND f.arrival_id=?2 AND f.travel_date=?3", nativeQuery = true)
	public List<Flight> findByFlight(int departuteId, int arrivalId, Date date);

	public Flight findFlightById(int flightId);

}
