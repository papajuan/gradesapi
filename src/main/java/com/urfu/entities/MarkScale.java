package com.urfu.entities;

import lombok.Data;

import javax.persistence.*;

/**
 * @author aperminov
 * 02.03.2020
 */
@Entity
@Data
@Table(name = "tmarkscale")
public class MarkScale {

    @Id
    @Column(name = "code")
    private int code;

    @Column(name = "rate")
    private int rate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mark", referencedColumnName = "code")
    private Mark mark;

    @Column(name = "test")
    private boolean test;
}
