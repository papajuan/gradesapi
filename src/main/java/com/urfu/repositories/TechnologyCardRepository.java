package com.urfu.repositories;

import com.urfu.entities.DisciplineLoad;
import com.urfu.entities.TechnologyCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author aperminov
 * 31.01.2020
 */
public interface TechnologyCardRepository extends CrudRepository<TechnologyCard, String> {

    @Query("from TechnologyCard tc where tc.disciplineLoad in (:disciplineLoads)")
    Iterable<TechnologyCard> findAllByDisciplineLoads(@Param("disciplineLoads") Iterable<DisciplineLoad> disciplineLoads);

    @Query("from TechnologyCard tc where tc.disciplineLoad = :disciplineLoad")
    Iterable<TechnologyCard> findAllByDisciplineLoad(@Param("disciplineLoad") DisciplineLoad disciplineLoad);
}
