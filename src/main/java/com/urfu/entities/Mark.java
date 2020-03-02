package com.urfu.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author aperminov
 * 02.03.2020
 */
@Entity
@Data
@Table(name = "tmark")
public class Mark {

    @Id
    @Column(name = "code")
    private int code;

    @Column(name = "title")
    private String title;

    @Column(name = "failure")
    private int failure;

    @Column(name = "mark")
    private int mark;
}
