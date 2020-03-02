package com.urfu.repositories;

import com.urfu.entities.DisciplineLoad;
import com.urfu.entities.StudentTotalMark;
import com.urfu.entities.TechnologyCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author aperminov
 * 31.01.2020
 */
public interface StudentTotalMarkRepository extends CrudRepository<StudentTotalMark, Integer> {

    @Query("from StudentTotalMark stm where substring(stm.studentId, 1, 32) = :studentId")
    Iterable<StudentTotalMark> findMarksByStudentId(@Param("studentId") String studentId);

    @Query("select distinct stm.disciplineLoad from StudentTotalMark stm " +
            "where substring(stm.studentId, 1, 32) = :studentId " +
            "and stm.disciplineLoad.eduYear = :eduYear " +
            "and stm.disciplineLoad.termType = :termType " +
            "and stm.disciplineLoad.hasNewRegister = false " +
            "and stm.isMinor = true")
    Iterable<DisciplineLoad> findAllDisciplineLoadByStudentYearSemesterInOldRegister(@Param("studentId") String studentId,
                                                                                     @Param("eduYear") int eduYear,
                                                                                     @Param("termType") String termType);

    @Query("from TechnologyCard tc where tc.technologyCardType = :techCardType " +
            "and tc.disciplineLoad = :disciplineLoad " +
            "and tc.isIntermediate = :isIntermediate " +
            "and :studentId in (select substring(stm.studentId, 1 ,32) from StudentTotalMark stm where stm.disciplineLoad = :disciplineLoad)")
    Iterable<TechnologyCard> findTechnologyCardsByTypeStudentAndDisciplineLoad(@Param("techCardType") String technologyCardTypeName,
                                                                               @Param("studentId") String studentId,
                                                                               @Param ("disciplineLoad") DisciplineLoad disciplineLoad,
                                                                               @Param ("isIntermediate") boolean isIntermediate);
}
