package com.piyushtyagi.elevatorservice.controller;

import com.piyushtyagi.elevatorservice.entity.Elevator;
import com.piyushtyagi.elevatorservice.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elevator-service")
public class ElevatorController {

    @Autowired
    private ElevatorService elevatorService;

    @GetMapping("/hotel/{hotelId}/elevator/{elevatorId}/floor/{floorNumber}")
    public String moveToFloor(@PathVariable Long hotelId,
                              @PathVariable Long elevatorId,
                              @PathVariable Integer floorNumber) throws Exception {
        return elevatorService.moveToFloor(hotelId, elevatorId, floorNumber);
    }

    @GetMapping("/hotel/{hotelId}/elevator/{elevatorId}/status")
    public Elevator getStatus(@PathVariable Long hotelId, @PathVariable Long elevatorId) {
        return elevatorService.getStatus(hotelId, elevatorId);
    }
}
