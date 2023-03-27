package com.piyushtyagi.elevatorservice.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.piyushtyagi.elevatorservice.Constants.ElevatorPlatformConstants.TRY_AGAIN_LATER;

@Service
public class ElevatorHistoryService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    /*public String saveTravelInfo(Long hotelId, Long elevatorId, Integer floorsTravelled) {
        return restTemplate.getForObject("http://history-service:9002/history-service/hotel/"+hotelId+"/elevator/"+elevatorId+"/travelled/"+floorsTravelled,
                String.class);
    }*/

    public String saveTravelInfo(Long hotelId, Long elevatorId, Integer floorsTravelled) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        String url = "http://history-service:9002/history-service/hotel/"+hotelId+"/elevator/"+elevatorId+"/travelled/"+floorsTravelled;

        return circuitBreaker.run(() -> restTemplate.getForObject(url, String.class),
                throwable -> getFallbackHistoryServiceResponse());
    }

    private String getFallbackHistoryServiceResponse() {
        return TRY_AGAIN_LATER;
    }
}
