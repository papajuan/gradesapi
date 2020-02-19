package com.urfu.objects.exportAttestations;

import com.urfu.objects.AttestationControl;
import com.urfu.objects.TechnologyCardFactorsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author aperminov
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attestation {

    private TechnologyCardFactorsType type;
    private double factor;
    private List<AttestationControl> controls;
}
