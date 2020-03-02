package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author aperminov
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "tstudenttotalmark")
public class StudentTotalMark {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplineload_uuid", referencedColumnName = "dd")
    private DisciplineLoad disciplineLoad;

    @Column(name = "student_uuid")
    private String studentId;

    @Column(name = "isminor")
    private boolean isMinor;
}
