package com.kitHub.Facilities_Info.service;

import com.kitHub.Facilities_Info.domain.GeoCoordinates;
import com.kitHub.Facilities_Info.repository.GeoCoordinatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoCoordinatesService {

    private final GeoCoordinatesRepository geoCoordinatesRepository;

    public List<GeoCoordinates> getGeoCoordinatesWithinRadius(double latitude, double longitude, double radius) {
        return geoCoordinatesRepository.findGeoCoordinatesWithinRadius(latitude, longitude, radius);
    }
}
