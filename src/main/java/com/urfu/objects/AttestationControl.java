package com.urfu.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author aperminov
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttestationControl {

    private String title;
    private int maxScore;
    private double score;

    public AttestationControl(String title, int maxScore) {
        this.title = title;
        this.maxScore = maxScore;
        score = 0;
    }
}
