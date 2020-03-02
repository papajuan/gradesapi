package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author aperminov
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "tzajv")
public class DisciplineLoad {

    @Id
    @Column(name = "dd")
    private String disciplineLoadId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kdis", referencedColumnName = "kdis")
    private DisciplineCatalogItem disciplineCatalogItem;

    @Column(name = "termtype")
    private String termType;

    @Column(name = "eduyear")
    private int eduYear;

    @Column(name = "hasnewregister")
    private boolean hasNewRegister;
}
