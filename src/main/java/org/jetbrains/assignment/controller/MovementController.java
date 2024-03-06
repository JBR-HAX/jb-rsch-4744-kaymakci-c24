package org.jetbrains.assignment.controller;

import java.util.List;

import org.jetbrains.assignment.domain.Movement;
import org.jetbrains.assignment.domain.Position;
import org.jetbrains.assignment.service.LocationService;
import org.jetbrains.assignment.service.MovementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovementController {

    private final MovementService movementService;
    private final LocationService locationService;

    public MovementController(MovementService movementService, LocationService locationService) {
        this.movementService = movementService;
        this.locationService = locationService;
    }

    @PostMapping("/locations")
    public ResponseEntity<List<Position>> changeLocation(@RequestBody final List<Movement> movements) {
        List<Position> positions = locationService.changeLocation(movements);
        return ResponseEntity.ok(positions);
    }

    @PostMapping("/moves")
    public ResponseEntity<List<Movement>> makeMovement(@RequestBody final List<Position> position) {
        List<Movement> moves = movementService.makeMovements(position);
        return ResponseEntity.ok(moves);
    }

}
