package com.booking.brownfield.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.brownfield.dao.BookingDao;
import com.booking.brownfield.dao.FlightDao;
import com.booking.brownfield.dao.PassengerDao;
import com.booking.brownfield.dto.BookingDto;
import com.booking.brownfield.dto.PassengerDto;
import com.booking.brownfield.entity.Booking;
import com.booking.brownfield.entity.Flight;
import com.booking.brownfield.entity.Passenger;
import com.booking.brownfield.exception.RecordNotFoundException;

@Service
public class BookingServiceImpl implements BookingService {

	private static String TICKETS_NOT_AVAILABLE = "TICKETS NOT AVAILABLE";
	private static String SEATS_AVAILABLE = "SEATS ARE AVAILABLE ";
	private static String SORRY = "SORRY ";
	private static final String FLIGHT_NOT_FOUND = "FLIGHT NOT FOUND";
	private static final String BOOKING_NOT_FOUND = "BOOKING NOT FOUND";

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private PassengerDao passengerDao;
	@Autowired
	private BookingDao bookingDao;

	@Override
	public String checkSeatAvailability(int flightId, int seatsRequired, Date date, String classType) {
		Optional<Flight> flightCheck = flightDao.findById(flightId);

		if (flightCheck.isPresent()) {
			if (classType.equalsIgnoreCase("economy")) {
				if (flightCheck.get().getRemainingEconomySeats() >= seatsRequired) {
					return SEATS_AVAILABLE;
				}

				return SORRY + flightCheck.get().getRemainingEconomySeats() + " " + SEATS_AVAILABLE;
			} else if (classType.equalsIgnoreCase("business")) {
				if (flightCheck.get().getRemainingBusinessSeats() >= seatsRequired) {
					return SEATS_AVAILABLE;
				}

				return SORRY + flightCheck.get().getRemainingBusinessSeats() + " " + SEATS_AVAILABLE;
			} else if (classType.equalsIgnoreCase("premium")) {
				if (flightCheck.get().getRemainingPremiumSeats() >= seatsRequired) {
					return SEATS_AVAILABLE;
				}

				return SORRY + flightCheck.get().getRemainingPremiumSeats() + " " + SEATS_AVAILABLE;
			}

		}
		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);
	}

	@Override
	public boolean bookTicket(Booking booking, List<Passenger> passengers) {

		Optional<Flight> checkFlight = flightDao.findById(booking.getFlightId());

		if (updateSeat(booking.getFlightId(), booking.getSeatsBooked(), booking.getSeatClass())
				&& checkFlight.isPresent()) {
			Booking newBooking = booking;
			List<Passenger> newPassenger = passengers;

			for (int i = 0; i < passengers.size(); i++) {
				passengerDao.save(newPassenger.get(i));
			}

			bookingDao.save(newBooking);

			return true;

		}
		throw new RecordNotFoundException(TICKETS_NOT_AVAILABLE);
	}

	@Override
	public boolean cancelTicket(int bookingId) {
		Optional<Booking> bookingCheck = bookingDao.findById(bookingId);
		if (bookingCheck.isPresent()) {
			Flight flight = flightDao.findFlightById(bookingCheck.get().getFlightId());
			List<Passenger> passenger = passengerDao.findByBookingNo((int) bookingCheck.get().getBookingNo());

			String classType = bookingCheck.get().getSeatClass();
			int seat = bookingCheck.get().getSeatsBooked();

			if (classType.equalsIgnoreCase("economy")) {
				flight.setRemainingEconomySeats(flight.getRemainingEconomySeats() + seat);
			}

			if (classType.equalsIgnoreCase("business")) {
				flight.setRemainingBusinessSeats(flight.getRemainingBusinessSeats() + seat);
			}

			if (classType.equalsIgnoreCase("premium")) {
				flight.setRemainingPremiumSeats(flight.getRemainingPremiumSeats() + seat);
			}

			flightDao.save(flight);
			passengerDao.deleteAll(passenger);
			bookingDao.deleteById(bookingId);
			return true;

		}

		throw new RecordNotFoundException(BOOKING_NOT_FOUND);
	}

	@Override
	public boolean updateSeat(int flightId, int seat, String classType) {
		Optional<Flight> flightCheck = flightDao.findById(flightId);
		if (flightCheck.isPresent()) {
			if (classType.equalsIgnoreCase("economy")) {
				if (flightCheck.get().getRemainingEconomySeats() >= seat) {
					flightCheck.get().setRemainingEconomySeats(flightCheck.get().getRemainingEconomySeats() - seat);
					return true;
				}

				return false;
			}

			else if (classType.equalsIgnoreCase("business")) {
				if (flightCheck.get().getRemainingBusinessSeats() >= seat) {
					flightCheck.get().setRemainingBusinessSeats(flightCheck.get().getRemainingBusinessSeats() - seat);
					return true;
				}
				return false;
			}

			else if (classType.equalsIgnoreCase("premium")) {
				if (flightCheck.get().getRemainingPremiumSeats() >= seat) {
					flightCheck.get().setRemainingPremiumSeats(flightCheck.get().getRemainingPremiumSeats() - seat);
					return true;

				}
				return false;
			}

			flightDao.save(flightCheck.get());
		}

		return false;
	}

	public BookingDto getBookingInfo(long bookingNo) {
		Booking bookingCheck = bookingDao.findByBookingNo(bookingNo);
		if (bookingCheck != null) {
			BookingDto bookingDto = new BookingDto();
			BeanUtils.copyProperties(bookingCheck, bookingDto);
			return bookingDto;
		}

		throw new RecordNotFoundException(BOOKING_NOT_FOUND);
	}

	public List<PassengerDto> getPassengerInfo(long bookingNo) {
		List<Passenger> passengerCheck = passengerDao.findByBookingNo(bookingNo);
		if (!passengerCheck.isEmpty()) {
			ModelMapper mapper = new ModelMapper();
			return Arrays.asList(mapper.map(passengerCheck, PassengerDto[].class));

		}

		throw new RecordNotFoundException(BOOKING_NOT_FOUND);
	}

}