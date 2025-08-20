package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dk.cphbusiness.flightdemo.FlightReader.getFlightInfoDetails;
import static dk.cphbusiness.flightdemo.FlightReader.getFlightsFromFile;
import static java.lang.Long.sum;

public class FlightServices {

    public static void main(String[] args) throws IOException {
        System.out.println(getFlightDurations("Lufthansa"));
        System.out.println("********************************************");
        System.out.println(getAvgFlightDurations("Lufthansa"));
        System.out.println("********************************************");
        //getOriginAndDestinationAirplanes("Fukuoka","Haneda Airport").forEach(System.out::println);
        System.out.println("********************************************");
       // getFlightsBeforeTime("01:00").forEach(System.out::println);

    }
    public static long getFlightDurations(String airline) throws IOException {
        List<FlightDTO> flightList = getFlightsFromFile("flights.json");
        List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);

        long result = flightInfoDTOList.stream()
                .filter(flight -> flight.getAirline() != null)
                .filter(flight -> flight.getAirline().equals(airline))
                .mapToLong(flight -> flight.getDuration().toMinutes())
                .sum();

        return result;

    }

    public static double getAvgFlightDurations(String airline) throws IOException {
        List<FlightDTO> flightList = getFlightsFromFile("flights.json");
        List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);

        double result = flightInfoDTOList.stream()
                .filter(flight -> flight.getAirline() != null)
                .filter(flight -> flight.getAirline().equals(airline))
                .mapToLong(flight -> flight.getDuration().toHours())
                .average()
                .orElse(0.0);

        return result;

    }

    public static List<FlightInfoDTO>getOriginAndDestinationAirplanes(String origin, String destination) throws IOException {
        List<FlightDTO> flightList = getFlightsFromFile("flights.json");
        List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);

        List<FlightInfoDTO> result = flightInfoDTOList.stream()
                .filter(flight -> flight.getOrigin() != null && flight.getDestination() != null)
                .filter(flight -> flight.getOrigin().equals(origin))
                .filter(flight -> flight.getDestination().equals(destination))
                .collect(Collectors.toList());

        return result;


    }

    public static List<FlightInfoDTO> getFlightsBeforeTime(String time) throws IOException {
        List<FlightDTO> flightList = getFlightsFromFile("flights.json");
        List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);

        // Konverter input tid til LocalTime
        LocalTime inputTime = LocalTime.parse(time); // fx "01:00"

        List<FlightInfoDTO> result = flightInfoDTOList.stream()
                .filter(flight -> flight.getDeparture() != null)
                .filter(flight -> {
                    LocalTime departureTime = flight.getDeparture().toLocalTime();
                    return departureTime.isBefore(inputTime);
                })
                .collect(Collectors.toList());

        return result;
    }
/*
    public static Map<String, Double> getAllAvgFlightDurations() throws IOException {
        List<FlightDTO> flightList = getFlightsFromFile("flights.json");
        List<FlightInfoDTO> flightInfoDTOList = getFlightInfoDetails(flightList);

        Map<String, Double> result = flightInfoDTOList.stream()
                .filter(flight -> flight.getAirline() != null)
                .mapToLong(flight -> flight.getDuration().toHours())
                .average()
                .orElse(0.0);


        return result;

    }

 */



}
