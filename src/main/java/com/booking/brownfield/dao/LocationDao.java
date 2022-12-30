package com.booking.brownfield.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.Location;

@Repository
public interface LocationDao extends CrudRepository<Location,Integer> {
	
	@Query(value="SELECT * FROM Location l where l.name=?1", nativeQuery = true) //Not Necessary to use
	public Location findByName(@Param("name") String name);

}
