package com.tramshedtech.eventmanagement.service.impl;

import com.tramshedtech.eventmanagement.entity.Location;
import com.tramshedtech.eventmanagement.mapper.LocationMapper;
import com.tramshedtech.eventmanagement.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<Location> getAllLocations() {
//        return locationMapper.getAllLocations();
        List<Location> locations = locationMapper.getAllLocations();
        System.out.println("Fetched Locations: " + locations);
        return locations;
    }
}
