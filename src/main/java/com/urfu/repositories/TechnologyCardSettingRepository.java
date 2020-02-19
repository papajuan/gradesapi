package com.urfu.repositories;

import com.urfu.entities.DisciplineLoad;
import com.urfu.entities.TechnologyCardSetting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author aperminov
 * 31.01.2020
 */
public interface TechnologyCardSettingRepository extends CrudRepository<TechnologyCardSetting, Long> {

    @Query("select distinct tcs.disciplineLoad from TechnologyCardSetting tcs where tcs.disciplineLoad in (:disciplineLoads) and tcs.isAgreed = true")
    Iterable<DisciplineLoad> findDisciplineLoadsInAgreedTechnologyCards(@Param("disciplineLoads") Iterable<DisciplineLoad> disciplineLoads);
}
