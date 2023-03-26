package com.piyushtyagi.elevatorservice.service;

import com.piyushtyagi.elevatorservice.entity.Elevator;
import com.piyushtyagi.elevatorservice.models.ElevatorStatusResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public interface ElevatorService {

    public String moveToFloor(Long hotelId, Long elevatorId, Integer floorNumber) throws Exception;

    ElevatorStatusResponse getStatus(Long hotelId, Long elevatorId);

    ByteArrayOutputStream getWorkbookByteArrayOutputStream() throws IOException;
}

