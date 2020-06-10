package com.urfu.repositories;

import com.urfu.entities.Mark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author aperminov
 * 02.03.2020
 */
@Repository
public interface MarkRepository extends CrudRepository<Mark, Integer> {

}
