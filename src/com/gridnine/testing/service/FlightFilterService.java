package com.gridnine.testing.service;

import com.gridnine.testing.Flight;
import java.util.List;

public interface FlightFilterService {


        public List<Flight> departureToTheCurrentPointInTime(List<Flight> flights);

        public List<Flight> segmentsEarlierThanDepartureDate(List<Flight> flights);

        public List<Flight> flightsWhereTimeOnTheGroundExceedsTwoHours(List<Flight> flights);

}
