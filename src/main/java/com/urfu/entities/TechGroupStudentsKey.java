package com.urfu.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author aperminov
 * 03.02.2020
 */
@EqualsAndHashCode
@AllArgsConstructor
public class TechGroupStudentsKey implements Serializable {

    private String studentId;
    private TechGroup techGroup;
}
