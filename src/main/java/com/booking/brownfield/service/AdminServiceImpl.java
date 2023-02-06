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
import com.booking.brownfield.dao.LocationDao;
import com.booking.brownfield.dto.FlightDto;
import com.booking.brownfield.entity.Booking;
import com.booking.brownfield.entity.Flight;
import com.booking.brownfield.exception.RecordAlreadyPresentException;
import com.booking.brownfield.exception.RecordNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {

	private static final String FLIGHT_ALREADY_PRESENT = "FLIGHT ALREADY EXISTS!";
	private static final String INVALID_DETAILS_ENTERED = "INVALID DETAILS ENTERED";
	private static final String FLIGHT_NOT_FOUND = "FLIGHT NOT FOUND";

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private BookingDao bookingDao;

	@Override
	public boolean addFlight(FlightDto flightdto) {
		Flight flight = new Flight();
		BeanUtils.copyProperties(flightdto, flight);
		Optional<Flight> checkFlight = flightDao.findById(flight.getId());
		if (!checkFlight.isPresent()) {
			if (flight.getDepartureTime() != null && flight.getArrivalTime() != null && flight.getTravelDate() != null
					&& flight.getFare().getBusinessFare() >= 1 && flight.getFare().getEconomyFare() >= 1
					&& flight.getFare().getPremiumFare() >= 1 && flight.getFleet().getTotalEconomySeats() >= 1
					&& flight.getFleet().getTotalBusinessSeats() >= 1 && flight.getFleet().getTotalPremiumSeats() >= 1
					&& flight.getRemainingEconomySeats() <= flight.getFleet().getTotalEconomySeats()
					&& flight.getRemainingBusinessSeats() <= flight.getFleet().getTotalBusinessSeats()
					&& flight.getRemainingPremiumSeats() <= flight.getFleet().getTotalPremiumSeats()) {

				if (locationDao.findByName(flight.getDepartureLocation().getName()) != null) {
					flight.setDepartureLocation(locationDao.findByName(flight.getDepartureLocation().getName()));
				}

				if (locationDao.findByName(flight.getArrivalLocation().getName()) != null) {
					flight.setArrivalLocation(locationDao.findByName(flight.getArrivalLocation().getName()));
				}
				flightDao.save(flight);
				return true;
			}
			throw new RecordAlreadyPresentException(INVALID_DETAILS_ENTERED);
		}

		throw new RecordAlreadyPresentException(FLIGHT_ALREADY_PRESENT);
	}

	@Override
	public List<FlightDto> getAllFlights() {

		ModelMapper mapper = new ModelMapper();
		List<Flight> flightList = (List<Flight>) flightDao.findAll();
		return Arrays.asList(mapper.map(flightList, FlightDto[].class));
	}

	@Override
	public boolean deleteFlight(int flightId) {
		Optional<Flight> checkFlight = flightDao.findById(flightId);
		if (checkFlight.isPresent()) {
			checkFlight.get().setArrivalLocation(null);
			checkFlight.get().setDepartureLocation(null);
			flightDao.save(checkFlight.get());
			flightDao.deleteById(flightId);
			return true;
		}

		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);
	}

	@Override
	public boolean modifyFlight(FlightDto flightdto) {

		Flight flight = new Flight();
		BeanUtils.copyProperties(flightdto, flight);
		Optional<Flight> checkFlight = flightDao.findById(flight.getId());
		if (checkFlight.isPresent()) {
			if (flight.getDepartureTime() != null && flight.getArrivalTime() != null && flight.getTravelDate() != null
					&& flight.getFare().getBusinessFare() >= 1 && flight.getFare().getEconomyFare() >= 1
					&& flight.getFare().getPremiumFare() >= 1 && flight.getFleet().getTotalEconomySeats() >= 1
					&& flight.getFleet().getTotalBusinessSeats() >= 1 && flight.getFleet().getTotalPremiumSeats() >= 1
					&& flight.getRemainingEconomySeats() <= flight.getFleet().getTotalEconomySeats()
					&& flight.getRemainingBusinessSeats() <= flight.getFleet().getTotalBusinessSeats()
					&& flight.getRemainingPremiumSeats() <= flight.getFleet().getTotalPremiumSeats()) {

				if (locationDao.findByName(flight.getDepartureLocation().getName()) != null) {
					flight.setDepartureLocation(locationDao.findByName(flight.getDepartureLocation().getName()));
				}

				if (locationDao.findByName(flight.getArrivalLocation().getName()) != null) {
					flight.setArrivalLocation(locationDao.findByName(flight.getArrivalLocation().getName()));
				}
				flightDao.save(flight);
				return true;

			}

			throw new RecordAlreadyPresentException(INVALID_DETAILS_ENTERED);
		}

		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);

	}

	@Override
	public long totalRevenue() {
		long revenue = 0;
		List<Booking> bookingList = (List<Booking>) bookingDao.findAll();
		for (int i = 0; i < bookingList.size(); i++) {
			revenue += bookingList.get(i).getTotalCost();
		}
		return revenue;
	}

}
