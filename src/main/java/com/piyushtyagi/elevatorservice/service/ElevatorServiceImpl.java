package com.piyushtyagi.elevatorservice.service;

import com.piyushtyagi.elevatorservice.entity.Elevator;
import com.piyushtyagi.elevatorservice.repository.ElevatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    @Autowired
    HotelService hotelService;

    @Autowired
    ElevatorHistoryService elevatorHistoryService;

    @Autowired
    ElevatorRepository elevatorRepository;

    @Override
    public String moveToFloor(Long hotelId, Long elevatorId, Integer targetFloor) throws Exception {
        int totalFloors = hotelService.getHotelInfo(hotelId).getTotalFloors();
        if(targetFloor>totalFloors) {
            throw new IllegalArgumentException("Target floor number exceeds max floors in Hotel!");
        }
        Elevator elevator = elevatorRepository.findById(elevatorId).get();
        if(elevator.isRestricted()) {
            throw new Exception("Elevator is not accessible.");
        }
        int currentFloor = elevator.getCurrentFloor();
        int distance = Math.abs(targetFloor-currentFloor);
        elevator.setCurrentFloor(targetFloor);
        elevatorRepository.save(elevator);
        elevatorHistoryService.saveTravelInfo(hotelId, elevatorId, distance);
        return "Elevator traversal complete!";
    }

    @Override
    public Elevator getStatus(Long hotelId, Long elevatorId) {
        return elevatorRepository.findById(elevatorId).get();
    }
}
