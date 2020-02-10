package com.urfu.objects;

import com.urfu.entities.TechnologyCardType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author urfu
 * 31.01.2020
 */
@Data
@AllArgsConstructor
public class DisciplineEvent {

    private TechnologyCardType type;
    private String typeTitle;
    private double totalFactor;
    private boolean testBeforeExam;
    private Set<Attestation> attestations;
}
