package com.urfu.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @author urfu
 * 31.01.2020
 */
@Data
@AllArgsConstructor
public class StudentFactorsInformation {

    private String uuid;
    private int eduYear;
    private String semester;
    Set<ExportDiscipline> disciplines;
}
