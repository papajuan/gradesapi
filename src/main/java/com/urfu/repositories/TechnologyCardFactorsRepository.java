package com.urfu.repositories;

import com.urfu.entities.DisciplineLoad;
import com.urfu.entities.TechnologyCardFactors;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TechnologyCardFactorsRepository extends CrudRepository<TechnologyCardFactors, String> {

    @Query("from TechnologyCardFactors tcf where tcf.disciplineLoad in (:disciplineLoads)")
    Iterable<TechnologyCardFactors> findAllByDisciplineLoads(@Param("disciplineLoads") Iterable<DisciplineLoad> dsiciplineLoads);

    @Query("from TechnologyCardFactors tcf where tcf.disciplineLoad = :disciplineLoad")
    Iterable<TechnologyCardFactors> findAllByDisciplineLoad(@Param("disciplineLoad") DisciplineLoad disciplineLoad);

    @Query("from TechnologyCardFactors tcf where tcf.technologyCardType = :technologyCardType and tcf.disciplineLoad = :disciplineLoad")
    Optional<TechnologyCardFactors> findByTypeAndDisciplineLoad(@Param("technologyCardType") String technologyCardType, @Param("disciplineLoad") DisciplineLoad disciplineLoad);
}
