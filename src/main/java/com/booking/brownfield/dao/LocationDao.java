package com.booking.brownfield.dao;

import org.springframework.data.repository.CrudRepository;

import com.booking.brownfield.entity.Location;

public interface LocationDao extends CrudRepository<Location,Integer> {

}
