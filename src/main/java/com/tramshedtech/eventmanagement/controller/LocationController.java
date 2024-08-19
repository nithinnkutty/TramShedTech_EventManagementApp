package com.tramshedtech.eventmanagement.controller;

import com.tramshedtech.eventmanagement.entity.Location;
import com.tramshedtech.eventmanagement.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
}
