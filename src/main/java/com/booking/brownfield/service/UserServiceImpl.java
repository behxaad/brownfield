package com.booking.brownfield.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if ((!userdto.getUserName().equals("")) && (!userdto.getPassword().equals(""))
				&& (!userdto.getEmail().equals(""))) {
			Optional<User> userCheckName = userDao.findByUserName(userdto.getUserName());
			User user = new User();
			BeanUtils.copyProperties(userdto, user);
			Optional<User> userCheck = userDao.findById(user.getId());
			System.out.println(!userdto.getUserName().equals(null));

			String regex = "^[_a-zA-Z0-9]+@[a-zA-Z]+[.]{1}[a-zA-Z]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(user.getEmail());

			if (userDao.findByEmail(user.getEmail()) == null && matcher.matches() && !userCheck.isPresent()
					&& !userCheckName.isPresent()) {

				userDao.save(user);
				return true;

			}

		}

		else {
			throw new RecordAlreadyPresentException("some fields are required");
		}

		throw new RecordAlreadyPresentException(USER_ALREADY_PRESENT);
	}

	@Override
	public boolean modifyUser(UserDto userdto) {
		User user = new User();
		BeanUtils.copyProperties(userdto, user);
		Optional<User> userCheck = userDao.findById(user.getId());
		if (userCheck.isPresent()) {
			userDao.save(user);
			return true;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

	@Override
	public boolean checkUser(String email, String password) {

		User userCheck = userDao.findByEmail(email);
		if (userCheck != null && userCheck.getPassword().equals(password)) {
			return true;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

	@Override
	public UserDto getUser(String email) {

		User checkUser = userDao.findByEmail(email);
		UserDto userdto = new UserDto();
		if (checkUser != null) {
			BeanUtils.copyProperties(checkUser, userdto);
			return userdto;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

}