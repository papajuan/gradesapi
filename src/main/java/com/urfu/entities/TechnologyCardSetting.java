package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author aperminov
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "technologycardsetting")
public class TechnologyCardSetting {

    @Id
    @Column(name = "settingid")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplineloadid", referencedColumnName = "dd")
    private DisciplineLoad disciplineLoad;

    @Column(name = "agreed")
    private boolean isAgreed;
}
