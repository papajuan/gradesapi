package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author urfu
 * 29.01.2020
 */
@Entity
@Data
@Table(name = "technologycard")
public class TechnologyCard {

    @Id
    @Column(name = "UUID")
    private String id;

    @Column(name = "controlaction")
    private String controlAction;

    @Column(name = "maxvalue")
    private int maxValue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplineload", referencedColumnName = "dd")
    private DisciplineLoad disciplineLoad;

    @Column(name = "technologycardtype")
    private String technologyCardType;

    @Column(name = "intermediate")
    private boolean isIntermediate;
}
