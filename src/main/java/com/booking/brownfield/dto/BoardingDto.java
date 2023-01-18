package com.booking.brownfield.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BoardingDto {
	@JsonIgnore
	int id;
	private long bookingNo;
	@JsonIgnore
	private String seatNo;
	@JsonIgnore
	private String bagTag;
	@JsonIgnore
	private boolean checkedIn;
	@JsonIgnore
	private boolean boardingCheck;
	@JsonIgnore
	private int totalCheckedInEconomySeats;
	@JsonIgnore
	private int totalCheckedInBusinessSeats;
	@JsonIgnore
	private int totalCheckedInPremiumSeats;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(long bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getBagTag() {
		return bagTag;
	}

	public void setBagTag(String bagTag) {
		this.bagTag = bagTag;
	}

	public boolean isCheckedIn() {
		return checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}

	public boolean isBoardingCheck() {
		return boardingCheck;
	}

	public void setBoardingCheck(boolean boardingCheck) {
		this.boardingCheck = boardingCheck;
	}

	public int getTotalCheckedInEconomySeats() {
		return totalCheckedInEconomySeats;
	}

	public void setTotalCheckedInEconomySeats(int totalCheckedInEconomySeats) {
		this.totalCheckedInEconomySeats = totalCheckedInEconomySeats;
	}

	public int getTotalCheckedInBusinessSeats() {
		return totalCheckedInBusinessSeats;
	}

	public void setTotalCheckedInBusinessSeats(int totalCheckedInBusinessSeats) {
		this.totalCheckedInBusinessSeats = totalCheckedInBusinessSeats;
	}

	public int getTotalCheckedInPremiumSeats() {
		return totalCheckedInPremiumSeats;
	}

	public void setTotalCheckedInPremiumSeats(int totalCheckedInPremiumSeats) {
		this.totalCheckedInPremiumSeats = totalCheckedInPremiumSeats;
	}

}
