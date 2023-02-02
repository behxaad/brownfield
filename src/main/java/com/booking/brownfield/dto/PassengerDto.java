package com.booking.brownfield.dto;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PassengerDto {

	@JsonIgnore
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	@NotEmpty
	private String gender;
	@JsonInclude(Include.NON_EMPTY)
	private String mealPref;
	@JsonInclude(Include.NON_EMPTY)
	private String passportNumber;
	@JsonIgnore
	private long bookingNo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMealPref() {
		return mealPref;
	}

	public void setMealPref(String mealPref) {
		this.mealPref = mealPref;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public long getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(long bookingNo) {
		this.bookingNo = bookingNo;
	}

}
