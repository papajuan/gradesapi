package com.urfu.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author aperminov
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "tkdis")
public class DisciplineCatalogItem {

    @Id
    @Column(name = "kdis")
    private String disciplineCatalogItemId;

    @Column(name = "rdis")
    private String title;
}
