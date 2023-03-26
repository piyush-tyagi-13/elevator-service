package com.piyushtyagi.elevatorservice.service;

import com.piyushtyagi.elevatorservice.entity.Elevator;
import com.piyushtyagi.elevatorservice.entity.Hotel;
import com.piyushtyagi.elevatorservice.exception.ElevatorRestrictedException;
import com.piyushtyagi.elevatorservice.models.ElevatorStatusResponse;
import com.piyushtyagi.elevatorservice.repository.ElevatorRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        //initialise elevator
        Elevator elevator = elevatorRepository.findById(elevatorId).get();

        //checking if elevator is not restricted due to ongoing movement,
        // maintenance or for use by select personnel.
        elevatorAvailabilityCheck(elevator);

        //restricting inputs till movement is complete
        elevator.setRestricted(Boolean.TRUE);
        elevatorRepository.save(elevator);
        //movement is restricted

        //starting traversal
        int currentFloor = elevator.getCurrentFloor();
        int distance = Math.abs(targetFloor-currentFloor);
        elevator.setCurrentFloor(targetFloor);
        elevator.setRestricted(Boolean.FALSE);
        elevatorRepository.save(elevator);
        //traversal is complete

        //logging movement in history table
        elevatorHistoryService.saveTravelInfo(hotelId, elevatorId, distance);
        return "Elevator traversal complete!";
    }

    private static void elevatorAvailabilityCheck(Elevator elevator) throws Exception {
        if(elevator.isRestricted()) {
            throw new ElevatorRestrictedException("Elevator is not accessible.");
        }
    }

    @Override
    public ElevatorStatusResponse getStatus(Long hotelId, Long elevatorId) {
        ElevatorStatusResponse response = new ElevatorStatusResponse();
        Elevator elevator = elevatorRepository.findById(elevatorId).get();
        response.setHotelId(String.valueOf(elevator.getHotel().getId()));
        response.setElevatorId(String.valueOf(elevator.getId()));
        response.setCurrentFloor(String.valueOf(elevator.getCurrentFloor()));
        return response;
    }

    public ByteArrayOutputStream getWorkbookByteArrayOutputStream() throws IOException {
        List<Hotel> hotels = hotelService.getAll();
        Map<Long, Hotel> hotelMap = hotels.stream().collect(Collectors.toMap(key -> key.getId(), value -> value));

        List<Elevator> elevators = elevatorRepository.findAll();

        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Add a new sheet to the workbook
        Sheet sheet = workbook.createSheet("Hotels and Elevators");

        // Create the header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Hotel ID");
        headerRow.createCell(1).setCellValue("Hotel Name");
        headerRow.createCell(2).setCellValue("Elevator ID");
        headerRow.createCell(3).setCellValue("Elevator Name");

        // Populate the data rows
        int rowIndex = 1;
        for (Elevator elevator : elevators) {
                Row dataRow = sheet.createRow(rowIndex++);
                dataRow.createCell(0).setCellValue(elevator.getHotel().getId());
                dataRow.createCell(1).setCellValue(elevator.getHotel().getName());
                dataRow.createCell(2).setCellValue(elevator.getId());
                dataRow.createCell(3).setCellValue(elevator.getName());
        }

        // Serialize the workbook to a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return baos;
    }
}
