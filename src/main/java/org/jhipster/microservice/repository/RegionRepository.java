package org.jhipster.microservice.repository;
import org.jhipster.microservice.domain.Region;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends MongoRepository<Region, String> {

}
