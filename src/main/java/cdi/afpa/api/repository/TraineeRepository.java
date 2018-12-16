package cdi.afpa.api.repository;

import cdi.afpa.api.domain.Trainee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Trainee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

}
