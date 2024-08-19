package com.tramshedtech.eventmanagement.service;

import com.tramshedtech.eventmanagement.entity.Location;
import com.tramshedtech.eventmanagement.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();
}
