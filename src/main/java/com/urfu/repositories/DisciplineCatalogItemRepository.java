package com.urfu.repositories;

import com.urfu.entities.DisciplineCatalogItem;
import org.springframework.data.repository.CrudRepository;

/**
 * @author aperminov
 * 31.01.2020
 */
public interface DisciplineCatalogItemRepository extends CrudRepository<DisciplineCatalogItem, String> {
}
