package com.booking.brownfield.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.brownfield.dao.BoardingDao;
import com.booking.brownfield.dao.BookingDao;
import com.booking.brownfield.dao.FlightDao;
import com.booking.brownfield.dto.BoardingDto;
import com.booking.brownfield.dto.BoardingDtoPass;
import com.booking.brownfield.entity.Boarding;
import com.booking.brownfield.entity.Booking;
import com.booking.brownfield.entity.Flight;
import com.booking.brownfield.exception.RecordAlreadyPresentException;
import com.booking.brownfield.exception.RecordNotFoundException;

@Service
public class BoardingServiceImpl implements BoardingService {

	private static final String CHECKIN_ALREADY_DONE = "CHECKIN ALREADY DONE";
	private static final String BOOKING_NOT_FOUND = "BOOKING NOT FOUND";
	private static final String CHECKIN_NOT_DONE = "CHECKIN NOT DONE";
	private static final String BOARDING_ALREADY_DONE = "PASSENGER ALREADY BOARDED";
	private static final String BOARDING_NOT_DONE = "BOARDING NOT DONE";

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

		if (checkBooking != null) {
			if (checkBoarding.isEmpty()) {

				Optional<Flight> checkFlight = flightDao.findById(checkBooking.getFlightId());
				if (checkBooking.getSeatClass().equalsIgnoreCase("economy") && checkFlight.isPresent()) {
					int totalSeats = checkFlight.get().getRemainingEconomySeats();
					totalSeats -= boardingDto.getTotalCheckedInEconomySeats();
					boardingDto.setSeatNo("" + totalSeats + "E");
					boardingDto.setBagTag("" + totalSeats + "E" + checkBooking.getFlightId());
					boardingDto.setCheckedIn(true);
					boardingDto.setTotalCheckedInEconomySeats(boardingDto.getTotalCheckedInEconomySeats() + 1);
				}

				else if (checkBooking.getSeatClass().equalsIgnoreCase("business") && checkFlight.isPresent()) {
					int totalSeats = checkFlight.get().getRemainingBusinessSeats();
					totalSeats -= boardingDto.getTotalCheckedInBusinessSeats();
					boardingDto.setSeatNo("" + totalSeats + "E");
					boardingDto.setBagTag("" + totalSeats + "E" + checkBooking.getFlightId());
					boardingDto.setCheckedIn(true);
					boardingDto.setTotalCheckedInBusinessSeats(boardingDto.getTotalCheckedInBusinessSeats() + 1);

				}

				else if (checkBooking.getSeatClass().equalsIgnoreCase("premium") && checkFlight.isPresent()) {
					int totalSeats = checkFlight.get().getRemainingPremiumSeats();
					totalSeats -= boardingDto.getTotalCheckedInEconomySeats();
					boardingDto.setSeatNo("" + totalSeats + "E");
					boardingDto.setBagTag("" + totalSeats + "E" + checkBooking.getFlightId());
					boardingDto.setCheckedIn(true);
					boardingDto.setTotalCheckedInPremiumSeats(boardingDto.getTotalCheckedInPremiumSeats() + 1);
				}
				Boarding dtoToObject = new Boarding();
				BeanUtils.copyProperties(boardingDto, dtoToObject);
				boardingDao.save(dtoToObject);
				return true;

			}
			throw new RecordAlreadyPresentException(CHECKIN_ALREADY_DONE);
		}

		throw new RecordNotFoundException(BOOKING_NOT_FOUND);
	}

	public boolean boardingStatus(long bookingNo) {
		Optional<Boarding> checkBoarding = boardingDao.findByBookingNo(bookingNo);

		if (checkBoarding.isPresent()) {
			if (checkBoarding.get().isCheckedIn() && !checkBoarding.get().isBoardingCheck()) {

				checkBoarding.get().setBoardingCheck(true);
				boardingDao.save(checkBoarding.get());
				return true;
			}
			throw new RecordNotFoundException(BOARDING_ALREADY_DONE);
		}

		throw new RecordNotFoundException(CHECKIN_NOT_DONE);
	}

	public BoardingDtoPass boardingPass(long bookingNo) {
		Optional<Boarding> checkBoarding = boardingDao.findByBookingNo(bookingNo);
		if (checkBoarding.isPresent()) {
			BoardingDtoPass objectToDto = new BoardingDtoPass();
			Booking checkBooking = bookingDao.findByBookingNo(bookingNo);
			objectToDto.setFlightId(checkBooking.getFlightId());
			BeanUtils.copyProperties(checkBoarding.get(), objectToDto);
			return objectToDto;
		}

		throw new RecordNotFoundException(BOARDING_NOT_DONE);
	}

}
