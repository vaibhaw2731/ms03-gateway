package org.jhipster.microservice.service.impl;

import org.jhipster.microservice.service.LocationService;
import org.jhipster.microservice.domain.Location;
import org.jhipster.microservice.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Location}.
 */
@Service
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    /**
     * Save a location.
     *
     * @param location the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    /**
     * Get all the locations.
     *
     * @return the list of entities.
     */
    @Override
    public List<Location> findAll() {
        log.debug("Request to get all Locations");
        return locationRepository.findAll();
    }


    /**
     * Get one location by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Location> findOne(String id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    /**
     * Delete the location by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }
}
