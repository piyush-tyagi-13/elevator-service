package com.piyushtyagi.elevatorservice.service;

import com.piyushtyagi.elevatorservice.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HotelService {

    @Autowired
    RestTemplate restTemplate;

    public Hotel getHotelInfo(Long id) {
        return this.restTemplate.getForObject("http://hotel-service:8081/hotel-service/hotel/1", Hotel.class);
    }
}
