package com.urfu.objects;

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
public class Attestation {

    private TechnologyCardFactorsType type;
    private double factor;
    private Set<AttestationControl> controls;
}
