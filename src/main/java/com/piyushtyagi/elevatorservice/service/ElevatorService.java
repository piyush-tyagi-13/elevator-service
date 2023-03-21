package com.piyushtyagi.elevatorservice.service;

import com.piyushtyagi.elevatorservice.entity.Elevator;
import org.springframework.stereotype.Service;

@Service
public interface ElevatorService {

    public String moveToFloor(Long hotelId, Long elevatorId, Integer floorNumber) throws Exception;

    Elevator getStatus(Long hotelId, Long elevatorId);
}

