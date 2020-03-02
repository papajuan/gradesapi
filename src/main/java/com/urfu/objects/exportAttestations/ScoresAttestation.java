package com.urfu.objects.exportAttestations;

import com.urfu.objects.TechCardFactorsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author aperminov
 * 20.02.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoresAttestation {

    private TechCardFactorsType type;
    private BigDecimal factor;
    private BigDecimal calculatedScore;
}
