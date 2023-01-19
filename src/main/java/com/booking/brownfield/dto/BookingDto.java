package com.booking.brownfield.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookingDto {
	@JsonIgnore
	private long bookingNo;
	private Date bookingDate;
	private Date travelDate;
	private double totalCost;
	private int seatsBooked;
	private String seatClass;
	private int flightId;

	public long getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(long bookingNo) {
		this.bookingNo = bookingNo;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public String getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(String seatClass) {
		this.seatClass = seatClass;
	}

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

}