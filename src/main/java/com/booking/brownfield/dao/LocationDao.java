package com.booking.brownfield.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.Location;

@Repository
public interface LocationDao extends CrudRepository<Location, Integer> {

//	@Query(value = "SELECT * FROM Location l WHERE l.name=?1 INNER JOIN FLIGHT f ON l.id=f.departure_id", nativeQuery = true)
//	public Location findByDepartureName(@Param("name") String name);

//	@Query(value = "SELECT * FROM Location l WHERE l.name=?1 INNER JOIN FLIGHT f ON l.id=f.arrival_id", nativeQuery = true)
//	public Location findByArrivalName(@Param("name") String name);

	public Location findByName(@Param("name") String name);

//	@Query(value = "SELECT id FROM Location l INNER JOIN Flight f ON l.id=f.id", nativeQuery = true)
//	public Location getIdDA();

}
