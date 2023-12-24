package com.gridnine.testing.service.impl;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import com.gridnine.testing.service.FlightFilterService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterServiceImpl implements FlightFilterService {

    @Override
    public List<Flight> departureToTheCurrentPointInTime(List<Flight> flights) {
        LocalDateTime now = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(now)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> segmentsEarlierThanDepartureDate(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList());

    }

    @Override
    public List<Flight> flightsWhereTimeOnTheGroundExceedsTwoHours(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> groundTimeBetweenSegments(flight) <= 2)
                .collect(Collectors.toList());
    }

    private int groundTimeBetweenSegments(Flight flight) {
        List<Segment> segments = flight.getSegments();
        int groundTime = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            LocalDateTime currentArrival = segments.get(i).getArrivalDate();
            LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
            groundTime += currentArrival.until(nextDeparture, ChronoUnit.HOURS);
        }
        return groundTime;
    }
}
