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
@Table(name = "tmer")
public class ControlAction {

    @Id
    @Column(name = "kmer")
    private String event;

    @Column(name = "kgmer")
    private int eventCode;

    @Column(name = "rmer")
    private String eventTitle;
}
