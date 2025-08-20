package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static dk.cphbusiness.flightdemo.FlightReader.getFlightInfoDetails;
import static dk.cphbusiness.flightdemo.FlightReader.getFlightsFromFile;
import static java.lang.Long.sum;

public class FlightServices {

    public static void main(String[] args) throws IOException {
        System.out.println(getFlightDurations("Lufthansa"));
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

}
