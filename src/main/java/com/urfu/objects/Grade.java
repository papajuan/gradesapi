package com.urfu.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author aperminov
 * 02.03.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {

    private BigDecimal totalCalculatedScore;
    private String mark;
}
