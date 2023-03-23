package com.piyushtyagi.elevatorservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ElevatorHistoryService {

    @Autowired
    RestTemplate restTemplate;

    public String saveTravelInfo(Long hotelId, Long elevatorId, Integer floorsTravelled) {
        return restTemplate.getForObject("http://history-service:9002/history-service/hotel/"+hotelId+"/elevator/"+elevatorId+"/travelled/"+floorsTravelled,
                String.class);
    }
}
