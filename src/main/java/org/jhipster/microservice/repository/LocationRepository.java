package org.jhipster.microservice.repository;
import org.jhipster.microservice.domain.Location;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends MongoRepository<Location, String> {

}
