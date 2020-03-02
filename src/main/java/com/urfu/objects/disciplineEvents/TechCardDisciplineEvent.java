package com.urfu.objects.disciplineEvents;

import com.urfu.entities.TechnologyCardType;
import com.urfu.objects.exportAttestations.TechCardAttestation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author aperminov
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechCardDisciplineEvent {

    private TechnologyCardType type;
    private String typeTitle;
    private BigDecimal totalFactor;
    private boolean testBeforeExam;
    private Set<TechCardAttestation> attestations;
}
