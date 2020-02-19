package com.urfu.objects;

import com.urfu.entities.TechnologyCardType;
import com.urfu.objects.exportAttestations.Attestation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author urfu
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineEvent {

    private TechnologyCardType type;
    private String typeTitle;
    private double totalFactor;
    private boolean testBeforeExam;
    private Set<Attestation> attestations;
}
