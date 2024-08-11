package com.tramshedtech.eventmanagement.mapper;

import com.tramshedtech.eventmanagement.entity.Location;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface LocationMapper {
    List<Location> getAllLocations();
}
