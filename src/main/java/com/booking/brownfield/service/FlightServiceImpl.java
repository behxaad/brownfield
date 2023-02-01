package com.booking.brownfield.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.brownfield.dao.FlightDao;
import com.booking.brownfield.dao.LocationDao;
import com.booking.brownfield.dto.FlightDto;
import com.booking.brownfield.entity.Flight;
import com.booking.brownfield.entity.Location;
import com.booking.brownfield.exception.RecordNotFoundException;

@Service
public class FlightServiceImpl implements FlightService {

	private static final String FLIGHT_NOT_FOUND = "FLIGHT NOT FOUND";
	private static final String INVALID_SEAT_CLASS = "INVALID SEAT CLASS ENTERED";

	@Autowired
	private FlightDao flightDao;
	@Autowired
	private LocationDao locationDao;

	@Override
	public List<FlightDto> searchFlight(String departure, String arrival, Date date) {
		if (locationDao.findByName(departure) != null && locationDao.findByName(arrival) != null) {
			Location departureSearch = locationDao.findByName(departure);
			Location arrivalSearch = locationDao.findByName(arrival);
			ModelMapper mapper = new ModelMapper();
			List<Flight> list = flightDao.findByFlight(departureSearch.getId(), arrivalSearch.getId(), date);
			if (!list.isEmpty()) {
				return Arrays.asList(mapper.map(list, FlightDto[].class));
			}

		}

		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);
	}

	@Override
	public double getFare(int flightId, String classType) {
		Optional<Flight> flightCheck = flightDao.findById(flightId);

		if (flightCheck.isPresent()) {
			if (classType.equalsIgnoreCase("economy")) {
				return flightCheck.get().getFare().getEconomyFare();

			}

			else if (classType.equalsIgnoreCase("business")) {
				return flightCheck.get().getFare().getBusinessFare();

			}

			else if (classType.equalsIgnoreCase("premium")) {
				return flightCheck.get().getFare().getPremiumFare();

			}

			else
				throw new RecordNotFoundException(INVALID_SEAT_CLASS);
		}

		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);
	}

	@Override
	public FlightDto getFlight(int flightId) {
		Optional<Flight> flightCheck = flightDao.findById(flightId);
		if (flightCheck.isPresent()) {
			FlightDto flightDto = new FlightDto();
			BeanUtils.copyProperties(flightCheck.get(), flightDto);
			return flightDto;
		}

		throw new RecordNotFoundException(FLIGHT_NOT_FOUND);
	}

	@Override  //WE ARE NOT DISPLAYING IT TO USER. AS IT MAKES DATA VULNERABLE
	public List<FlightDto> getAllFlights() {
		ModelMapper mapper = new ModelMapper();
		return Arrays.asList(mapper.map(flightDao.findAll(), FlightDto[].class));
	}

}
