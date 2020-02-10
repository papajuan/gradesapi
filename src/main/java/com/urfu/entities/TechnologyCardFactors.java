package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author urfu
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "technologycardfactors")
@IdClass(TechnologyCardFactorsKey.class)
public class TechnologyCardFactors {

    @Id
    @Column(name = "technologycardtype")
    private String technologyCardType;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplineload", referencedColumnName = "dd")
    private DisciplineLoad disciplineLoad;

    @Column(name = "currentfactor")
    private BigDecimal current;

    @Column(name = "intermediatefactor")
    private BigDecimal intermediate;

    @Column(name = "totalfactor")
    private BigDecimal total;
}
