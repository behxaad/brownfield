package com.booking.brownfield.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.brownfield.dao.UserDao;
import com.booking.brownfield.dto.UserDto;
import com.booking.brownfield.entity.User;
import com.booking.brownfield.exception.RecordAlreadyPresentException;
import com.booking.brownfield.exception.RecordNotFoundException;
@Service
public class UserServiceImpl implements UserService {
	
	private static final String USER_ALREADY_PRESENT = "USER ALREADY EXISTS!";
	private static final String USER_NOT_FOUND = "USER NOT FOUND";
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean addUser(UserDto userdto) {
		
		User user = new User();
		BeanUtils.copyProperties(userdto, user);
		Optional<User> userCheck = userDao.findById(user.getId());
		if(!userCheck.isPresent())
		{
			userDao.save(user);
			return true;
		}
		
		throw new RecordAlreadyPresentException(USER_ALREADY_PRESENT);
	}

	@Override
	public boolean modifyUser(UserDto userdto) {
		User user = new User();
		BeanUtils.copyProperties(userdto, user);
		Optional<User> userCheck = userDao.findById(user.getId());
		if(userCheck.isPresent())
		{
			userDao.save(user);
			return true;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

	@Override
	public boolean checkUser(String email, String password) {
		
		User userCheck = userDao.findByEmail(email);
		if(userCheck!= null && userCheck.getPassword().equals(password))
		{
			return true;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

	@Override
	public UserDto getUser(String email) {
		
		User checkUser = userDao.findByEmail(email);
		UserDto userdto = new UserDto();
		if(checkUser !=null)
		{
			BeanUtils.copyProperties(checkUser, userdto);
			return userdto;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

}
