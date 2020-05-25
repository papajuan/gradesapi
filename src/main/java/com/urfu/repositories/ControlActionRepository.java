package com.urfu.repositories;

import com.urfu.entities.ControlAction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author aperminov
 * 02.03.2020
 */
public interface ControlActionRepository extends CrudRepository<ControlAction, String> {

    @Query("from ControlAction ca where ca.eventCode = 3")
    Iterable<ControlAction> getTestControlActions();

    @Query("select distinct ca from TechnologyCard tc join tc.event as ca where tc.disciplineLoad.disciplineLoadId = :disciplineId and ca is not null and ca.eventCode = 3")
    Optional<ControlAction> getControlActionByDisciplineId(@Param("disciplineId") String disciplineId);
}
