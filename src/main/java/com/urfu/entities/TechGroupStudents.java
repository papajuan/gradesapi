package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author aperminov
 * 29.01.2020
 */
@Entity
@Data
@IdClass(TechGroupStudentsKey.class)
@Table(name = "techgroupstudents")
public class TechGroupStudents {

    @Id
    @Column(name = "student")
    private String studentId;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "techgroup", referencedColumnName = "id")
    private TechGroup techGroup;

    @Column(name = "detaildiscipline")
    private String detailDiscipline;
}
