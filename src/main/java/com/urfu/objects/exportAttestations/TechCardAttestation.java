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
 * 21.02.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechCardAttestation {

    private TechCardFactorsType type;
    private BigDecimal factor;
    private List<AttestationControl> controls;
}
