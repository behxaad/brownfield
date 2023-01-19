package com.booking.brownfield.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Passenger implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonIgnore
	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private String mealPref;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
