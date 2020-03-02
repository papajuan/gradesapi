package com.urfu.objects.disciplineEvents;

import com.urfu.entities.TechnologyCardType;
import com.urfu.objects.exportAttestations.ScoresAttestation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author aperminov
 * 21.02.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoresDisciplineEvent {

    private TechnologyCardType type;
    private String typeTitle;
    private BigDecimal totalFactor;
    private BigDecimal scoreWithoutFactor;
    private BigDecimal scoreWithFactor;
    private Set<ScoresAttestation> attestations;
}
