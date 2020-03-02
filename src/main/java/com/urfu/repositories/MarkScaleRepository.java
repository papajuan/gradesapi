package com.urfu.repositories;

import com.urfu.entities.MarkScale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author aperminov
 * 02.03.2020
 */
public interface MarkScaleRepository extends CrudRepository<MarkScale, Integer> {

    @Query("from MarkScale scale where scale.rate <= :score and scale.test = :test order by scale.rate desc")
    Iterable<MarkScale> getMarkScaleByScoreAndControl(@Param("score") int score, @Param("test") boolean test);
}
