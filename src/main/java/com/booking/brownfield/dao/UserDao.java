package com.booking.brownfield.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.booking.brownfield.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	public User findByEmail(@Param("email") String email);
	public Optional<User> findByUserName(String userName);
	public Optional<User> findByContact(long mobileNumber);

}
