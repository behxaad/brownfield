package com.booking.brownfield.service;

import java.util.List;
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
	private static final String INVALID_DETAILS_ENTERED = "INVALID DETAILS ENTERED";

	@Autowired
	private UserDao userDao;

	@Override
	public boolean addUser(UserDto userdto) {
		String regex = "^[_a-zA-Z0-9]+@[a-zA-Z]+[.]{1}[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userdto.getEmail());
		if ((!userdto.getUserName().equals("")) && (!userdto.getPassword().equals(""))
				&& (!userdto.getEmail().equals("")) && !userdto.getFirstName().equals("")
				&& !userdto.getLastName().equals("")
				&& Long.toString(userdto.getContact().getMobileNumber()).length() == 10
				&& Long.toString(userdto.getContact().getZipCode()).length() == 6
				&& !userdto.getContact().getCity().equals("") && !userdto.getContact().getStreet().equals("")
				&& !userdto.getContact().getCountry().equals("") && !userdto.getContact().getState().equals("")
				&& matcher.matches()) {

			Optional<User> userCheckName = userDao.findByUserName(userdto.getUserName());
			User user = new User();
			BeanUtils.copyProperties(userdto, user);
			Optional<User> userCheck = userDao.findById(user.getId());
			System.out.println(!userdto.getUserName().equals(null));

			List<User> checkMobileNumber = (List<User>) userDao.findAll();
			Boolean mobileNumberCheck = false;
			for (int i = 0; i < checkMobileNumber.size(); i++) {
				if (checkMobileNumber.get(i).getContact().getMobileNumber() == user.getContact().getMobileNumber())
					mobileNumberCheck = true;
			}

			if (userDao.findByEmail(user.getEmail()) == null && !userCheck.isPresent() && !userCheckName.isPresent()
					&& !mobileNumberCheck) {

				userDao.save(user);
				return true;

			}

			throw new RecordAlreadyPresentException(USER_ALREADY_PRESENT);

		}

		throw new RecordAlreadyPresentException(INVALID_DETAILS_ENTERED);
	}

	@Override
	public boolean modifyUser(UserDto userdto) {
		User user = new User();
		BeanUtils.copyProperties(userdto, user);

		String regex = "^[_a-zA-Z0-9]+@[a-zA-Z]+[.]{1}[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(user.getEmail());

		Optional<User> userCheck = userDao.findById(user.getId());
		if (userCheck.isPresent()) {
			if (!userdto.getFirstName().equals("") && !userdto.getLastName().equals("")
					&& Long.toString(userdto.getContact().getMobileNumber()).length() == 10
					&& Long.toString(userdto.getContact().getZipCode()).length() == 6 && matcher.matches()
					&& !userdto.getContact().getCity().equals("") && !userdto.getContact().getStreet().equals("")
					&& !userdto.getContact().getCountry().equals("") && !userdto.getContact().getState().equals("")) {
				userDao.save(user);
				return true;
			}

			throw new RecordAlreadyPresentException(INVALID_DETAILS_ENTERED);
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);

	}

	@Override
	public boolean checkUser(String email, String password) {

		User userCheck = userDao.findByEmail(email);
		if (userCheck != null && userCheck.getPassword().equals(password) && !userCheck.getUserName().equals("admin")) {
			return true;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

	@Override //WE ARE NOT DISPLAYING IT TO USER. AS IT MAKES DATA VULNERABLE
	public UserDto getUser(String email) {

		User checkUser = userDao.findByEmail(email);
		UserDto userdto = new UserDto();
		if (checkUser != null) {
			BeanUtils.copyProperties(checkUser, userdto);
			return userdto;
		}

		throw new RecordNotFoundException(USER_NOT_FOUND);
	}

	@Override
	public boolean checkAdmin(String userName, String password) {
		Optional<User> checkAdmin = userDao.findByUserName(userName);
		if (checkAdmin.isPresent() && checkAdmin.get().getPassword().equals(password)) {

			return true;

		}
		throw new RecordNotFoundException("Admin Credentials Invalid");
	}

}