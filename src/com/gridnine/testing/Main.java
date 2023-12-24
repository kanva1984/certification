package com.gridnine.testing;

import com.gridnine.testing.service.FlightFilterService;
import com.gridnine.testing.service.impl.FlightFilterServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();


        FlightFilterService departureToTheCurrentPointInTime = new FlightFilterServiceImpl();
        List<com.gridnine.testing.Flight> departureToTheCurrentPointInTimeFinal =
                departureToTheCurrentPointInTime.departureToTheCurrentPointInTime(flights);
        System.out.println("Вылет до текущего момента времени: "
                + departureToTheCurrentPointInTimeFinal);

        FlightFilterService segmentsEarlierThanDepartureDate = new FlightFilterServiceImpl();
        List<com.gridnine.testing.Flight> filteredFlightsByArrivalBeforeDeparture =
                segmentsEarlierThanDepartureDate.segmentsEarlierThanDepartureDate(flights);
        System.out.println("Сегменты с датой прилёта раньше даты вылета: "
                + filteredFlightsByArrivalBeforeDeparture);

        FlightFilterService flightsWhereTimeOnTheGroundExceedsTwoHours = new FlightFilterServiceImpl();
        List<Flight> groundExceedsTwoHours;
        groundExceedsTwoHours = flightsWhereTimeOnTheGroundExceedsTwoHours.flightsWhereTimeOnTheGroundExceedsTwoHours(flights);
        System.out.println("Перелеты, где общее время, проведённое на земле, превышает два часа: "
                + groundExceedsTwoHours);
    }

}
