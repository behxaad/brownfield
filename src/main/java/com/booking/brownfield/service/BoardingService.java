package com.booking.brownfield.service;

import com.booking.brownfield.dto.BoardingDto;
import com.booking.brownfield.dto.BoardingDtoPass;

public interface BoardingService {
	
	public boolean checkIn(BoardingDto boardingDto);
	public boolean boardingStatus(long bookingNo);
	public BoardingDtoPass boardingPass(long bookingNo);

}
