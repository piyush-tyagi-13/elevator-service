package com.piyushtyagi.elevatorservice.service;

import com.piyushtyagi.elevatorservice.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    RestTemplate restTemplate;

    public Hotel getHotelInfo(Long id) {
        return this.restTemplate.getForObject("http://hotel-service/hotels/"+String.valueOf(id), Hotel.class);
    }

    public List<Hotel> getAll() {
        //return this.restTemplate.getForObject("http://hotel-service/hotel-service/all", ResponseEntity.class).get;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<List<Hotel>> responseEntity = restTemplate.exchange("http://hotel-service/hotels/all",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Hotel>>() {});
        //return restTemplate.getForObject("http://hotel-service/hotels/all", ArrayList.class);

        return responseEntity.getBody();
    }
}
