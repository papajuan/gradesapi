package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author urfu
 * 03.02.2020
 */
@Entity
@Data
@Table(name = "techgroups")
public class TechGroup {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String disciplineTitle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disciplineload", referencedColumnName = "dd")
    private DisciplineLoad disciplineLoad;


    public void setDisciplineTitle(String disciplineTitle) {
        int delimiter = disciplineTitle.indexOf("/");
        this.disciplineTitle = delimiter != -1 ? disciplineTitle.substring(0, delimiter) : disciplineTitle;
    }
}
