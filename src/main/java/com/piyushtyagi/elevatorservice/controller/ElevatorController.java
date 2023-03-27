package com.piyushtyagi.elevatorservice.controller;

import com.piyushtyagi.elevatorservice.models.TraverseElevatorRequest;
import com.piyushtyagi.elevatorservice.models.ElevatorStatusResponse;
import com.piyushtyagi.elevatorservice.service.ElevatorService;
import com.piyushtyagi.elevatorservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/elevator-service")
public class ElevatorController {

    @Autowired
    private ElevatorService elevatorService;

    @Autowired
    private HotelService hotelService;

    @PostMapping("/traverseFloor")
    public ResponseEntity<ElevatorStatusResponse> moveToFloor(@RequestBody TraverseElevatorRequest req) throws Exception {
        ElevatorStatusResponse resp = new ElevatorStatusResponse();
        resp.setHotelId(req.getHotelId());
        resp.setElevatorId(req.getElevatorId());
        resp.setStatus(elevatorService.moveToFloor(Long.parseLong(req.getHotelId()), Long.parseLong(req.getElevatorId()), Integer.parseInt(req.getFloorNumber())));
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<ElevatorStatusResponse> getStatus(@PathVariable Long hotelId, @PathVariable Long elevatorId) {
        return new ResponseEntity<>(elevatorService.getStatus(hotelId, elevatorId), HttpStatus.OK);
    }

    @PostMapping("/hotels/elevators/export")
    public ResponseEntity<byte[]> exportHotelsElevatorsToExcel() throws IOException {
        // Fetch the hotels and their elevators from the database
        ByteArrayOutputStream baos = elevatorService.getWorkbookByteArrayOutputStream();

        // Set the HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData("attachment", "hotels-and-elevators.xlsx");

        // Return the byte array as the response body
        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }


}
