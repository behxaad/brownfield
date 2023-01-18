package com.booking.brownfield.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.brownfield.dao.BoardingDao;
import com.booking.brownfield.dao.BookingDao;
import com.booking.brownfield.dao.FlightDao;
import com.booking.brownfield.dto.BoardingDto;
import com.booking.brownfield.entity.Boarding;
import com.booking.brownfield.entity.Booking;
import com.booking.brownfield.entity.Flight;
import com.booking.brownfield.exception.RecordNotFoundException;

@Service
public class BoardingServiceImpl implements BoardingService {

	private static final String CHECKIN_ALREADY_DONE = "CHECKIN_ALREADY_DONE OR BOOKING NOT FOUND";
	private static final String CHECKIN_NOT_DONE = "CHECKIN_NOT_DONE OR PASSENGER_ALREADY_BOARDED";
	private static final String BOARDING_NOT_DONE = "BOARDING_NOT_DONE";

	@Autowired
	private BookingDao bookingDao;
	@Autowired
	private BoardingDao boardingDao;
	@Autowired
	private FlightDao flightDao;

	@Override
	public boolean checkIn(BoardingDto boardingDto) {

		Booking checkBooking = bookingDao.findByBookingNo(boardingDto.getBookingNo());
		Optional<Boarding> checkBoarding = boardingDao.findByBookingNo(boardingDto.getBookingNo());

		if (checkBooking != null && checkBoarding.isEmpty()) {

			Optional<Flight> checkFlight = flightDao.findById(checkBooking.getFlightId());
			if (checkBooking.getSeatClass().equalsIgnoreCase("economy") && checkFlight.isPresent()) {
				int totalSeats = checkFlight.get().getRemainingEconomySeats();
				totalSeats -= boardingDto.getTotalCheckedInEconomySeats();
				boardingDto.setSeatNo("" + totalSeats + "E");
				boardingDto.setBagTag("" + totalSeats + "E" + checkBooking.getFlightId());
				boardingDto.setCheckedIn(true);
				boardingDto.setTotalCheckedInEconomySeats(boardingDto.getTotalCheckedInEconomySeats()+1);
			}

			else if (checkBooking.getSeatClass().equalsIgnoreCase("business") && checkFlight.isPresent()) {
				int totalSeats = checkFlight.get().getRemainingBusinessSeats();
				totalSeats -= boardingDto.getTotalCheckedInBusinessSeats();
				boardingDto.setSeatNo("" + totalSeats + "E");
				boardingDto.setBagTag("" + totalSeats + "E" + checkBooking.getFlightId());
				boardingDto.setCheckedIn(true);
				boardingDto.setTotalCheckedInBusinessSeats(boardingDto.getTotalCheckedInBusinessSeats()+1);

			}

			else if (checkBooking.getSeatClass().equalsIgnoreCase("premium") && checkFlight.isPresent()) {
				int totalSeats = checkFlight.get().getRemainingPremiumSeats();
				totalSeats -= boardingDto.getTotalCheckedInEconomySeats();
				boardingDto.setSeatNo("" + totalSeats + "E");
				boardingDto.setBagTag("" + totalSeats + "E" + checkBooking.getFlightId());
				boardingDto.setCheckedIn(true);
				boardingDto.setTotalCheckedInPremiumSeats(boardingDto.getTotalCheckedInPremiumSeats()+1);
			}
			Boarding dtoToObject = new Boarding();
			BeanUtils.copyProperties(boardingDto, dtoToObject);
			boardingDao.save(dtoToObject);
			return true;

		}

		throw new RecordNotFoundException(CHECKIN_ALREADY_DONE);
	}

	public boolean boardingStatus(long bookingNo) {
		Optional<Boarding> checkBoarding = boardingDao.findByBookingNo(bookingNo);

		if (checkBoarding.isPresent() && checkBoarding.get().isCheckedIn() && !checkBoarding.get().isBoardingCheck()) {
			checkBoarding.get().setBoardingCheck(true);
			boardingDao.save(checkBoarding.get());
			return true;
		}

		throw new RecordNotFoundException(CHECKIN_NOT_DONE);
	}
	
	public BoardingDto boardingPass(long bookingNo)
	{
		Optional<Boarding> checkBoarding = boardingDao.findByBookingNo(bookingNo);
		if(checkBoarding.isPresent())
		{
			BoardingDto objectToDto = new BoardingDto();
			BeanUtils.copyProperties(checkBoarding,objectToDto);
			return  objectToDto;
		}
		
		throw new RecordNotFoundException(BOARDING_NOT_DONE);
	}

}
