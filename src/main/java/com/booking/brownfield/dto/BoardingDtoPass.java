package com.booking.brownfield.dto;

public class BoardingDtoPass {

	private long bookingNo;
	private String seatNo;
	private String bagTag;
	private int flightId;

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

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

}
