package com.booking.brownfield.service;

import com.booking.brownfield.dto.BoardingDto;

public interface BoardingService {
	
	public boolean checkIn(BoardingDto boardingDto);
	public boolean boardingStatus(long bookingNo);
	public BoardingDto boardingPass(long bookingNo);

}
