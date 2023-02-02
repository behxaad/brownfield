package com.booking.brownfield.service;

import java.util.Arrays;
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

	private static String TICKETS_NOT_AVAILABLE = "APOLOGIES! TICKETS ARE NOT AVAILABLE";
	private static String SEATS_AVAILABLE = "SEATS ARE AVAILABLE ";
	private static String SORRY = "SORRY! ";
	private static final String FLIGHT_NOT_FOUND = "FLIGHT NOT FOUND";
	private static final String BOOKING_NOT_FOUND = "BOOKING NOT FOUND";
	private int totalFare = 0;

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private PassengerDao passengerDao;
	@Autowired
	private BookingDao bookingDao;

	@Override
	public String checkSeatAvailability(int flightId, int seatsRequired, String classType) {
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
	public Long bookTicket(Booking booking, List<Passenger> passengers) {

		Optional<Flight> checkFlight = flightDao.findById(booking.getFlightId());
		if (checkFlight.isPresent()) {

			if (updateSeat(booking.getFlightId(), passengers.size(), booking.getSeatClass())) {
				Booking newBooking = booking;
				List<Passenger> newPassenger = passengers;
				newBooking.setTotalCost(totalFare);
				newBooking.setSeatsBooked(passengers.size());
				newBooking.setTravelDate(checkFlight.get().getTravelDate());
				Booking b = bookingDao.save(newBooking);

				for (int i = 0; i < passengers.size(); i++) {
					newPassenger.get(i).setBookingNo(b.getBookingNo());

					if (newPassenger.get(i).getFirstName().equals("") || newPassenger.get(i).getLastName().equals("")) {
						throw new RecordNotFoundException("PLEASE FILL FIRST/LAST NAME");

					} else if (newPassenger.get(i).getAge() <= 0) {
						throw new RecordNotFoundException("PLEASE ENTER CORRECT AGE");

					} else
						passengerDao.save(newPassenger.get(i));
				}

				return newBooking.getBookingNo();

			}
			throw new RecordNotFoundException(TICKETS_NOT_AVAILABLE);
		}

		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);

	}

	@Override
	public boolean cancelTicket(long bookingNo) {
		Booking bookingCheck = bookingDao.findByBookingNo(bookingNo);
		if (bookingCheck != null) {
			Flight flight = flightDao.findFlightById(bookingCheck.getFlightId());
			List<Passenger> passenger = passengerDao.findByBookingNo(bookingCheck.getBookingNo());

			String classType = bookingCheck.getSeatClass();
			int seat = bookingCheck.getSeatsBooked();

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
			bookingDao.deleteById(bookingCheck.getBookingNo());
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
					totalFare = flightCheck.get().getFare().getEconomyFare() * seat;
					return true;
				}

				return false;
			}

			else if (classType.equalsIgnoreCase("business")) {
				if (flightCheck.get().getRemainingBusinessSeats() >= seat) {
					flightCheck.get().setRemainingBusinessSeats(flightCheck.get().getRemainingBusinessSeats() - seat);
					totalFare = flightCheck.get().getFare().getBusinessFare() * seat;
					return true;
				}
				return false;
			}

			else if (classType.equalsIgnoreCase("premium")) {
				if (flightCheck.get().getRemainingPremiumSeats() >= seat) {
					flightCheck.get().setRemainingPremiumSeats(flightCheck.get().getRemainingPremiumSeats() - seat);
					totalFare = flightCheck.get().getFare().getPremiumFare() * seat;
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