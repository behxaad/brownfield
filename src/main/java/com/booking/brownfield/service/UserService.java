package com.booking.brownfield.service;

import org.springframework.stereotype.Service;

import com.booking.brownfield.dto.UserDto;
@Service
public interface UserService {
	
	public boolean addUser(UserDto userdto);
	public boolean modifyUser(UserDto userdto);
	public boolean checkUser(String email, String password);
	public boolean checkAdmin(String userName, String password);
	public UserDto getUser(String email);

}
