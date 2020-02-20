package com.urfu.objects;

import com.urfu.objects.exportDisciplines.ExportDiscipline;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author aperminov
 * 31.01.2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInformation {

    private String uuid;
    private int eduYear;
    private String semester;
    Set<? extends ExportDiscipline> disciplines;
}
