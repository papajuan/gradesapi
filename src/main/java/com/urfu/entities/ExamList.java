package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author urfu
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "examlist")
public class ExamList {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "student")
    private String studentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "techcard", referencedColumnName = "UUID")
    private TechnologyCard technologyCard;

    @Column(name = "testbeforeexam")
    private boolean isTestBeforeExam;
}
