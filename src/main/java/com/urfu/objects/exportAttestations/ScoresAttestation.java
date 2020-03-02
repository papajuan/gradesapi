package com.urfu.objects.exportAttestations;

import com.urfu.objects.AttestationControl;
import com.urfu.objects.TechCardFactorsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal scoreWithoutFactor;
    private BigDecimal scoreWithFactor;
    List<AttestationControl> controls;
}
