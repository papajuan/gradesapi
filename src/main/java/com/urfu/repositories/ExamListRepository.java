package com.urfu.repositories;

import com.urfu.entities.ExamList;
import com.urfu.entities.TechnologyCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author aperminov
 * 31.01.2020
 */
public interface ExamListRepository extends CrudRepository<ExamList, Integer> {

    @Query("from ExamList examlist where substring(examlist.studentId, 1, 32) = :studentId and examlist.technologyCard = :techCard")
    Optional<ExamList> findByStudentAndTechCard(@Param("studentId") String studentId, @Param("techCard") TechnologyCard techCard);
}
