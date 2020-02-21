package com.urfu.objects.exportAttestations;

import com.urfu.objects.AttestationControl;
import com.urfu.objects.TechnologyCardFactorsType;
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
public class TechnologyCardAttestation {

    private TechnologyCardFactorsType type;
    private BigDecimal factor;
    private List<AttestationControl> controls;
}
