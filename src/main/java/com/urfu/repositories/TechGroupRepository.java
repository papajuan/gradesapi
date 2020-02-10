package com.urfu.repositories;

import com.urfu.entities.DisciplineLoad;
import com.urfu.entities.TechGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TechGroupRepository extends CrudRepository<TechGroup, Long> {

    @Query("select distinct substring(tg.disciplineTitle, 1, locate('\\', tg.disciplineTitle) - 1) " +
            "from TechGroup as tg, TechGroupStudents as tgs " +
            "where tg.disciplineLoad = :disciplineLoad " +
            "and substring(tgs.studentId, 1, 32) = :studentId")
    Optional<String> getTitleByDisciplineLoad(@Param("disciplineLoad") DisciplineLoad disciplineLoad,
                                              @Param("studentId") String studentId);
}
