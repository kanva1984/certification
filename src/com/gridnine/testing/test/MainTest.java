package com.gridnine.testing.test;

import com.gridnine.testing.Flight;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import com.gridnine.testing.FlightBuilder;
import com.gridnine.testing.Segment;
import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {
    private List<Flight> flights;

@BeforeEach
    public void setUp() {
        flights = FlightBuilder.createFlights();
    }

    @Test
    public void shouldFilterByDepartureToTheCurrentPointInTime() {

        FlightFilterService flightFilterService = new FlightFilterServiceImpl();
        List<Flight> filtered = flightFilterService.departureToTheCurrentPointInTime(flights);

        Assertions.assertEquals(5, filtered.size());
        Assertions.assertTrue(filtered.get(0).getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()));
    }

    @Test
    public void shouldFilterBySegmentsEarlierThanDepartureDate() {
        FlightFilterService flightFilterService = new FlightFilterServiceImpl();
        List<Flight> filtered = flightFilterService.segmentsEarlierThanDepartureDate(flights);

        Assertions.assertEquals(5, filtered.size());
        Assertions.assertTrue(filtered.get(0).getSegments().get(0).getArrivalDate()
                .isAfter(filtered.get(0).getSegments().get(0).getDepartureDate()));
    }

    @Test
    public void shouldFilterByFlightsWhereTimeOnTheGroundExceedsTwoHours() {
        FlightFilterService flightFilterService = new FlightFilterServiceImpl() {
        };
        List<Flight> filtered = flightFilterService.flightsWhereTimeOnTheGroundExceedsTwoHours(flights);

        Assertions.assertEquals(4, filtered.size());
        Assertions.assertTrue(intGroundTime(filtered.get(0)));
        Assertions.assertTrue(intGroundTime(filtered.get(1)));
    }

    private boolean intGroundTime(Flight flight) {
        List<Segment> segments = flight.getSegments();
        int groundTime = 0;

        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime currentArrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
            groundTime += currentArrival.until(nextDeparture, ChronoUnit.HOURS);
        }
        return groundTime < 2;
    }
}
